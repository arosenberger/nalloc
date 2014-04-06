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

  def map[T](f: Short => T)(implicit x: OptionalResolver[T]): x.OptionalType = macro OptionalMacros.map_impl[Short, T]
  def flatMap[T](f: Short => T)(implicit x: PrimitiveResolver[T]): T = macro OptionalMacros.flatMap_impl[Short, T]
  def foreach(f: Short => Unit): Unit = macro OptionalMacros.foreach_impl[Short]
  def exists(f: Short => Boolean): Boolean = macro OptionalMacros.exists_impl[Short]
  def filter(f: Short => Boolean): OptionalShort = macro OptionalMacros.filter_impl[Short]
  def getOrElse(f: => Short): Short = macro OptionalMacros.getOrElse_impl[Short]
	def orElse(f: => OptionalShort): OptionalShort = macro OptionalMacros.orElse_impl[Short, Short]
  def fold[T](ifEmpty: => T)(f: Short => T): T = macro OptionalMacros.fold_impl[Short, T]
	def forAll(f: Short => Boolean): Boolean = macro OptionalMacros.forAll_impl[Short]
	def collect[B](pf: PartialFunction[Short, B])(implicit x: OptionalResolver[B]): x.OptionalType = macro OptionalMacros.collect[Short, B]

  override def toString = if (isEmpty) "-32768 (empty)" else s"$value"
}
