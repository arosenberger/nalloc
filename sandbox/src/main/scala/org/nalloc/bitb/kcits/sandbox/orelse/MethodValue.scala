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

package org.nalloc.bitb.kcits.sandbox.orelse

import org.nalloc.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {

  private[this] val bMethod = b.getOrElse(otherB)
  private[this] val sMethod = s.getOrElse(otherS)
  private[this] val iMethod = i.getOrElse(otherI)
  private[this] val lMethod = l.getOrElse(otherL)
  private[this] val fMethod = f.getOrElse(otherF)
  private[this] val dMethod = d.getOrElse(otherD)
  private[this] val stMethod = st.getOrElse(otherSt)

  private def otherB = 1.toByte
  private def otherS = 1.toShort
  private def otherI = 1
  private def otherL = 1
  private def otherF = 1
  private def otherD = 1
  private def otherSt = "foo"
}
