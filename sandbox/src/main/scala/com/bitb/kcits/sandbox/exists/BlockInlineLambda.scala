package com.bitb.kcits.sandbox.exists

import com.bitb.kcits.sandbox.Inspectable

class BlockInlineLambda extends Inspectable {
  private[this] val be = b.exists(x => {
    println(x)
    x == 0
  })
  private[this] val se = s.exists(x => {
    println(x)
    x == 0
  })
  private[this] val ie = i.exists(x => {
    println(x)
    x == 0
  })
  private[this] val le = l.exists(x => {
    println(x)
    x == 0
  })
  private[this] val fe = f.exists(x => {
    println(x)
    x == 0
  })
  private[this] val de = d.exists(x => {
    println(x)
    x == 0
  })
  private[this] val ste = st.exists(x => {
    println(x)
    x == ""
  })
}
