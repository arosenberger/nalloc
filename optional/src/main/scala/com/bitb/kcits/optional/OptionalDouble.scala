package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalMacros

object OptionalDouble {
  final def empty: OptionalDouble = new OptionalDouble(java.lang.Double.NaN)
  final def apply(value: Double): OptionalDouble = new OptionalDouble(value)
  final def unapply(value: Double): OptionalDouble = new OptionalDouble(value)
}

final class OptionalDouble(val value: Double) extends AnyVal {
  def isEmpty = value != value
  def get: Double = value

  def isMinValue = value == java.lang.Double.MIN_VALUE
  def isMaxValue = value == java.lang.Double.MAX_VALUE

  def map[T](f: Double => T): T = macro OptionalMacros.map_impl[Double, T]
  def foreach(f: Double => Unit): Unit = macro OptionalMacros.foreach_impl[Double]
}
