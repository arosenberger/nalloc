package com.bitb.kcits.optional

import org.scalacheck.Gen
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class OptionalFloatSpec extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("The empty value does not unapply") {
    Float.NaN match {
      case OptionalFloat(x) => fail(s"Empty value unapplied to $x")
      case _                =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    OptionalFloat.empty.map(_ + 1.toByte).isNaN shouldBe true
    OptionalFloat.empty.map(_ + 1.toShort).isNaN shouldBe true
    OptionalFloat.empty.map(_ + 1).isNaN shouldBe true
    OptionalFloat.empty.map(_ + 1L).isNaN shouldBe true
    OptionalFloat.empty.map(_ + 1f).isNaN shouldBe true
    OptionalFloat.empty.map(_ + 1d).isNaN shouldBe true
  }

  property("Non empty values unapply to themselves") {
    forAll { x: Float =>
      x match {
        case OptionalFloat(unapplied) => x shouldBe unapplied
        case _                        => fail(s"$x failed to unapply")
      }
    }
  }

  property("Non empty values map using the passed in function") {
    forAll(smallFloat, smallFloat) { (value: Float, modifier: Float) =>
      whenever(modifier != 0) {
        OptionalFloat(value).map(_ + modifier) shouldBe (value + modifier)
        OptionalFloat(value).map(_ - modifier) shouldBe (value - modifier)
        OptionalFloat(value).map(_ * modifier) shouldBe (value * modifier)
        OptionalFloat(value).map(_ / modifier) shouldBe (value / modifier)
        OptionalFloat(value).map(_ % modifier) shouldBe (value % modifier)
        OptionalFloat(value).map(v => math.pow(v, modifier)) shouldBe math.pow(value, modifier)
      }
    }
  }

  property("foreach on the empty value is a no-op") {
    OptionalFloat.empty.foreach(_ => fail())
  }

  property("foreach acts on non empty values") {
    forAll { x: Float =>
      var executed = false
      OptionalFloat(x).foreach(_ => executed = true)
      executed shouldBe true
    }
  }

  private def smallFloat: Gen[Float] = Gen.choose(0, 1000).map(_ * 1f)
}
