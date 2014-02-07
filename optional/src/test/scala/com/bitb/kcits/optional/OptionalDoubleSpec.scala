/*
 * Copyright 2014 Adam Rosenberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bitb.kcits.optional

class OptionalDoubleSpec extends OptionalTypeSuite {

  property("The empty value does not unapply") {
    Double.NaN match {
      case OptionalDouble(x) => fail(s"Empty value unapplied to $x")
      case _                 =>
    }
  }


  property("The empty value maps to the empty value of its target type") {
    forAll(mapFunctionsFrom[Double]) { functions =>
      import functions._

      OptionalDouble.empty.map(mapToByte) shouldBe OptionalByte.empty
      OptionalDouble.empty.map(mapToShort) shouldBe OptionalShort.empty
      OptionalDouble.empty.map(mapToInt) shouldBe OptionalInt.empty
      OptionalDouble.empty.map(mapToLong) shouldBe OptionalLong.empty
      OptionalDouble.empty.map(mapToFloat).isEmpty shouldBe true
      OptionalDouble.empty.map(mapToDouble).isEmpty shouldBe true
      OptionalDouble.empty.map(mapToString) shouldBe Optional.empty[String]
    }
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
    forAll(doubles, mapFunctionsFrom[Double]) { (value, functions) =>
      import functions._

      OptionalDouble(value).map(mapToByte) shouldBe OptionalByte(mapToByte(value))
      OptionalDouble(value).map(mapToShort) shouldBe OptionalShort(mapToShort(value))
      OptionalDouble(value).map(mapToInt) shouldBe OptionalInt(mapToInt(value))
      OptionalDouble(value).map(mapToLong) shouldBe OptionalLong(mapToLong(value))
      OptionalDouble(value).map(mapToFloat) shouldBe OptionalFloat(mapToFloat(value))
      OptionalDouble(value).map(mapToDouble) shouldBe OptionalDouble(mapToDouble(value))
      OptionalDouble(value).map(mapToString) shouldBe Optional(mapToString(value))
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

  property("orElse on the empty value returns the passed in alternative") {
    OptionalDouble.empty.orElse(1.toByte) shouldBe 1
  }

  property("orElse on non empty values does not evaluate the passed in function") {
    forAll { x: Double =>
      OptionalDouble(x).orElse(throw new IllegalArgumentException) shouldBe x
    }
  }

  property("The empty value flatMaps to the empty value of its target type") {
    forAll(flatMapFunctionsFrom[Double]) { functions =>
      import functions._

      OptionalDouble.empty.flatMap(mapToOptionalByte) shouldBe OptionalByte.empty
      OptionalDouble.empty.flatMap(mapToOptionalShort) shouldBe OptionalShort.empty
      OptionalDouble.empty.flatMap(mapToOptionalInt) shouldBe OptionalInt.empty
      OptionalDouble.empty.flatMap(mapToOptionalLong) shouldBe OptionalLong.empty
      OptionalDouble.empty.flatMap(mapToOptionalFloat).isEmpty shouldBe true
      OptionalDouble.empty.flatMap(mapToOptionalDouble).isEmpty shouldBe true
      OptionalDouble.empty.flatMap(mapToOptionalString) shouldBe Optional.empty[String]
    }
  }

  property("Non empty values flatMap using the passed in function") {
    forAll(doubles, flatMapFunctionsFrom[Double]) { (value, functions) =>
      import functions._

      OptionalDouble(value).flatMap(mapToOptionalByte) shouldBe mapToOptionalByte(value)
      OptionalDouble(value).flatMap(mapToOptionalShort) shouldBe mapToOptionalShort(value)
      OptionalDouble(value).flatMap(mapToOptionalInt) shouldBe mapToOptionalInt(value)
      OptionalDouble(value).flatMap(mapToOptionalLong) shouldBe mapToOptionalLong(value)
      OptionalDouble(value).flatMap(mapToOptionalFloat) shouldBe mapToOptionalFloat(value)
      OptionalDouble(value).flatMap(mapToOptionalDouble) shouldBe mapToOptionalDouble(value)
      OptionalDouble(value).flatMap(mapToOptionalString) shouldBe mapToOptionalString(value)
    }
  }

  property("The empty value always applies the ifEmpty portion of a fold") {
    forAll { (ifEmpty: Double, ifNotEmpty: Double) =>
      whenever(ifEmpty != ifNotEmpty) {
        OptionalDouble.empty.fold(ifEmpty)(_ => ifNotEmpty) shouldBe ifEmpty
      }
    }
  }

  property("Non empty values always apply the map portion of a fold") {
    forAll(doubles, doubles, mapFunctionsFrom[Double]) { (ifEmpty, value, functions) =>
      import functions._

      OptionalDouble(value).fold(ifEmpty)(mapToDouble) shouldBe mapToDouble(value)
    }
  }
}
