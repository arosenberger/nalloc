package com.bitb.kcits.optional

import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class OptionalSpec extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {

  property("The empty value does not unapply") {
    (null: String) match {
      case Optional(x) => fail(s"Empty value unapplied to ")
      case _           =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    Optional(null: String).map(_.toByte).get shouldBe Byte.MinValue
    Optional(null: String).map(_.toShort).get shouldBe Short.MinValue
    Optional(null: String).map(_.toInt).get shouldBe Int.MinValue
    Optional(null: String).map(_.toLong).get shouldBe Long.MinValue
    Optional(null: String).map(_.toFloat).isNaN shouldBe true
    Optional(null: String).map(_.toDouble).isNaN shouldBe true
    Optional(null: String).map(_ + "foo").get shouldBe (null: String)
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
        Optional(value.toString).map(_ + modifier).get shouldBe (value.toString + modifier)
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

  property("exists on the empty value always returns false") {
    Optional.empty[String].exists(_ => true) shouldBe false
  }

  property("exists on non empty values evaluates the passed in function") {
    forAll { x: String =>
      whenever(x != null) {
        Optional(x).exists(x => x == x) shouldBe true
        Optional(x).exists(x => x == x + "foo") shouldBe false
      }
    }
  }

  property("filter on the empty value always returns the empty value") {
    Optional.empty[String].filter(_ => false) shouldBe Optional.empty[String]
    Optional.empty[String].filter(_ => true) shouldBe Optional.empty[String]
  }

  property("filter on non empty values evaluates the passed in function") {
    forAll { x: String =>
      whenever(x != null) {
        Optional(x).filter(x => x == x) shouldBe Optional(x)
        Optional(x).filter(x => x == x + "foo") shouldBe Optional.empty[String]
      }
    }
  }

  property("getOrElse on the empty value returns the passed in alternative") {
    Optional.empty[String].orElse("foo") shouldBe "foo"
  }

  property("getOrElse on non empty values does not evaluate the passed in function") {
    forAll { x: String =>
      whenever(x != null) {
        Optional(x).orElse(throw new IllegalArgumentException) shouldBe x
      }
    }
  }
}
