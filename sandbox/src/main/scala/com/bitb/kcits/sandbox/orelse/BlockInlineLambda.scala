package com.bitb.kcits.sandbox.orelse

import com.bitb.kcits.sandbox.Inspectable

class BlockInlineLambda extends Inspectable {

  private[this] val bInlineComplex = b.orElse {
    val y = 5
    (y * 3).toByte
  }
  private[this] val sInlineComplex = s.orElse {
    val y = 5
    (y * 3).toShort
  }
  private[this] val iInlineComplex = i.orElse {
    val y = 5
    y * 3
  }
  private[this] val lInlineComplex = l.orElse {
    val y = 5
    y * 3
  }
  private[this] val fInlineComplex = f.orElse {
    val y = 5
    y * 3
  }
  private[this] val dInlineComplex = d.orElse {
    val y = 5
    y * 3
  }
  private[this] val stInlineComplex = st.orElse {
    val y = "foo"
    y + y
  }
}
