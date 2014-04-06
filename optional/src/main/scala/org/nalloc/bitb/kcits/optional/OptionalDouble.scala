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

object OptionalDouble {
  final def empty: OptionalDouble = new OptionalDouble(java.lang.Double.NaN)
  final def apply(value: Double): OptionalDouble = new OptionalDouble(value)
  final def unapply(value: Double): OptionalDouble = new OptionalDouble(value)
}

final class OptionalDouble(val value: Double) extends AnyVal {
  def isEmpty = value != value
  def get: Double = value
  def isNaN = value != value

  def isMinValue = value == java.lang.Double.MIN_VALUE
  def isMaxValue = value == java.lang.Double.MAX_VALUE

  def map[T](f: Double => T)(implicit x: OptionalResolver[T]): x.OptionalType = macro OptionalMacros.map_impl[Double, T]
  def flatMap[T](f: Double => T)(implicit x: PrimitiveResolver[T]): T = macro OptionalMacros.flatMap_impl[Double, T]
  def foreach(f: Double => Unit): Unit = macro OptionalMacros.foreach_impl[Double]
  def exists(f: Double => Boolean): Boolean = macro OptionalMacros.exists_impl[Double]
  def filter(f: Double => Boolean): OptionalDouble = macro OptionalMacros.filter_impl[Double]
  def getOrElse(f: => Double): Double = macro OptionalMacros.getOrElse_impl[Double]
	def orElse(f: => OptionalDouble): OptionalDouble = macro OptionalMacros.orElse_impl[Double, Double]
  def fold[T](ifEmpty: => T)(f: Double => T): T = macro OptionalMacros.fold_impl[Double, T]
	def forAll(f: Double => Boolean): Boolean = macro OptionalMacros.forAll_impl[Double]
	def collect[B](pf: PartialFunction[Double, B])(implicit x: OptionalResolver[B]): x.OptionalType = macro OptionalMacros.collect[Double, B]

  override def toString = if (isEmpty) s"${java.lang.Double.MIN_VALUE} (empty)" else s"$value"
}
