package com.bitb.kcits.optional

import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class OptionalLongSpec extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("The empty value does not unapply") {
    Long.MinValue match {
      case OptionalLong(x) => fail(s"Empty value unapplied to $x")
      case _               =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    OptionalLong.empty.map(_ + 1.toByte) shouldBe Long.MinValue
    OptionalLong.empty.map(_ + 1.toShort) shouldBe Long.MinValue
    OptionalLong.empty.map(_ + 1) shouldBe Long.MinValue
    OptionalLong.empty.map(_ + 1L) shouldBe Long.MinValue
    OptionalLong.empty.map(_ + 1f).isNaN shouldBe true
    OptionalLong.empty.map(_ + 1d).isNaN shouldBe true
  }

  property("Non empty values unapply to themselves") {
    forAll { x: Long =>
      whenever(x != Long.MinValue) {
        x match {
          case OptionalLong(unapplied) => x shouldBe unapplied
          case _                       => fail(s"$x failed to unapply")
        }
      }
    }
  }

  property("Non empty values map using the passed in function") {
    forAll { (value: Long, modifier: Long) =>
      whenever(value != Long.MinValue && modifier != 0) {
        OptionalLong(value).map(_ + modifier) shouldBe (value + modifier)
        OptionalLong(value).map(_ - modifier) shouldBe (value - modifier)
        OptionalLong(value).map(_ * modifier) shouldBe (value * modifier)
        OptionalLong(value).map(_ / modifier) shouldBe (value / modifier)
        OptionalLong(value).map(_ % modifier) shouldBe (value % modifier)
        OptionalLong(value).map(_ ^ modifier) shouldBe (value ^ modifier)
        OptionalLong(value).map(v => math.pow(v, modifier)) shouldBe math.pow(value, modifier)
      }
    }
  }

  property("foreach on the empty value is a no-op") {
    OptionalLong.empty.foreach(_ => fail())
  }

  property("foreach acts on non empty values") {
    forAll { x: Long =>
      whenever(x != Long.MinValue) {
        var executed = false
        OptionalLong(x).foreach(_ => executed = true)
        executed shouldBe true
      }
    }
  }
}
