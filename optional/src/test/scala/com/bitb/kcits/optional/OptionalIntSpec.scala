package com.bitb.kcits.optional

import Ints._
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class OptionalIntSpec extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("The empty value does not unapply") {
    Int.MinValue match {
      case OptionalInt(x) => fail(s"Empty value unapplied to $x")
      case _              =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    Int.MinValue.map(_ + 1.toByte) shouldBe Int.MinValue
    Int.MinValue.map(_ + 1.toShort) shouldBe Int.MinValue
    Int.MinValue.map(_ + 1) shouldBe Int.MinValue
    Int.MinValue.map(_ + 1L) shouldBe Long.MinValue
    Int.MinValue.map(_ + 1f).isNaN shouldBe true
    Int.MinValue.map(_ + 1d).isNaN shouldBe true
  }

  property("Non empty values unapply to themselves") {
    forAll { x: Int =>
      whenever(x != Int.MinValue) {
        x match {
          case OptionalInt(unapplied) => x shouldBe unapplied
          case _                      => fail(s"$x failed to unapply")
        }
      }
    }
  }

  property("Non empty values map using the passed in function") {
    forAll { (value: Int, modifier: Int) =>
      whenever(value != Int.MinValue && modifier != 0) {
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
