package com.bitb.kcits.sandbox.orelse

import com.bitb.kcits.sandbox.Inspectable

class SimpleInlineLambda extends Inspectable {
  private[this] val bInline = b.orElse(1)
  private[this] val sInline = s.orElse(2)
  private[this] val iInline = i.orElse(3)
  private[this] val lInline = l.orElse(4)
  private[this] val fInline = f.orElse(5)
  private[this] val dInline = d.orElse(6)
  private[this] val stInline = st.orElse("foo")
}
