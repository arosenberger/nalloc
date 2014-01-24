package com.bitb.kcits.sandbox.exists

import com.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {
  private[this] val be = b.exists(isZero)
  private[this] val se = s.exists(isZero)
  private[this] val ie = i.exists(isZero)
  private[this] val le = l.exists(isZero)
  private[this] val fe = f.exists(isZero)
  private[this] val de = d.exists(isZero)
  private[this] val ste = st.exists(isEmpty)

  private def isZero[T: Numeric](t: T) = implicitly[Numeric[T]].zero == t

  private def isEmpty(s: String) = s == ""
}
