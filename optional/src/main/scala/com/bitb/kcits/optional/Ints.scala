package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalMacros

object Ints {

  object OptionalInt {
    final def unapply(value: Int): OptionalInt = new OptionalInt(value)
  }

  final implicit class OptionalInt(val value: Int) extends AnyVal {
    def isEmpty = value == -2147483648
    def get: Int = value

    def isMinValue = value == -2147483647
    def isMaxValue = value == 2147483647

    def map[T](f: Int => T): T = macro OptionalMacros.map_impl[Int, T]
  }
}
