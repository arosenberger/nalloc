package com.bitb.kcits.optional

import com.bitb.kcits.macros._

object OptionalFloat extends OptionalImplicits {
  final def empty: OptionalFloat = new OptionalFloat(java.lang.Float.NaN)
  final def apply(value: Float): OptionalFloat = new OptionalFloat(value)
  final def unapply(value: Float): OptionalFloat = new OptionalFloat(value)
}

final class OptionalFloat(val value: Float) extends AnyVal {
  def isEmpty = value != value
  def get: Float = value
  def isNaN = value.isNaN

  def isMinValue = value == java.lang.Float.MIN_VALUE
  def isMaxValue = value == java.lang.Float.MAX_VALUE

  def map[T](f: Float => T)(implicit x: OptionalResolver[T]): x.OptionalType = macro OptionalMacros.map_impl[Float, T]
  def foreach(f: Float => Unit): Unit = macro OptionalMacros.foreach_impl[Float]
  def exists(f: Float => Boolean): Boolean = macro OptionalMacros.exists_impl[Float]
  def filter(f: Float => Boolean): OptionalFloat = macro OptionalMacros.filter_impl[Float]
  def orElse(f: => Float): Float = macro OptionalMacros.getOrElse_impl[Float]
}
