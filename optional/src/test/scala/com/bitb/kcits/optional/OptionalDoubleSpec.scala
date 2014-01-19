package com.bitb.kcits.optional

import Doubles._
import org.scalacheck.Gen
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class OptionalDoubleSpec extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("The empty value does not unapply") {
    Double.NaN match {
      case OptionalDouble(x) => fail(s"Empty value unapplied to $x")
      case _                 =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    Double.NaN.map(_ + 1).isNaN shouldBe true
    Double.NaN.map(_ + 1).isNaN shouldBe true
    Double.NaN.map(_ + 1).isNaN shouldBe true
    Double.NaN.map(_ + 1).isNaN shouldBe true
    Double.NaN.map(_ + 1f).isNaN shouldBe true
    Double.NaN.map(_ + 1d).isNaN shouldBe true
  }

  property("Non empty values unapply to themselves") {
    forAll { x: Double =>
      x match {
        case OptionalDouble(unapplied) => x shouldBe unapplied
        case _                         => fail(s"$x failed to unapply")
      }
    }
  }

  property("Non empty values map using the passed in function") {
    forAll(smallDouble, smallDouble) { (value: Double, modifier: Double) =>
      whenever(modifier != 0) {
        value.map(_ + modifier) shouldBe (value + modifier)
        value.map(_ - modifier) shouldBe (value - modifier)
        value.map(_ * modifier) shouldBe (value * modifier)
        value.map(_ / modifier) shouldBe (value / modifier)
        value.map(_ % modifier) shouldBe (value % modifier)
        value.map(v => math.pow(v, modifier)) shouldBe math.pow(value, modifier)
      }
    }
  }

  private def smallDouble: Gen[Double] = Gen.choose(0, 1000).map(_ * 1d)
}
