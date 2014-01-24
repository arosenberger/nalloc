package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalMacros

object OptionalShort {
  final def empty: OptionalShort = new OptionalShort(-32768)
  final def apply(value: Short): OptionalShort = new OptionalShort(value)
  final def unapply(value: Short): OptionalShort = new OptionalShort(value)
}

final class OptionalShort(val value: Short) extends AnyVal {
  def isEmpty = value == -32768
  def get: Short = value

  def isMinValue = value == -32767
  def isMaxValue = value == 32767

  def map[T](f: Short => T): T = macro OptionalMacros.map_impl[Short, T]
  def foreach(f: Short => Unit): Unit = macro OptionalMacros.foreach_impl[Short]
  def exists(f: Short => Boolean): Boolean = macro OptionalMacros.exists_impl[Short]
}
