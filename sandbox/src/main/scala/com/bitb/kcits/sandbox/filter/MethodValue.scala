package com.bitb.kcits.sandbox.filter

import com.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {
  private[this] val be = b.filter(isZero)
  private[this] val se = s.filter(isZero)
  private[this] val ie = i.filter(isZero)
  private[this] val le = l.filter(isZero)
  private[this] val fe = f.filter(isZero)
  private[this] val de = d.filter(isZero)
  private[this] val ste = st.filter(isEmpty)

  private def isZero[T: Numeric](t: T) = implicitly[Numeric[T]].zero == t

  private def isEmpty(s: String) = s == ""
}
