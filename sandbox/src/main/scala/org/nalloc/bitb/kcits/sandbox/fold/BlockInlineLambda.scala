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

class BlockInlineLambda extends Inspectable {

  private[this] val bInlineComplex = b.fold(1) { x =>
    val y = 5
    y * 3 + x
  }
  private[this] val sInlineComplex = s.fold(2) { x =>
    val y = 5
    y * 3 + x
  }
  private[this] val iInlineComplex = i.fold(3) { x =>
    val y = 5
    y * 3 + x
  }
  private[this] val lInlineComplex = l.fold(4L) { x =>
    val y = 5
    y * 3 + x
  }
  private[this] val fInlineComplex = f.fold(5f) { x =>
    val y = 5
    y * 3 + x
  }
  private[this] val dInlineComplex = d.fold(6d) { x =>
    val y = 5
    y * 3 + x
  }
  private[this] val stInlineComplex = st.fold("bar") { x =>
    val y = "foo"
    y + y + x
  }
}
