package com.bitb.kcits.macros

import scala.reflect.macros._

object Inliner {
  final val debug = Option(System.getProperty("inlinerDebug")).exists(_.toBoolean)
}

class Inliner[C <: BlackboxContext with Singleton](val c: C) {

  import Inliner._
  import c.universe._

  def inlineAndReset[T](tree: Tree): c.Expr[T] = {
    val inlined = inlineApplyRecursive(tree)
    c.Expr[T](c.resetAllAttrs(inlined))
  }

  def inlineApplyRecursive(tree: Tree): Tree = {
    val ApplyName = TermName("apply")

    class InlineSymbol(symbol: Symbol, value: Tree) extends Transformer {
      override def transform(tree: Tree): Tree = tree match {
        case Ident(_) if tree.symbol == symbol   => value
        case tt: TypeTree if tt.original != null => super.transform(TypeTree().setOriginal(transform(tt.original)))
        case _ => super.transform(tree)
      }
    }

    object InlineApply extends Transformer {
      def inlineSymbol(symbol: Symbol, body: Tree, arg: Tree): Tree =
        new InlineSymbol(symbol, arg).transform(body)

      override def transform(tree: Tree): Tree = tree match {
        case Apply(Select(Function(params, body), ApplyName), args) =>
          if (debug) println(s"***** (1) tree transform\n$tree\n${showRaw(tree)}\n\n")
          params.zip(args).foldLeft(body) { case (b, (param, arg)) =>
            inlineSymbol(param.symbol, b, arg)
          }

        case Apply(Function(params, body), args) =>
          if (debug) println(s"***** (2) tree transform\n$tree\n${showRaw(tree)}\n\n")
          params.zip(args).foldLeft(body) { case (b, (param, arg)) =>
            inlineSymbol(param.symbol, b, arg)
          }

        case Apply(Select(Block(_, Function(params, body)), ApplyName), args) =>
          if (debug) println(s"***** (3) tree transform\n$tree\n${showRaw(tree)}\n\n")
          params.zip(args).foldLeft(body) { case (b, (param, arg)) =>
            inlineSymbol(param.symbol, b, arg)
          }

        case Apply(bl@Block(_, Function(params, body)), args) =>
          if (debug) println(s"***** (4) tree transform\n$tree\n${showRaw(tree)}\n\n")
          params.zip(args).foldLeft(body) { case (b, (param, arg)) =>
            inlineSymbol(param.symbol, b, arg)
          }

        case _ =>
          if (debug) println(s"***** Missed tree transform\n$tree\n${showRaw(tree)}\n\n")
          super.transform(tree)
      }
    }

    InlineApply.transform(tree)
  }
}