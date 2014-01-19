package com.bitb.kcits.optional

import Bytes._
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class OptionalByteSpec extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("The empty value does not unapply") {
    Byte.MinValue match {
      case OptionalByte(x) => fail(s"Empty value unapplied to $x")
      case _               =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    Byte.MinValue.map(_ + 1.toByte) shouldBe Int.MinValue
    Byte.MinValue.map(_ + 1.toShort) shouldBe Int.MinValue
    Byte.MinValue.map(_ + 1) shouldBe Int.MinValue
    Byte.MinValue.map(_ + 1L) shouldBe Long.MinValue
    Byte.MinValue.map(_ + 1f).isNaN shouldBe true
    Byte.MinValue.map(_ + 1d).isNaN shouldBe true
  }

  property("Non empty values unapply to themselves") {
    forAll { x: Byte =>
      whenever(x != Byte.MinValue) {
        x match {
          case OptionalByte(unapplied) => x shouldBe unapplied
          case _                       => fail(s"$x failed to unapply")
        }
      }
    }
  }

  property("Non empty values map using the passed in function") {
    forAll { (value: Byte, modifier: Byte) =>
      whenever(value != Byte.MinValue && modifier != 0) {
        value.map(_ + modifier) shouldBe (value + modifier)
        value.map(_ - modifier) shouldBe (value - modifier)
        value.map(_ * modifier) shouldBe (value * modifier)
        value.map(_ / modifier) shouldBe (value / modifier)
        value.map(_ % modifier) shouldBe (value % modifier)
        value.map(_ ^ modifier) shouldBe (value ^ modifier)
        value.map(v => math.pow(v, modifier)) shouldBe math.pow(value, modifier)
      }
    }
  }
}
