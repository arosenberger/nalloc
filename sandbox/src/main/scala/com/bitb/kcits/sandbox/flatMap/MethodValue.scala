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

package com.bitb.kcits.sandbox.flatMap

import com.bitb.kcits.optional._
import com.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {

  private[this] val bMethod = b.flatMap(addB)
  private[this] val sMethod = s.flatMap(addS)
  private[this] val iMethod = i.flatMap(addI)
  private[this] val lMethod = l.flatMap(addL)
  private[this] val fMethod = f.flatMap(addF)
  private[this] val dMethod = d.flatMap(addD)
  private[this] val stMethod = st.flatMap(toList)

  private def addB(value: Byte) = OptionalByte((value + 1).toByte)
  private def addS(value: Short) = OptionalShort((value + 1).toShort)
  private def addI(value: Int) = OptionalInt(value + 1)
  private def addL(value: Long) = OptionalLong(value + 1)
  private def addF(value: Float) = OptionalFloat(value + 1)
  private def addD(value: Double) = OptionalDouble(value + 1)
  private def toList(value: String) = Optional(List(value))

}
