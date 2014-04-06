/*
 * Copyright 2014 Adam Rosenberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nalloc.bitb.kcits.macros

import scala.language.existentials
import scala.reflect.macros.blackbox._

private[kcits] trait OptionalResolver[T] {
  type OptionalType
}

private[kcits] trait PrimitiveResolver[T] {
  type PrimitiveType
}

object OptionalMacros {

  def map_impl[A: c.WeakTypeTag, B: c.WeakTypeTag](c: Context)(f: c.Expr[A => B])(x: c.Expr[OptionalResolver[B]]): c.Expr[x.value.OptionalType] = {
    import c.universe._

    val underlying = underlyingValue[A](c)
    val sentinelTo = sentinelValue[B](c)
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)

    val optionalType = x.tree.tpe.declarations
                       .find(x => x.isType && x.name == TypeName("OptionalType"))
                       .map(_.typeSignature)
                       .getOrElse(c.abort(c.enclosingPosition, "Couldn't determine optional type"))

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelGuard)
      new $optionalType($f($underlying))
    else
      new $optionalType($sentinelTo)
    """)
  }

  def flatMap_impl[A: c.WeakTypeTag, B: c.WeakTypeTag](c: Context)(f: c.Expr[A => B])(x: c.Expr[PrimitiveResolver[B]]): c.Expr[B] = {
    import c.universe._

    val underlying = underlyingValue[A](c)

    val primitiveType = x.tree.tpe.declarations
                        .find(x => x.isType && x.name == TypeName("PrimitiveType"))
                        .map(_.typeSignature)
                        .getOrElse(c.abort(c.enclosingPosition, "Couldn't determine optional type"))

    val sentinelTo = sentinelValueFor(c)(primitiveType)
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)
    val optionalType = c.macroApplication.tpe

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelGuard)
      $f($underlying)
    else
      new $optionalType($sentinelTo)
    """)
  }

  def foreach_impl[A: c.WeakTypeTag](c: Context)(f: c.Expr[A => Unit]): c.Expr[Unit] = {
    import c.universe._

    val underlying = underlyingValue[A](c)
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelGuard)
      $f($underlying)
    """)
  }

  def exists_impl[A: c.WeakTypeTag](c: Context)(f: c.Expr[A => Boolean]): c.Expr[Boolean] = {
    import c.universe._

    val underlying = underlyingValue[A](c)
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)

    new Inliner[c.type](c).inlineAndReset(q"$sentinelGuard && $f($underlying)")
  }

  def filter_impl[A: c.WeakTypeTag](c: Context)(f: c.Expr[A => Boolean]) = {
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

  def getOrElse_impl[A: c.WeakTypeTag](c: Context)(f: c.Expr[A]) = {
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

  def fold_impl[A: c.WeakTypeTag, B: c.WeakTypeTag](c: Context)(ifEmpty: c.Expr[B])(f: c.Expr[A => B]) = {
    import c.universe._

    val underlying = underlyingValue[A](c)
    val sentinelGuard = generateSentinelGuard[A](c)(underlying)

    new Inliner[c.type](c).inlineAndReset( q"""
    if ($sentinelGuard)
      $f($underlying)
    else
      $ifEmpty
    """)
  }

  private def underlyingValue[A: c.WeakTypeTag](c: Context) = {
    import c.universe._

    c.Expr[A](Select(c.prefix.tree, TermName("value")))
  }

  private def generateSentinelGuard[A: c.WeakTypeTag](c: Context)(underlying: c.Expr[A]) = {
    import c.universe._

    val sentinel = sentinelValue[A](c)

    if (isFloatingPointType[A](c)) q"$underlying == $underlying"
    else q"$sentinel != $underlying"
  }

  private def isFloatingPointType[A: c.WeakTypeTag](c: Context) = c.weakTypeTag[A].tpe match {
    case x if x =:= c.WeakTypeTag.Float.tpe || x =:= c.WeakTypeTag.Double.tpe => true
    case _                                                                    => false
  }

  private def sentinelValue[X: c.WeakTypeTag](c: Context): c.universe.Tree =
    sentinelValueFor(c)(c.weakTypeTag[X].tpe)

  private def sentinelValueFor(c: Context)(underlyingType: c.universe.Type): c.universe.Tree = {
    import c.universe._

    underlyingType match {
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
