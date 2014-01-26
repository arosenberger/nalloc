package com.bitb.kcits.optional

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
    OptionalDouble.empty.map(_ + 1).isNaN shouldBe true
    OptionalDouble.empty.map(_ + 1).isNaN shouldBe true
    OptionalDouble.empty.map(_ + 1).isNaN shouldBe true
    OptionalDouble.empty.map(_ + 1).isNaN shouldBe true
    OptionalDouble.empty.map(_ + 1f).isNaN shouldBe true
    OptionalDouble.empty.map(_ + 1d).isNaN shouldBe true
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
        OptionalDouble(value).map(_ + modifier) shouldBe (value + modifier)
        OptionalDouble(value).map(_ - modifier) shouldBe (value - modifier)
        OptionalDouble(value).map(_ * modifier) shouldBe (value * modifier)
        OptionalDouble(value).map(_ / modifier) shouldBe (value / modifier)
        OptionalDouble(value).map(_ % modifier) shouldBe (value % modifier)
        OptionalDouble(value).map(v => math.pow(v, modifier)) shouldBe math.pow(value, modifier)
      }
    }
  }

  property("foreach on the empty value is a no-op") {
    OptionalDouble.empty.foreach(_ => fail())
  }

  property("foreach acts on non empty values") {
    forAll { x: Double =>
      var executed = false
      OptionalDouble(x).foreach(_ => executed = true)
      executed shouldBe true
    }
  }

  property("exists on the empty value always returns false") {
    OptionalDouble.empty.exists(_ => true) shouldBe false
  }

  property("exists on non empty values evaluates the passed in function") {
    forAll { x: Double =>
      OptionalDouble(x).exists(x => x == x) shouldBe true
      OptionalDouble(x).exists(x => x.isNaN) shouldBe false
    }
  }

  property("filter on the empty value always returns the empty value") {
    OptionalDouble.empty.filter(_ => false).value.isNaN shouldBe true
    OptionalDouble.empty.filter(_ => true).value.isNaN shouldBe true
  }

  property("filter on non empty values evaluates the passed in function") {
    forAll { x: Double =>
      OptionalDouble(x).filter(x => x == x) shouldBe OptionalDouble(x)
      OptionalDouble(x).filter(x => x.isNaN).value.isNaN shouldBe true
    }
  }

  private def smallDouble: Gen[Double] = Gen.choose(0, 1000).map(_ * 1d)
}
