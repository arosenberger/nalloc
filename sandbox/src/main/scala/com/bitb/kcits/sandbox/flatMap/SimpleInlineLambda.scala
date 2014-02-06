package com.bitb.kcits.sandbox.flatMap

import com.bitb.kcits.optional._
import com.bitb.kcits.sandbox.Inspectable

class SimpleInlineLambda extends Inspectable {
  private[this] val bInline = b.flatMap(x => OptionalByte((x + 1).toByte))
  private[this] val sInline = s.flatMap(x => OptionalShort((x + 2).toShort))
  private[this] val iInline = i.flatMap(x => OptionalInt(x + 3))
  private[this] val lInline = l.flatMap(x => OptionalLong(x + 4))
  private[this] val fInline = f.flatMap(x => OptionalFloat(x + 5))
  private[this] val dInline = d.flatMap(x => OptionalDouble(x + 6))
  private[this] val stInline = st.flatMap(x => Optional(List(x)))
}
