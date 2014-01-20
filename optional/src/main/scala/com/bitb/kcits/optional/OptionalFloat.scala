package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalMacros

object OptionalFloat {
  final def empty: OptionalFloat = new OptionalFloat(java.lang.Float.NaN)
  final def apply(value: Float): OptionalFloat = new OptionalFloat(value)
  final def unapply(value: Float): OptionalFloat = new OptionalFloat(value)
}

final class OptionalFloat(val value: Float) extends AnyVal {
  def isEmpty = value != value
  def get: Float = value

  def isMinValue = value == java.lang.Float.MIN_VALUE
  def isMaxValue = value == java.lang.Float.MAX_VALUE

  def map[T](f: Float => T): T = macro OptionalMacros.map_impl[Float, T]
  def foreach(f: Float => Unit): Unit = macro OptionalMacros.foreach_impl[Float]
}
