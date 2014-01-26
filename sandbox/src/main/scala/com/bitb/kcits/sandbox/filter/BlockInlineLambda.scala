package com.bitb.kcits.sandbox.filter

import com.bitb.kcits.sandbox.Inspectable

class BlockInlineLambda extends Inspectable {
  private[this] val be = b.filter(x => {
    println(x)
    x == 0
  })
  private[this] val se = s.filter(x => {
    println(x)
    x == 0
  })
  private[this] val ie = i.filter(x => {
    println(x)
    x == 0
  })
  private[this] val le = l.filter(x => {
    println(x)
    x == 0
  })
  private[this] val fe = f.filter(x => {
    println(x)
    x == 0
  })
  private[this] val de = d.filter(x => {
    println(x)
    x == 0
  })
  private[this] val ste = st.filter(x => {
    println(x)
    x == ""
  })
}
