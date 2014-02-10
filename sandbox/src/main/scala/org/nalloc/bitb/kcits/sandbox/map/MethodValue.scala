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

package org.nalloc.bitb.kcits.sandbox.map

import org.nalloc.bitb.kcits.optional._
import org.nalloc.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {

  private[this] val bMethod = b.map(addB)
  private[this] val sMethod = s.map(addS)
  private[this] val iMethod = i.map(addI)
  private[this] val lMethod = l.map(addL)
  private[this] val fMethod = f.map(addF)
  private[this] val dMethod = d.map(addD)
  private[this] val stMethod = st.map(toList)

  private def addB(value: Byte) = value + 1
  private def addS(value: Short) = value + 1
  private def addI(value: Int) = value + 1
  private def addL(value: Long) = value + 1
  private def addF(value: Float) = value + 1
  private def addD(value: Double) = value + 1
  private def toList(value: String) = List(value)

}
