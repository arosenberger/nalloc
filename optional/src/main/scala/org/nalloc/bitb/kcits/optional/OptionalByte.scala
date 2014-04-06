/*
 * Copyright 2014 Adam Rosenberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nalloc.bitb.kcits.optional

import org.nalloc.bitb.kcits.macros._

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

  def map[T](f: Byte => T)(implicit x: OptionalResolver[T]): x.OptionalType = macro OptionalMacros.map_impl[Byte, T]
  def flatMap[T](f: Byte => T)(implicit x: PrimitiveResolver[T]): T = macro OptionalMacros.flatMap_impl[Byte, T]
  def foreach(f: Byte => Unit): Unit = macro OptionalMacros.foreach_impl[Byte]
  def exists(f: Byte => Boolean): Boolean = macro OptionalMacros.exists_impl[Byte]
  def filter(f: Byte => Boolean): OptionalByte = macro OptionalMacros.filter_impl[Byte]
  def getOrElse(f: => Byte): Byte = macro OptionalMacros.getOrElse_impl[Byte]
  def orElse(f: => OptionalByte): OptionalByte = macro OptionalMacros.orElse_impl[Byte, Byte]
  def fold[T](ifEmpty: => T)(f: Byte => T): T = macro OptionalMacros.fold_impl[Byte, T]
	def forAll(f: Byte => Boolean): Boolean = macro OptionalMacros.forAll_impl[Byte]

  override def toString = if (isEmpty) "-128 (empty)" else s"$value"
}
