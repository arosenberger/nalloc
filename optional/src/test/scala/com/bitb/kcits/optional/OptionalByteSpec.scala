package com.bitb.kcits.optional

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
    OptionalByte.empty.map(_ + 1.toByte) shouldBe Int.MinValue
    OptionalByte.empty.map(_ + 1.toShort) shouldBe Int.MinValue
    OptionalByte.empty.map(_ + 1) shouldBe Int.MinValue
    OptionalByte.empty.map(_ + 1L) shouldBe Long.MinValue
    OptionalByte.empty.map(_ + 1f).isNaN shouldBe true
    OptionalByte.empty.map(_ + 1d).isNaN shouldBe true
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
        OptionalByte(value).map(_ + modifier) shouldBe (value + modifier)
        OptionalByte(value).map(_ - modifier) shouldBe (value - modifier)
        OptionalByte(value).map(_ * modifier) shouldBe (value * modifier)
        OptionalByte(value).map(_ / modifier) shouldBe (value / modifier)
        OptionalByte(value).map(_ % modifier) shouldBe (value % modifier)
        OptionalByte(value).map(_ ^ modifier) shouldBe (value ^ modifier)
        OptionalByte(value).map(v => math.pow(v, modifier)) shouldBe math.pow(value, modifier)
      }
    }
  }

  property("foreach on the empty value is a no-op") {
    OptionalByte.empty.foreach(_ => fail())
  }

  property("foreach acts on non empty values") {
    forAll { x: Byte =>
      whenever(x != Byte.MinValue) {
        var executed = false
        OptionalByte(x).foreach(_ => executed = true)
        executed shouldBe true
      }
    }
  }

  property("exists on the empty value always returns false") {
    OptionalByte.empty.exists(_ => true) shouldBe false
  }

  property("exists on non empty values evaluates the passed in function") {
    forAll { x: Byte =>
      whenever(x != Byte.MinValue) {
        OptionalByte(x).exists(x => x == x) shouldBe true
        OptionalByte(x).exists(x => x == x + 1) shouldBe false
      }
    }
  }
}
