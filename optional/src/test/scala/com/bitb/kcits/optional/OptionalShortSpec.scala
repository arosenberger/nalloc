package com.bitb.kcits.optional

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
    OptionalShort.empty.map(_ + 1.toShort) shouldBe Int.MinValue
    OptionalShort.empty.map(_ + 1.toShort) shouldBe Int.MinValue
    OptionalShort.empty.map(_ + 1) shouldBe Int.MinValue
    OptionalShort.empty.map(_ + 1L) shouldBe Long.MinValue
    OptionalShort.empty.map(_ + 1f).isNaN shouldBe true
    OptionalShort.empty.map(_ + 1d).isNaN shouldBe true
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
        OptionalShort(value).map(_ + modifier) shouldBe (value + modifier)
        OptionalShort(value).map(_ - modifier) shouldBe (value - modifier)
        OptionalShort(value).map(_ * modifier) shouldBe (value * modifier)
        OptionalShort(value).map(_ / modifier) shouldBe (value / modifier)
        OptionalShort(value).map(_ % modifier) shouldBe (value % modifier)
        OptionalShort(value).map(_ ^ modifier) shouldBe (value ^ modifier)
        OptionalShort(value).map(v => math.pow(v, modifier)) shouldBe math.pow(value, modifier)
      }
    }
  }


  property("foreach on the empty value is a no-op") {
    OptionalShort.empty.foreach(_ => fail())
  }

  property("foreach acts on non empty values") {
    forAll { x: Short =>
      whenever(x != Short.MinValue) {
        var executed = false
        OptionalShort(x).foreach(_ => executed = true)
        executed shouldBe true
      }
    }
  }

  property("exists on the empty value always returns false") {
    OptionalShort.empty.exists(_ => true) shouldBe false
  }

  property("exists on non empty values evaluates the passed in function") {
    forAll { x: Short =>
      whenever(x != Short.MinValue) {
        OptionalShort(x).exists(x => x == x) shouldBe true
        OptionalShort(x).exists(x => x == x + 1) shouldBe false
      }
    }
  }

  property("filter on the empty value always returns the empty value") {
    OptionalShort.empty.filter(_ => false) shouldBe OptionalShort.empty
    OptionalShort.empty.filter(_ => true) shouldBe OptionalShort.empty
  }

  property("filter on non empty values evaluates the passed in function") {
    forAll { x: Short =>
      whenever(x != Short.MinValue) {
        OptionalShort(x).filter(x => x == x) shouldBe OptionalShort(x)
        OptionalShort(x).filter(x => x == x + 1) shouldBe OptionalShort.empty
      }
    }
  }

  property("getOrElse on the empty value returns the passed in alternative") {
    OptionalShort.empty.orElse(1.toByte) shouldBe 1
  }

  property("getOrElse on non empty values does not evaluate the passed in function") {
    forAll { x: Short =>
      whenever(x != Short.MinValue) {
        OptionalShort(x).orElse(throw new IllegalArgumentException) shouldBe x
      }
    }
  }
}
