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

class SimpleInlineLambda extends Inspectable {
  private[this] val bInline = b.flatMap(x => OptionalByte((x + 1).toByte))
  private[this] val sInline = s.flatMap(x => OptionalShort((x + 2).toShort))
  private[this] val iInline = i.flatMap(x => OptionalInt(x + 3))
  private[this] val lInline = l.flatMap(x => OptionalLong(x + 4))
  private[this] val fInline = f.flatMap(x => OptionalFloat(x + 5))
  private[this] val dInline = d.flatMap(x => OptionalDouble(x + 6))
  private[this] val stInline = st.flatMap(x => Optional(List(x)))
}
