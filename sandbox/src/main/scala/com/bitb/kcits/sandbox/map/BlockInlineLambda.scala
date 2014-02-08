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

import com.bitb.kcits.optional._
import com.bitb.kcits.sandbox.Inspectable

class BlockInlineLambda extends Inspectable {

  private[this] val bInlineComplex = b.map { x =>
    val y = x + 5
    y * 3
  }
  private[this] val sInlineComplex = s.map { x =>
    val y = x + 5
    y * 3
  }
  private[this] val iInlineComplex = i.map { x =>
    val y = x + 5
    y * 3
  }
  private[this] val lInlineComplex = l.map { x =>
    val y = x + 5
    y * 3
  }
  private[this] val fInlineComplex = f.map { x =>
    val y = x + 5
    y * 3
  }
  private[this] val dInlineComplex = d.map { x =>
    val y = x + 5
    y * 3
  }
  private[this] val stInlineComplex = st.map { x =>
    val y = x + x
    y + y
  }
}
