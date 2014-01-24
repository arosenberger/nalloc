package com.bitb.kcits.optional

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
    OptionalInt.empty.map(_ + 1.toByte) shouldBe Int.MinValue
    OptionalInt.empty.map(_ + 1.toShort) shouldBe Int.MinValue
    OptionalInt.empty.map(_ + 1) shouldBe Int.MinValue
    OptionalInt.empty.map(_ + 1L) shouldBe Long.MinValue
    OptionalInt.empty.map(_ + 1f).isNaN shouldBe true
    OptionalInt.empty.map(_ + 1d).isNaN shouldBe true
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
        OptionalInt(value).map(_ + modifier) shouldBe (value + modifier)
        OptionalInt(value).map(_ - modifier) shouldBe (value - modifier)
        OptionalInt(value).map(_ * modifier) shouldBe (value * modifier)
        OptionalInt(value).map(_ / modifier) shouldBe (value / modifier)
        OptionalInt(value).map(_ % modifier) shouldBe (value % modifier)
        OptionalInt(value).map(_ ^ modifier) shouldBe (value ^ modifier)
        OptionalInt(value).map(v => math.pow(v, modifier)) shouldBe math.pow(value, modifier)
      }
    }
  }

  property("foreach on the empty value is a no-op") {
    OptionalInt.empty.foreach(_ => fail())
  }

  property("foreach acts on non empty values") {
    forAll { x: Int =>
      whenever(x != Int.MinValue) {
        var executed = false
        OptionalInt(x).foreach(_ => executed = true)
        executed shouldBe true
      }
    }
  }

  property("exists on the empty value always returns false") {
    OptionalInt.empty.exists(_ => true) shouldBe false
  }

  property("exists on non empty values evaluates the passed in function") {
    forAll { x: Int =>
      whenever(x != Int.MinValue) {
        OptionalInt(x).exists(x => x == x) shouldBe true
        OptionalInt(x).exists(x => x == x + 1) shouldBe false
      }
    }
  }
}
