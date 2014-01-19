package com.bitb.kcits.optional

import Shorts._
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class OptionalShortSpec extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("The empty value does not unapply") {
    Short.MinValue match {
      case OptionalShort(x) => fail(s"Empty value unapplied to $x")
      case _                =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    Short.MinValue.map(_ + 1.toByte) shouldBe Int.MinValue
    Short.MinValue.map(_ + 1.toShort) shouldBe Int.MinValue
    Short.MinValue.map(_ + 1) shouldBe Int.MinValue
    Short.MinValue.map(_ + 1L) shouldBe Long.MinValue
    Short.MinValue.map(_ + 1f).isNaN shouldBe true
    Short.MinValue.map(_ + 1d).isNaN shouldBe true
  }

  property("Non empty values unapply to themselves") {
    forAll { x: Short =>
      whenever(x != Short.MinValue) {
        x match {
          case OptionalShort(unapplied) => x shouldBe unapplied
          case _                        => fail(s"$x failed to unapply")
        }
      }
    }
  }

  property("Non empty values map using the passed in function") {
    forAll { (value: Short, modifier: Short) =>
      whenever(value != Short.MinValue && modifier != 0) {
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
