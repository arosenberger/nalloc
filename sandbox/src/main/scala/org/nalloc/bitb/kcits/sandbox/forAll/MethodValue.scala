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

package org.nalloc.bitb.kcits.sandbox.forAll

import org.nalloc.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {
  private[this] val be = b.forAll(isZero)
  private[this] val se = s.forAll(isZero)
  private[this] val ie = i.forAll(isZero)
  private[this] val le = l.forAll(isZero)
  private[this] val fe = f.forAll(isZero)
  private[this] val de = d.forAll(isZero)
  private[this] val ste = st.forAll(isEmpty)

  private def isZero[T: Numeric](t: T) = implicitly[Numeric[T]].zero == t

  private def isEmpty(s: String) = s == ""
}
