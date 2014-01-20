package com.bitb.kcits.macros

import scala.language.existentials
import scala.reflect.macros.BlackboxContext

object OptionalMacros {

  def map_impl[A: c.WeakTypeTag, B: c.WeakTypeTag](c: BlackboxContext)(f: c.Expr[A => B]): c.Expr[B] = {
    import c.universe._

    val underlyingValue = c.Expr[A](Select(c.prefix.tree, TermName("value")))

    c.weakTypeTag[A].tpe match {
      case x if x =:= c.WeakTypeTag.Float.tpe ||
                x =:= c.WeakTypeTag.Double.tpe => floatingPointTypeSentinelGuard(c)(underlyingValue, f)
      case _                                   => integralTypeSentinelGuard(c)(underlyingValue, f)
    }
  }

  private def integralTypeSentinelGuard[A: c.WeakTypeTag, B: c.WeakTypeTag](c: BlackboxContext)(underlyingValue: c.Expr[A], f: c.Expr[A => B]): c.Expr[B] = {
    import c.universe._

    val sentinelFrom = sentinelValue[A](c)
    val sentinelTo = sentinelValue[B](c)

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelFrom == $underlyingValue)
      $sentinelTo
    else
      $f($underlyingValue)
    """)
  }

  private def floatingPointTypeSentinelGuard[A: c.WeakTypeTag, B: c.WeakTypeTag](c: BlackboxContext)(underlyingValue: c.Expr[A], f: c.Expr[A => B]): c.Expr[B] = {
    import c.universe._

    val sentinelTo = sentinelValue[B](c)

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($underlyingValue != $underlyingValue)
      $sentinelTo
    else
      $f($underlyingValue)
    """)
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
