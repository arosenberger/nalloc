package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalMacros

object Shorts {

  object OptionalShort {
    final def unapply(value: Short): OptionalShort = new OptionalShort(value)
  }

  final implicit class OptionalShort(val value: Short) extends AnyVal {
    def isEmpty = value == -32768
    def get: Short = value

    def isMinValue = value == -32767
    def isMaxValue = value == 32767

    def map[T](f: Short => T): T = macro OptionalMacros.map_impl[Short, T]
  }
}
