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

object Optional {
  final private[this] val None = new Optional[Null](null)

  def empty[A >: Null]: Optional[A] = None
  def apply[A >: Null](value: A): Optional[A] = if (value == null) None else new Optional[A](value)
  def unapply[A >: Null](value: A): Optional[A] = if (value == null) None else Optional[A](value)
}

final class Optional[+A >: Null](val value: A) extends AnyVal {
  def get: A = value
  def isEmpty = value == null

  def map[B](f: A => B)(implicit x: OptionalResolver[B]): x.OptionalType = macro OptionalMacros.map_impl[A, B]
  def flatMap[B](f: A => B)(implicit x: PrimitiveResolver[B]): B = macro OptionalMacros.flatMap_impl[A, B]
  def foreach(f: A => Unit): Unit = macro OptionalMacros.foreach_impl[A]
  def exists(f: A => Boolean): Boolean = macro OptionalMacros.exists_impl[A]
  def filter(f: A => Boolean): Optional[A] = macro OptionalMacros.filter_impl[A]
  def orElse(f: => A): A = macro OptionalMacros.orElse_impl[A]
  def fold[B](ifEmpty: => B)(f: A => B): B = macro OptionalMacros.fold_impl[A, B]
}
