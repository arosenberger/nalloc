package com.bitb.kcits.sandbox.foreach

import com.bitb.kcits.sandbox.Inspectable

class BlockInlineLambda extends Inspectable {
  b.foreach(x => {
    println(x)
    println(x + 1)
  })
  s.foreach(x => {
    println(x)
    println(x + 1)
  })
  i.foreach(x => {
    println(x)
    println(x + 1)
  })
  l.foreach(x => {
    println(x)
    println(x + 1)
  })
  f.foreach(x => {
    println(x)
    println(x + 1)
  })
  d.foreach(x => {
    println(x)
    println(x + 1)
  })
  st.foreach(x => {
    println(x)
    println(x + 1)
  })
}
