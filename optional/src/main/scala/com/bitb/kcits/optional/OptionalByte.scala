package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalMacros

object OptionalByte {
  final def empty: OptionalByte = new OptionalByte(-128)
  final def apply(value: Byte): OptionalByte = new OptionalByte(value)
  final def unapply(value: Byte): OptionalByte = new OptionalByte(value)
}

final class OptionalByte(val value: Byte) extends AnyVal {
  def isEmpty = value == -128
  def get: Byte = value

  def isMinValue = value == -127
  def isMaxValue = value == 127

  def map[T](f: Byte => T): T = macro OptionalMacros.map_impl[Byte, T]
}

