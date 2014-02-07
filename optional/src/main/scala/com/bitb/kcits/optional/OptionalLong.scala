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

package com.bitb.kcits.optional

import com.bitb.kcits.macros._

object OptionalLong extends OptionalResolverImplicits {
  final def empty: OptionalLong = new OptionalLong(0x8000000000000000L)
  final def apply(value: Long): OptionalLong = new OptionalLong(value)
  final def unapply(value: Long): OptionalLong = new OptionalLong(value)
}

final class OptionalLong(val value: Long) extends AnyVal {
  def isEmpty = value == 0x8000000000000000L
  def get: Long = value

  def isMinValue = value == 0x8000000000000001L
  def isMaxValue = value == 0x7fffffffffffffffL

  def map[T](f: Long => T)(implicit x: OptionalResolver[T]): x.OptionalType = macro OptionalMacros.map_impl[Long, T]
  def flatMap[T](f: Long => T)(implicit x: PrimitiveResolver[T]): T = macro OptionalMacros.flatMap_impl[Long, T]
  def foreach(f: Long => Unit): Unit = macro OptionalMacros.foreach_impl[Long]
  def exists(f: Long => Boolean): Boolean = macro OptionalMacros.exists_impl[Long]
  def filter(f: Long => Boolean): OptionalLong = macro OptionalMacros.filter_impl[Long]
  def orElse(f: => Long): Long = macro OptionalMacros.orElse_impl[Long]
  def fold[T](ifEmpty: => T)(f: Long => T): T = macro OptionalMacros.fold_impl[Long, T]
}
