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

package org.nalloc.bitb.kcits.sandbox.flatMap

import org.nalloc.bitb.kcits.optional._
import org.nalloc.bitb.kcits.sandbox.Inspectable

class BlockInlineLambda extends Inspectable {

  private[this] val bInlineComplex = b.flatMap { x =>
    val y = x + 5
    OptionalInt(y * 3)
  }
  private[this] val sInlineComplex = s.flatMap { x =>
    val y = x + 5
    OptionalInt(y * 3)
  }
  private[this] val iInlineComplex = i.flatMap { x =>
    val y = x + 5
    OptionalInt(y * 3)
  }
  private[this] val lInlineComplex = l.flatMap { x =>
    val y = x + 5
    OptionalLong(y * 3)
  }
  private[this] val fInlineComplex = f.flatMap { x =>
    val y = x + 5
    OptionalFloat(y * 3)
  }
  private[this] val dInlineComplex = d.flatMap { x =>
    val y = x + 5
    OptionalDouble(y * 3)
  }
  private[this] val stInlineComplex = st.flatMap { x =>
    val y = x + x
    Optional(y + y)
  }
}
