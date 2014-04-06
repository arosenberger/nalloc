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

object OptionalFloat {
  final def empty: OptionalFloat = new OptionalFloat(java.lang.Float.NaN)
  final def apply(value: Float): OptionalFloat = new OptionalFloat(value)
  final def unapply(value: Float): OptionalFloat = new OptionalFloat(value)
}

final class OptionalFloat(val value: Float) extends AnyVal {
  def isEmpty = value != value
  def get: Float = value
  def isNaN = value != value

  def isMinValue = value == java.lang.Float.MIN_VALUE
  def isMaxValue = value == java.lang.Float.MAX_VALUE

  def map[T](f: Float => T)(implicit x: OptionalResolver[T]): x.OptionalType = macro OptionalMacros.map_impl[Float, T]
  def flatMap[T](f: Float => T)(implicit x: PrimitiveResolver[T]): T = macro OptionalMacros.flatMap_impl[Float, T]
  def foreach(f: Float => Unit): Unit = macro OptionalMacros.foreach_impl[Float]
  def exists(f: Float => Boolean): Boolean = macro OptionalMacros.exists_impl[Float]
  def filter(f: Float => Boolean): OptionalFloat = macro OptionalMacros.filter_impl[Float]
  def getOrElse(f: => Float): Float = macro OptionalMacros.getOrElse_impl[Float]
	def orElse(f: => OptionalFloat): OptionalFloat = macro OptionalMacros.orElse_impl[Float, Float]
  def fold[T](ifEmpty: => T)(f: Float => T): T = macro OptionalMacros.fold_impl[Float, T]

  override def toString = if (isEmpty) s"${java.lang.Float.MIN_VALUE} (empty)" else s"$value"
}
