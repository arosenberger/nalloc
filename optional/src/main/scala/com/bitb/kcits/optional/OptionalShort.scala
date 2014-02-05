package com.bitb.kcits.optional

import com.bitb.kcits.macros._

object OptionalShort extends OptionalImplicits {
  final def empty: OptionalShort = new OptionalShort(-32768)
  final def apply(value: Short): OptionalShort = new OptionalShort(value)
  final def unapply(value: Short): OptionalShort = new OptionalShort(value)
}

final class OptionalShort(val value: Short) extends AnyVal {
  def isEmpty = value == -32768
  def get: Short = value

  def isMinValue = value == -32767
  def isMaxValue = value == 32767

  def map[T](f: Short => T)(implicit x: OptionalResolver[T]): x.OptionalType = macro OptionalMacros.map_impl[Short, T]
  def foreach(f: Short => Unit): Unit = macro OptionalMacros.foreach_impl[Short]
  def exists(f: Short => Boolean): Boolean = macro OptionalMacros.exists_impl[Short]
  def filter(f: Short => Boolean): OptionalShort = macro OptionalMacros.filter_impl[Short]
  def orElse(f: => Short): Short = macro OptionalMacros.getOrElse_impl[Short]
}
