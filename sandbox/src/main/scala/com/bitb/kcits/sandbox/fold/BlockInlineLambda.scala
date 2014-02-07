package com.bitb.kcits.sandbox.fold

import com.bitb.kcits.sandbox.Inspectable

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
