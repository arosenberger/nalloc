package com.bitb.kcits.sandbox.map

import com.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {

  private[this] val bMethod = b.map(addB)
  private[this] val sMethod = s.map(addS)
  private[this] val iMethod = i.map(addI)
  private[this] val lMethod = l.map(addL)
  private[this] val fMethod = f.map(addF)
  private[this] val dMethod = d.map(addD)
  private[this] val stMethod = st.map(toList)

  private def addB(value: Byte) = value + 1
  private def addS(value: Short) = value + 1
  private def addI(value: Int) = value + 1
  private def addL(value: Long) = value + 1
  private def addF(value: Float) = value + 1
  private def addD(value: Double) = value + 1
  private def toList(value: String) = List(value)

}
