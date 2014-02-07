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

package com.bitb.kcits.sandbox.fold

import com.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {

  private[this] val bMethod = b.fold(1)(otherB)
  private[this] val sMethod = s.fold(2)(otherS)
  private[this] val iMethod = i.fold(3)(otherI)
  private[this] val lMethod = l.fold(4L)(otherL)
  private[this] val fMethod = f.fold(5f)(otherF)
  private[this] val dMethod = d.fold(6d)(otherD)
  private[this] val stMethod = st.fold("foo")(otherSt)

  private def otherB(b: Byte) = b + 1
  private def otherS(s: Short) = s + 2
  private def otherI(i: Int) = i + 3
  private def otherL(l: Long) = l + 4
  private def otherF(f: Float) = f + 5
  private def otherD(d: Double) = d + 6
  private def otherSt(s: String) = s + "foo"
}
