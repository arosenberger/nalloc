package com.bitb.kcits.macros

import scala.language.existentials
import scala.reflect.macros._

object OptionalMacros {

  def map_impl[A: c.WeakTypeTag, B: c.WeakTypeTag](c: BlackboxContext)(f: c.Expr[A => B]): c.Expr[B] = {
    import c.universe._

    val underlying = underlyingValue[A](c)

    def withFloatingPointSentinelGuard: c.Expr[B] = {
      val sentinelTo = sentinelValue[B](c)

      new Inliner[c.type](c).inlineAndReset( q"""
    if ($underlying != $underlying)
      $sentinelTo
    else
      $f($underlying)
    """)
    }

    def withSentinelGuard: c.Expr[B] = {
      val sentinelFrom = sentinelValue[A](c)
      val sentinelTo = sentinelValue[B](c)

      new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelFrom == $underlying)
      $sentinelTo
    else
      $f($underlying)
    """)
    }

    if (isFloatingPointType[A](c))
      withFloatingPointSentinelGuard
    else
      withSentinelGuard
  }

  def foreach_impl[A: c.WeakTypeTag](c: BlackboxContext)(f: c.Expr[A => Unit]): c.Expr[Unit] = {
    import c.universe._

    val underlying = underlyingValue[A](c)

    def withFloatingPointSentinelGuard: c.Expr[Unit] =
      new Inliner[c.type](c).inlineAndReset( q"""
    if ($underlying == $underlying)
      $f($underlying)
    """)

    def withSentinelGuard: c.Expr[Unit] = {
      val sentinel = sentinelValue[A](c)

      new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinel != $underlying)
      $f($underlying)
    """)
    }

    if (isFloatingPointType[A](c))
      withFloatingPointSentinelGuard
    else
      withSentinelGuard
  }

  private def underlyingValue[A: c.WeakTypeTag](c: BlackboxContext) = {
    import c.universe._

    c.Expr[A](Select(c.prefix.tree, TermName("value")))
  }

  private def isFloatingPointType[A: c.WeakTypeTag](c: BlackboxContext) = c.weakTypeTag[A].tpe match {
    case x if x =:= c.WeakTypeTag.Float.tpe || x =:= c.WeakTypeTag.Double.tpe => true
    case _                                                                    => false
  }

  private def sentinelValue[X: c.WeakTypeTag](c: BlackboxContext): c.universe.Tree = {
    import c.universe._

    c.weakTypeTag[X].tpe match {
      case x if x =:= c.WeakTypeTag.Byte.tpe   => q"-128"
      case x if x =:= c.WeakTypeTag.Short.tpe  => q"-32768"
      case x if x =:= c.WeakTypeTag.Int.tpe    => q"-2147483648"
      case x if x =:= c.WeakTypeTag.Long.tpe   => q"0x8000000000000000L"
      case x if x =:= c.WeakTypeTag.Float.tpe  => q"java.lang.Float.NaN"
      case x if x =:= c.WeakTypeTag.Double.tpe => q"java.lang.Double.NaN"
      case x if x <:< c.WeakTypeTag.AnyRef.tpe => q"null"
      case x                                   => c.abort(c.enclosingPosition, s"Type $x does not support sentinel value checks")
    }
  }

}
