package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalMacros

object Longs {

  object OptionalLong {
    final def unapply(value: Long): OptionalLong = new OptionalLong(value)
  }

  final implicit class OptionalLong(val value: Long) extends AnyVal {
    def isEmpty = value == 0x8000000000000000L
    def get: Long = value

    def isMinValue = value == 0x8000000000000001L
    def isMaxValue = value == 0x7fffffffffffffffL

    def map[T](f: Long => T): T = macro OptionalMacros.map_impl[Long, T]
  }
}
