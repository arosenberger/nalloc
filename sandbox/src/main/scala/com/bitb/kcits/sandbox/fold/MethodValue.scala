package com.bitb.kcits.sandbox.fold

import com.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {

  private[this] val bMethod = b.fold(1)(otherB)
  private[this] val sMethod = s.fold(2)(otherS)
  private[this] val iMethod = i.fold(3)(otherI)
  private[this] val lMethod = l.fold(4L)(otherL)
  private[this] val fMethod = f.fold(5f)(otherF)
  private[this] val dMethod = d.fold(6d)(otherD)
  private[this] val stMethod = st.fold("foo")(otherSt)

  private def otherB(b: Byte) = b + 1
  private def otherS(s: Short) = s + 2
  private def otherI(i: Int) = i + 3
  private def otherL(l: Long) = l + 4
  private def otherF(f: Float) = f + 5
  private def otherD(d: Double) = d + 6
  private def otherSt(s: String) = s + "foo"
}
