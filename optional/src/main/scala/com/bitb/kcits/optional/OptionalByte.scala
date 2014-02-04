package com.bitb.kcits.optional

import com.bitb.kcits.macros._

object OptionalByte extends OptionalImplicits {
  final def empty: OptionalByte = new OptionalByte(-128)
  final def apply(value: Byte): OptionalByte = new OptionalByte(value)
  final def unapply(value: Byte): OptionalByte = new OptionalByte(value)

}

final class OptionalByte(val value: Byte) extends AnyVal {
  def isEmpty = value == -128
  def get: Byte = value

  def isMinValue = value == -127
  def isMaxValue = value == 127

  def map[T: OptionalResolver](f: Byte => T): OptionalResolver[T]#OptionalType = macro OptionalMacros.map_impl[Byte, T]
  def foreach(f: Byte => Unit): Unit = macro OptionalMacros.foreach_impl[Byte]
  def exists(f: Byte => Boolean): Boolean = macro OptionalMacros.exists_impl[Byte]
  def filter(f: Byte => Boolean): OptionalByte = macro OptionalMacros.filter_impl[Byte]
  def orElse(f: => Byte): Byte = macro OptionalMacros.getOrElse_impl[Byte]
}