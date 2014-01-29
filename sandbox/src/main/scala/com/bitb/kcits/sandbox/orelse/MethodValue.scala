package com.bitb.kcits.sandbox.orelse

import com.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {

  private[this] val bMethod = b.orElse(otherB)
  private[this] val sMethod = s.orElse(otherS)
  private[this] val iMethod = i.orElse(otherI)
  private[this] val lMethod = l.orElse(otherL)
  private[this] val fMethod = f.orElse(otherF)
  private[this] val dMethod = d.orElse(otherD)
  private[this] val stMethod = st.orElse(otherSt)

  private def otherB = 1.toByte
  private def otherS = 1.toShort
  private def otherI = 1
  private def otherL = 1
  private def otherF = 1
  private def otherD = 1
  private def otherSt = "foo"
}
