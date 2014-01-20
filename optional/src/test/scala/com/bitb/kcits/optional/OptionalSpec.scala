package com.bitb.kcits.optional

import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class OptionalSpec extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("The empty value does not unapply") {
    (null: String) match {
      case Optional(x) => fail(s"Empty value unapplied to $x")
      case _           =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    Optional(null: String).map(_.toByte) shouldBe Byte.MinValue
    Optional(null: String).map(_.toShort) shouldBe Short.MinValue
    Optional(null: String).map(_.toInt) shouldBe Int.MinValue
    Optional(null: String).map(_.toLong) shouldBe Long.MinValue
    Optional(null: String).map(_.toFloat).isNaN shouldBe true
    Optional(null: String).map(_.toDouble).isNaN shouldBe true
    Optional(null: String).map(_ + "foo") shouldBe null
  }

  property("Non empty values unapply to themselves") {
    forAll { x: String =>
      whenever(x != null) {
        x match {
          case Optional(unapplied) => x shouldBe unapplied
          case _                   => fail(s"$x failed to unapply")
        }
      }
    }
  }

  property("Non empty values map using the passed in function") {
    forAll { (value: Int, modifier: String) =>
      whenever(modifier != null) {
        Optional(value.toString).map(_ + modifier) shouldBe (value.toString + modifier)
      }
    }
  }

  property("foreach on the empty value is a no-op") {
    Optional.empty[String].foreach(_ => fail())
  }

  property("foreach acts on non empty values") {
    forAll { x: String =>
      whenever(x != null) {
        var executed = false
        Optional(x).foreach(_ => executed = true)
        executed shouldBe true
      }
    }
  }
}