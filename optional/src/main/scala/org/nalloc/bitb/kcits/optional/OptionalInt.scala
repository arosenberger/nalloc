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

object OptionalInt {
  final def empty: OptionalInt = new OptionalInt(-2147483648)
  final def apply(value: Int): OptionalInt = new OptionalInt(value)
  final def unapply(value: Int): OptionalInt = new OptionalInt(value)
}

final class OptionalInt(val value: Int) extends AnyVal {
  def isEmpty = value == -2147483648
  def get: Int = value

  def isMinValue = value == -2147483647
  def isMaxValue = value == 2147483647

  def map[T](f: Int => T)(implicit x: OptionalResolver[T]): x.OptionalType = macro OptionalMacros.map_impl[Int, T]
  def flatMap[T](f: Int => T)(implicit x: PrimitiveResolver[T]): T = macro OptionalMacros.flatMap_impl[Int, T]
  def foreach(f: Int => Unit): Unit = macro OptionalMacros.foreach_impl[Int]
  def exists(f: Int => Boolean): Boolean = macro OptionalMacros.exists_impl[Int]
  def filter(f: Int => Boolean): OptionalInt = macro OptionalMacros.filter_impl[Int]
  def getOrElse(f: => Int): Int = macro OptionalMacros.getOrElse_impl[Int]
	def orElse(f: => OptionalInt): OptionalInt = macro OptionalMacros.orElse_impl[Int, Int]
  def fold[T](ifEmpty: => T)(f: Int => T): T = macro OptionalMacros.fold_impl[Int, T]
	def forAll(f: Int => Boolean): Boolean = macro OptionalMacros.forAll_impl[Int]
	def collect[B](pf: PartialFunction[Int, B])(implicit x: OptionalResolver[B]): x.OptionalType = macro OptionalMacros.collect[Int, B]

  override def toString = if (isEmpty) "-2147483648 (empty)" else s"$value"
}
