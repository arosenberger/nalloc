package com.bitb.kcits.optional

import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class
OptionalByteSpec extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("The empty value does not unapply") {
    Byte.MinValue match {
      case OptionalByte(x) => fail(s"Empty value unapplied to $x")
      case _               =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    OptionalByte.empty.map(x => x) shouldBe OptionalByte.empty
    OptionalByte.empty.map(_.toShort) shouldBe OptionalShort.empty
    OptionalByte.empty.map(_ + 1.toByte) shouldBe OptionalInt.empty
    OptionalByte.empty.map(_ + 1.toShort) shouldBe OptionalInt.empty
    OptionalByte.empty.map(_ + 1) shouldBe OptionalInt.empty
    OptionalByte.empty.map(_ + 1L) shouldBe OptionalLong.empty
    OptionalByte.empty.map(_ + 1f).isEmpty shouldBe true
    OptionalByte.empty.map(_ + 1d).isEmpty shouldBe true
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
        OptionalByte(value).map(_ + modifier).get shouldBe (value + modifier)
        OptionalByte(value).map(_ - modifier).get shouldBe (value - modifier)
        OptionalByte(value).map(_ * modifier).get shouldBe (value * modifier)
        OptionalByte(value).map(_ / modifier).get shouldBe (value / modifier)
        OptionalByte(value).map(_ % modifier).get shouldBe (value % modifier)
        OptionalByte(value).map(_ ^ modifier).get shouldBe (value ^ modifier)
        OptionalByte(value).map(v => math.pow(v, modifier)).get shouldBe math.pow(value, modifier)
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

  property("filter on the empty value always returns the empty value") {
    OptionalByte.empty.filter(_ => false) shouldBe OptionalByte.empty
    OptionalByte.empty.filter(_ => true) shouldBe OptionalByte.empty
  }

  property("filter on non empty values evaluates the passed in function") {
    forAll { x: Byte =>
      whenever(x != Byte.MinValue) {
        OptionalByte(x).filter(x => x == x) shouldBe OptionalByte(x)
        OptionalByte(x).filter(x => x == x + 1) shouldBe OptionalByte.empty
      }
    }
  }

  property("getOrElse on the empty value returns the passed in alternative") {
    OptionalByte.empty.orElse(1.toByte) shouldBe 1
  }

  property("getOrElse on non empty values does not evaluate the passed in function") {
    forAll { x: Byte =>
      whenever(x != Byte.MinValue) {
        OptionalByte(x).orElse(throw new IllegalArgumentException) shouldBe x
      }
    }
  }
}
