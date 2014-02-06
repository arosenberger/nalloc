package com.bitb.kcits.sandbox.flatMap

import com.bitb.kcits.optional._
import com.bitb.kcits.sandbox.Inspectable

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
