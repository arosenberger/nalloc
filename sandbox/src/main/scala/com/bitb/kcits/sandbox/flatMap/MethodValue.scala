package com.bitb.kcits.sandbox.flatMap

import com.bitb.kcits.optional._
import com.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {

  private[this] val bMethod = b.flatMap(addB)
  private[this] val sMethod = s.flatMap(addS)
  private[this] val iMethod = i.flatMap(addI)
  private[this] val lMethod = l.flatMap(addL)
  private[this] val fMethod = f.flatMap(addF)
  private[this] val dMethod = d.flatMap(addD)
  private[this] val stMethod = st.flatMap(toList)

  private def addB(value: Byte) = OptionalByte((value + 1).toByte)
  private def addS(value: Short) = OptionalShort((value + 1).toShort)
  private def addI(value: Int) = OptionalInt(value + 1)
  private def addL(value: Long) = OptionalLong(value + 1)
  private def addF(value: Float) = OptionalFloat(value + 1)
  private def addD(value: Double) = OptionalDouble(value + 1)
  private def toList(value: String) = Optional(List(value))

}
