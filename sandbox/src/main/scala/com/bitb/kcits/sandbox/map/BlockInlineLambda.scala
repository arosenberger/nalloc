package com.bitb.kcits.sandbox.map

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
}
