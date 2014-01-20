package com.bitb.kcits.sandbox

import com.bitb.kcits.optional._
import java.util.Random

class VisualInspection {

  private[this] val random = new Random()

  private[this] val b = OptionalByte(random.nextInt().toByte)
  private[this] val s = OptionalShort(random.nextInt().toShort)
  private[this] val i = OptionalInt(random.nextInt())
  private[this] val l = OptionalLong(random.nextLong())
  private[this] val f = OptionalFloat(random.nextFloat())
  private[this] val d = OptionalDouble(random.nextDouble())
  private[this] val st = Optional(random.nextDouble().toString)

  //  private[this] val bInline = b.map(_ + 1)
  //  private[this] val sInline = s.map(_ + 2)
  //  private[this] val iInline = i.map(_ + 3)
  //  private[this] val lInline = l.map(_ + 4)
  //  private[this] val fInline = f.map(_ + 5)
  //  private[this] val dInline = d.map(_ + 6)
  //  private[this] val stInline = d.map(List(_))

  private[this] val bInlineComplex = b.map { x =>
    val y = x + 5
    y * 3
  }
//  private[this] val sInlineComplex = s.map { x =>
//    val y = x + 5
//    y * 3
//  }
//  private[this] val iInlineComplex = i.map { x =>
//    val y = x + 5
//    y * 3
//  }
//  private[this] val lInlineComplex = l.map { x =>
//    val y = x + 5
//    y * 3
//  }
//  private[this] val fInlineComplex = f.map { x =>
//    val y = x + 5
//    y * 3
//  }
//  private[this] val dInlineComplex = d.map { x =>
//    val y = x + 5
//    y * 3
//  }

  //  private[this] val bMethod = b.map(addB)
  //  private[this] val sMethod = s.map(addS)
  //  private[this] val iMethod = i.map(addI)
  //  private[this] val lMethod = l.map(addL)
  //  private[this] val fMethod = f.map(addF)
  //  private[this] val dMethod = d.map(addD)
  //  private[this] val stMethod = st.map(toList)
  //
  //  private def addB(value: Byte) = value + 1
  //  private def addS(value: Short) = value + 1
  //  private def addI(value: Int) = value + 1
  //  private def addL(value: Long) = value + 1
  //  private def addF(value: Float) = value + 1
  //  private def addD(value: Double) = value + 1
  //  private def toList(value: String) = List(value)

}
