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

package com.bitb.kcits.sandbox

import com.bitb.kcits.optional._
import java.util.Random

abstract class Inspectable extends OptionalResolverImplicits {
  private[this] val random = new Random()

  protected[this] val b = OptionalByte(random.nextInt().toByte)
  protected[this] val s = OptionalShort(random.nextInt().toShort)
  protected[this] val i = OptionalInt(random.nextInt())
  protected[this] val l = OptionalLong(random.nextLong())
  protected[this] val f = OptionalFloat(random.nextFloat())
  protected[this] val d = OptionalDouble(random.nextDouble())
  protected[this] val st = Optional(random.nextDouble().toString)
}
