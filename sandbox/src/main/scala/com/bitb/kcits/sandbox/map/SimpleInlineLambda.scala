package com.bitb.kcits.sandbox.map

import com.bitb.kcits.sandbox.Inspectable

class SimpleInlineLambda extends Inspectable {
  private[this] val bInline = b.map(_ + 1)
  private[this] val sInline = s.map(_ + 2)
  private[this] val iInline = i.map(_ + 3)
  private[this] val lInline = l.map(_ + 4)
  private[this] val fInline = f.map(_ + 5)
  private[this] val dInline = d.map(_ + 6)
  private[this] val stInline = st.map(List(_))
}
