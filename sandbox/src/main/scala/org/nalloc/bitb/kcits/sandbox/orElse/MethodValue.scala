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

package org.nalloc.bitb.kcits.sandbox.orElse

import org.nalloc.bitb.kcits.optional._
import org.nalloc.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {

	private[this] val bMethod = b.orElse(otherB)
	private[this] val sMethod = s.orElse(otherS)
	private[this] val iMethod = i.orElse(otherI)
	private[this] val lMethod = l.orElse(otherL)
	private[this] val fMethod = f.orElse(otherF)
	private[this] val dMethod = d.orElse(otherD)
	private[this] val stMethod = st.orElse(otherSt)

	private def otherB = OptionalByte(1.toByte)
	private def otherS = OptionalShort(1.toShort)
	private def otherI = OptionalInt(1)
	private def otherL = OptionalLong(1)
	private def otherF = OptionalFloat(1)
	private def otherD = OptionalDouble(1)
	private def otherSt = Optional("foo")
}
