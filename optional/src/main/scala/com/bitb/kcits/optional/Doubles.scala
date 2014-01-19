package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalMacros

object Doubles {

  object OptionalDouble {
    final def unapply(value: Double): OptionalDouble = new OptionalDouble(value)
  }

  final implicit class OptionalDouble(val value: Double) extends AnyVal {
    def isEmpty = value != value
    def get: Double = value

    def isMinValue = value == java.lang.Double.MIN_VALUE
    def isMaxValue = value == java.lang.Double.MAX_VALUE

    def map[T](f: Double => T): T = macro OptionalMacros.map_impl[Double, T]
  }
}
