package com.bitb.kcits.optional

class OptionalIntSpec extends OptionalTypeSuite {

  property("The empty value does not unapply") {
    Int.MinValue match {
      case OptionalInt(x) => fail(s"Empty value unapplied to $x")
      case _              =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    forAll(mapFunctionsFrom[Int]) { functions =>
      import functions._

      OptionalInt.empty.map(mapToByte) shouldBe OptionalByte.empty
      OptionalInt.empty.map(mapToShort) shouldBe OptionalShort.empty
      OptionalInt.empty.map(mapToInt) shouldBe OptionalInt.empty
      OptionalInt.empty.map(mapToLong) shouldBe OptionalLong.empty
      OptionalInt.empty.map(mapToFloat).isEmpty shouldBe true
      OptionalInt.empty.map(mapToDouble).isEmpty shouldBe true
      OptionalInt.empty.map(mapToString) shouldBe Optional.empty[String]
    }
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
    forAll(ints, mapFunctionsFrom[Int]) { (value, functions) =>
      whenever(value != Int.MinValue) {
        import functions._

        OptionalInt(value).map(mapToByte) shouldBe OptionalByte(mapToByte(value))
        OptionalInt(value).map(mapToShort) shouldBe OptionalShort(mapToShort(value))
        OptionalInt(value).map(mapToInt) shouldBe OptionalInt(mapToInt(value))
        OptionalInt(value).map(mapToLong) shouldBe OptionalLong(mapToLong(value))
        OptionalInt(value).map(mapToFloat) shouldBe OptionalFloat(mapToFloat(value))
        OptionalInt(value).map(mapToDouble) shouldBe OptionalDouble(mapToDouble(value))
        OptionalInt(value).map(mapToString) shouldBe Optional(mapToString(value))
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

  property("filter on the empty value always returns the empty value") {
    OptionalInt.empty.filter(_ => false) shouldBe OptionalInt.empty
    OptionalInt.empty.filter(_ => true) shouldBe OptionalInt.empty
  }

  property("filter on non empty values evaluates the passed in function") {
    forAll { x: Int =>
      whenever(x != Int.MinValue) {
        OptionalInt(x).filter(x => x == x) shouldBe OptionalInt(x)
        OptionalInt(x).filter(x => x == x + 1) shouldBe OptionalInt.empty
      }
    }
  }

  property("orElse on the empty value returns the passed in alternative") {
    OptionalInt.empty.orElse(1) shouldBe 1
  }

  property("orElse on non empty values does not evaluate the passed in function") {
    forAll { x: Int =>
      whenever(x != Int.MinValue) {
        OptionalInt(x).orElse(throw new IllegalArgumentException) shouldBe x
      }
    }
  }

  property("The empty value flatMaps to the empty value of its target type") {
    forAll(flatMapFunctionsFrom[Int]) { functions =>
      import functions._

      OptionalInt.empty.flatMap(mapToOptionalByte) shouldBe OptionalByte.empty
      OptionalInt.empty.flatMap(mapToOptionalShort) shouldBe OptionalShort.empty
      OptionalInt.empty.flatMap(mapToOptionalInt) shouldBe OptionalInt.empty
      OptionalInt.empty.flatMap(mapToOptionalLong) shouldBe OptionalLong.empty
      OptionalInt.empty.flatMap(mapToOptionalFloat).isEmpty shouldBe true
      OptionalInt.empty.flatMap(mapToOptionalDouble).isEmpty shouldBe true
      OptionalInt.empty.flatMap(mapToOptionalString) shouldBe Optional.empty[String]
    }
  }

  property("Non empty values flatMap using the passed in function") {
    forAll(ints, flatMapFunctionsFrom[Int]) { (value, functions) =>
      whenever(value != Int.MinValue) {
        import functions._

        OptionalInt(value).flatMap(mapToOptionalByte) shouldBe mapToOptionalByte(value)
        OptionalInt(value).flatMap(mapToOptionalShort) shouldBe mapToOptionalShort(value)
        OptionalInt(value).flatMap(mapToOptionalInt) shouldBe mapToOptionalInt(value)
        OptionalInt(value).flatMap(mapToOptionalLong) shouldBe mapToOptionalLong(value)
        OptionalInt(value).flatMap(mapToOptionalFloat) shouldBe mapToOptionalFloat(value)
        OptionalInt(value).flatMap(mapToOptionalDouble) shouldBe mapToOptionalDouble(value)
        OptionalInt(value).flatMap(mapToOptionalString) shouldBe mapToOptionalString(value)
      }
    }
  }
}
