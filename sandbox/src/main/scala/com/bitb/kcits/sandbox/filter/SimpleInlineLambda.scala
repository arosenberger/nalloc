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

package com.bitb.kcits.sandbox.filter

import com.bitb.kcits.sandbox.Inspectable

class SimpleInlineLambda extends Inspectable {
  private[this] val be = b.filter(x => x == 0)
  private[this] val se = s.filter(x => x == 0)
  private[this] val ie = i.filter(x => x == 0)
  private[this] val le = l.filter(x => x == 0)
  private[this] val fe = f.filter(x => x == 0)
  private[this] val de = d.filter(x => x == 0)
  private[this] val ste = st.filter(x => x == "")
}
