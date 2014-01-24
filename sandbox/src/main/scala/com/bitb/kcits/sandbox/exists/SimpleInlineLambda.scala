package com.bitb.kcits.sandbox.exists

import com.bitb.kcits.sandbox.Inspectable

class SimpleInlineLambda extends Inspectable {
  private[this] val be = b.exists(x => x == 0)
  private[this] val se = s.exists(x => x == 0)
  private[this] val ie = i.exists(x => x == 0)
  private[this] val le = l.exists(x => x == 0)
  private[this] val fe = f.exists(x => x == 0)
  private[this] val de = d.exists(x => x == 0)
  private[this] val ste = st.exists(x => x == "")
}
