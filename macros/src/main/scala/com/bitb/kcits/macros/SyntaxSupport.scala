package com.bitb.kcits.macros

import scala.reflect.macros.BlackboxContext

case class SyntaxSupport[C <: BlackboxContext with Singleton](c: C) {

  import c.universe._

  def name(s: String) = TermName(c.freshName(s + "$"))

  def isClean(es: c.Expr[_]*): Boolean =
    es.forall {
      _.tree match {
        case t@Ident(_: TermName) if t.symbol.asTerm.isStable => true
        case Function(_, _)                                   => true
        case _                                                => false
      }
    }
}
