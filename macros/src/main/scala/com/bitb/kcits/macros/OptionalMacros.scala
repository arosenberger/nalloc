package com.bitb.kcits.macros

import scala.language.existentials
import scala.reflect.macros._

object OptionalMacros {

  def map_impl[A: c.WeakTypeTag, B: c.WeakTypeTag](c: BlackboxContext)(f: c.Expr[A => B]): c.Expr[B] = {
    import c.universe._

    val underlying = underlyingValue[A](c)
    val sentinelTo = sentinelValue[B](c)
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelGuard)
      $f($underlying)
    else
      $sentinelTo
    """)
  }

  def foreach_impl[A: c.WeakTypeTag](c: BlackboxContext)(f: c.Expr[A => Unit]): c.Expr[Unit] = {
    import c.universe._

    val underlying = underlyingValue[A](c)
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelGuard)
      $f($underlying)
    """)
  }

  def exists_impl[A: c.WeakTypeTag](c: BlackboxContext)(f: c.Expr[A => Boolean]): c.Expr[Boolean] = {
    import c.universe._

    val underlying = underlyingValue[A](c)
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)

    new Inliner[c.type](c).inlineAndReset(q"$sentinelGuard && $f($underlying)")
  }

  def filter_impl[A: c.WeakTypeTag](c: BlackboxContext)(f: c.Expr[A => Boolean]) = {
    import c.universe._

    val underlying = underlyingValue[A](c)
    val sentinel = sentinelValue[A](c)
    val optionalType = c.macroApplication.tpe
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelGuard && $f($underlying))
      ${c.prefix.tree}
    else
      new $optionalType($sentinel)
    """)
  }

  def getOrElse_impl[A: c.WeakTypeTag](c: BlackboxContext)(f: c.Expr[A]) = {
    import c.universe._

    val underlying = underlyingValue[A](c)
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelGuard)
      $underlying
    else
      $f
    """)
  }

  private def underlyingValue[A: c.WeakTypeTag](c: BlackboxContext) = {
    import c.universe._

    c.Expr[A](Select(c.prefix.tree, TermName("value")))
  }

  private def generateSentinelGuard[A: c.WeakTypeTag](c: BlackboxContext)(underlying: c.Expr[A]) = {
    import c.universe._

    val sentinel = sentinelValue[A](c)

    if (isFloatingPointType[A](c)) q"$underlying == $underlying"
    else q"$sentinel != $underlying"
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
