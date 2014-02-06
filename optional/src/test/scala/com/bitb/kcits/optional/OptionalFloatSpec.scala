package com.bitb.kcits.optional

class OptionalFloatSpec extends OptionalTypeSuite {

  property("The empty value does not unapply") {
    Float.NaN match {
      case OptionalFloat(x) => fail(s"Empty value unapplied to $x")
      case _                =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    forAll(mapFunctionsFrom[Float]) { functions =>
      import functions._

      OptionalFloat.empty.map(mapToByte) shouldBe OptionalByte.empty
      OptionalFloat.empty.map(mapToShort) shouldBe OptionalShort.empty
      OptionalFloat.empty.map(mapToInt) shouldBe OptionalInt.empty
      OptionalFloat.empty.map(mapToLong) shouldBe OptionalLong.empty
      OptionalFloat.empty.map(mapToFloat).isEmpty shouldBe true
      OptionalFloat.empty.map(mapToDouble).isEmpty shouldBe true
      OptionalFloat.empty.map(mapToString) shouldBe Optional.empty[String]
    }
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
    forAll(floats, mapFunctionsFrom[Float]) { (value, functions) =>
      import functions._

      OptionalFloat(value).map(mapToByte) shouldBe OptionalByte(mapToByte(value))
      OptionalFloat(value).map(mapToShort) shouldBe OptionalShort(mapToShort(value))
      OptionalFloat(value).map(mapToInt) shouldBe OptionalInt(mapToInt(value))
      OptionalFloat(value).map(mapToLong) shouldBe OptionalLong(mapToLong(value))
      OptionalFloat(value).map(mapToFloat) shouldBe OptionalFloat(mapToFloat(value))
      OptionalFloat(value).map(mapToDouble) shouldBe OptionalDouble(mapToDouble(value))
      OptionalFloat(value).map(mapToString) shouldBe Optional(mapToString(value))
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

  property("exists on the empty value always returns false") {
    OptionalFloat.empty.exists(_ => true) shouldBe false
  }

  property("exists on non empty values evaluates the passed in function") {
    forAll { x: Float =>
      OptionalFloat(x).exists(x => x == x) shouldBe true
      OptionalFloat(x).exists(x => x.isNaN) shouldBe false
    }
  }

  property("filter on the empty value always returns the empty value") {
    OptionalFloat.empty.filter(_ => false).value.isNaN shouldBe true
    OptionalFloat.empty.filter(_ => true).value.isNaN shouldBe true
  }

  property("filter on non empty values evaluates the passed in function") {
    forAll { x: Float =>
      OptionalFloat(x).filter(x => x == x) shouldBe OptionalFloat(x)
      OptionalFloat(x).filter(x => x.isNaN).value.isNaN shouldBe true
    }
  }

  property("orElse on the empty value returns the passed in alternative") {
    OptionalFloat.empty.orElse(1.toByte) shouldBe 1
  }

  property("orElse on non empty values does not evaluate the passed in function") {
    forAll { x: Float =>
      OptionalFloat(x).orElse(throw new IllegalArgumentException) shouldBe x
    }
  }

  property("The empty value flatMaps to the empty value of its target type") {
    forAll(flatMapFunctionsFrom[Float]) { functions =>
      import functions._

      OptionalFloat.empty.flatMap(mapToOptionalByte) shouldBe OptionalByte.empty
      OptionalFloat.empty.flatMap(mapToOptionalShort) shouldBe OptionalShort.empty
      OptionalFloat.empty.flatMap(mapToOptionalInt) shouldBe OptionalInt.empty
      OptionalFloat.empty.flatMap(mapToOptionalLong) shouldBe OptionalLong.empty
      OptionalFloat.empty.flatMap(mapToOptionalFloat).isEmpty shouldBe true
      OptionalFloat.empty.flatMap(mapToOptionalDouble).isEmpty shouldBe true
      OptionalFloat.empty.flatMap(mapToOptionalString) shouldBe Optional.empty[String]
    }
  }

  property("Non empty values flatMap using the passed in function") {
    forAll(floats, flatMapFunctionsFrom[Float]) { (value, functions) =>
      import functions._

      OptionalFloat(value).flatMap(mapToOptionalByte) shouldBe mapToOptionalByte(value)
      OptionalFloat(value).flatMap(mapToOptionalShort) shouldBe mapToOptionalShort(value)
      OptionalFloat(value).flatMap(mapToOptionalInt) shouldBe mapToOptionalInt(value)
      OptionalFloat(value).flatMap(mapToOptionalLong) shouldBe mapToOptionalLong(value)
      OptionalFloat(value).flatMap(mapToOptionalFloat) shouldBe mapToOptionalFloat(value)
      OptionalFloat(value).flatMap(mapToOptionalDouble) shouldBe mapToOptionalDouble(value)
      OptionalFloat(value).flatMap(mapToOptionalString) shouldBe mapToOptionalString(value)
    }
  }
}
