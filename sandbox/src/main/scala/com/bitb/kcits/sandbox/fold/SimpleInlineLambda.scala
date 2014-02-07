package com.bitb.kcits.sandbox.fold

import com.bitb.kcits.sandbox.Inspectable

class SimpleInlineLambda extends Inspectable {
  private[this] val bInline = b.fold(1)(_ + 1)
  private[this] val sInline = s.fold(2)(_ + 1)
  private[this] val iInline = i.fold(3)(_ + 1)
  private[this] val lInline = l.fold(4L)(_ + 1L)
  private[this] val fInline = f.fold(5f)(_ + 1f)
  private[this] val dInline = d.fold(6d)(_ + 1d)
  private[this] val stInline = st.fold("foo")(_ + "foo")
}
