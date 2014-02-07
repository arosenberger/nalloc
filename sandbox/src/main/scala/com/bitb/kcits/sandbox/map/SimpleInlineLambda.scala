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

package com.bitb.kcits.sandbox.map

import com.bitb.kcits.sandbox.Inspectable

class SimpleInlineLambda extends Inspectable {
  private[this] val bInline = b.map(_ + 1)
  private[this] val sInline = s.map(_ + 2)
  private[this] val iInline = i.map(_ + 3)
  private[this] val lInline = l.map(_ + 4)
  private[this] val fInline = f.map(_ + 5)
  private[this] val dInline = d.map(_ + 6)
  private[this] val stInline = st.map(List(_))
}
