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

package org.nalloc.bitb.kcits.sandbox.fold

import org.nalloc.bitb.kcits.sandbox.Inspectable

class SimpleInlineLambda extends Inspectable {
  private[this] val bInline = b.fold(1)(_ + 1)
  private[this] val sInline = s.fold(2)(_ + 1)
  private[this] val iInline = i.fold(3)(_ + 1)
  private[this] val lInline = l.fold(4L)(_ + 1L)
  private[this] val fInline = f.fold(5f)(_ + 1f)
  private[this] val dInline = d.fold(6d)(_ + 1d)
  private[this] val stInline = st.fold("foo")(_ + "foo")
}
