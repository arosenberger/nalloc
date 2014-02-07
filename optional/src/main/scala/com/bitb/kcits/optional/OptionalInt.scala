package com.bitb.kcits.optional

import com.bitb.kcits.macros._

object OptionalInt extends OptionalResolverImplicits {
  final def empty: OptionalInt = new OptionalInt(-2147483648)
  final def apply(value: Int): OptionalInt = new OptionalInt(value)
  final def unapply(value: Int): OptionalInt = new OptionalInt(value)
}

final class OptionalInt(val value: Int) extends AnyVal {
  def isEmpty = value == -2147483648
  def get: Int = value

  def isMinValue = value == -2147483647
  def isMaxValue = value == 2147483647

  def map[T](f: Int => T)(implicit x: OptionalResolver[T]): x.OptionalType = macro OptionalMacros.map_impl[Int, T]
  def flatMap[T](f: Int => T)(implicit x: PrimitiveResolver[T]): T = macro OptionalMacros.flatMap_impl[Int, T]
  def foreach(f: Int => Unit): Unit = macro OptionalMacros.foreach_impl[Int]
  def exists(f: Int => Boolean): Boolean = macro OptionalMacros.exists_impl[Int]
  def filter(f: Int => Boolean): OptionalInt = macro OptionalMacros.filter_impl[Int]
  def orElse(f: => Int): Int = macro OptionalMacros.orElse_impl[Int]
  def fold[T](ifEmpty: => T)(f: Int => T): T = macro OptionalMacros.fold_impl[Int, T]
}
