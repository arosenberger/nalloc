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

package org.nalloc.bitb.kcits.optional

import org.scalacheck.Arbitrary._

class OptionalByteSpec extends OptionalTypeSuite {

  property("The empty value does not unapply") {
    Byte.MinValue match {
      case OptionalByte(x) => fail(s"Empty value unapplied to $x")
      case _               =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    forAll(mapFunctionsFrom[Byte]) { functions =>
      import functions._

      OptionalByte.empty.map(mapToByte) shouldBe OptionalByte.empty
      OptionalByte.empty.map(mapToShort) shouldBe OptionalShort.empty
      OptionalByte.empty.map(mapToInt) shouldBe OptionalInt.empty
      OptionalByte.empty.map(mapToLong) shouldBe OptionalLong.empty
      OptionalByte.empty.map(mapToFloat).isEmpty shouldBe true
      OptionalByte.empty.map(mapToDouble).isEmpty shouldBe true
      OptionalByte.empty.map(mapToString) shouldBe Optional.empty[String]
    }
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
    forAll(bytes, mapFunctionsFrom[Byte]) { (value, functions) =>
      whenever(value != Byte.MinValue) {
        import functions._

        OptionalByte(value).map(mapToByte) shouldBe OptionalByte(mapToByte(value))
        OptionalByte(value).map(mapToShort) shouldBe OptionalShort(mapToShort(value))
        OptionalByte(value).map(mapToInt) shouldBe OptionalInt(mapToInt(value))
        OptionalByte(value).map(mapToLong) shouldBe OptionalLong(mapToLong(value))
        OptionalByte(value).map(mapToFloat) shouldBe OptionalFloat(mapToFloat(value))
        OptionalByte(value).map(mapToDouble) shouldBe OptionalDouble(mapToDouble(value))
        OptionalByte(value).map(mapToString) shouldBe Optional(mapToString(value))
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
    OptionalByte.empty.getOrElse(1.toByte) shouldBe 1
  }

  property("getOrElse on non empty values does not evaluate the passed in function") {
    forAll { x: Byte =>
      whenever(x != Byte.MinValue) {
        OptionalByte(x).getOrElse(throw new IllegalArgumentException) shouldBe x
      }
    }
  }

  property("orElse on the empty value returns the passed in alternative") {
    OptionalByte.empty.orElse(OptionalByte(1)) shouldBe OptionalByte(1)
  }

  property("orElse on non empty values does not evaluate the passed in function") {
    forAll { x: Byte =>
      whenever(x != Byte.MinValue) {
        OptionalByte(x).orElse(throw new IllegalArgumentException) shouldBe OptionalByte(x)
      }
    }
  }

  property("The empty value flatMaps to the empty value of its target type") {
    forAll(flatMapFunctionsFrom[Byte]) { functions =>
      import functions._

      OptionalByte.empty.flatMap(mapToOptionalByte) shouldBe OptionalByte.empty
      OptionalByte.empty.flatMap(mapToOptionalShort) shouldBe OptionalShort.empty
      OptionalByte.empty.flatMap(mapToOptionalInt) shouldBe OptionalInt.empty
      OptionalByte.empty.flatMap(mapToOptionalLong) shouldBe OptionalLong.empty
      OptionalByte.empty.flatMap(mapToOptionalFloat).isEmpty shouldBe true
      OptionalByte.empty.flatMap(mapToOptionalDouble).isEmpty shouldBe true
      OptionalByte.empty.flatMap(mapToOptionalString) shouldBe Optional.empty[String]
    }
  }

  property("Non empty values flatMap using the passed in function") {
    forAll(bytes, flatMapFunctionsFrom[Byte]) { (value, functions) =>
      whenever(value != Byte.MinValue) {
        import functions._

        OptionalByte(value).flatMap(mapToOptionalByte) shouldBe mapToOptionalByte(value)
        OptionalByte(value).flatMap(mapToOptionalShort) shouldBe mapToOptionalShort(value)
        OptionalByte(value).flatMap(mapToOptionalInt) shouldBe mapToOptionalInt(value)
        OptionalByte(value).flatMap(mapToOptionalLong) shouldBe mapToOptionalLong(value)
        OptionalByte(value).flatMap(mapToOptionalFloat) shouldBe mapToOptionalFloat(value)
        OptionalByte(value).flatMap(mapToOptionalDouble) shouldBe mapToOptionalDouble(value)
        OptionalByte(value).flatMap(mapToOptionalString) shouldBe mapToOptionalString(value)
      }
    }
  }

  property("The empty value always applies the ifEmpty portion of a fold") {
    forAll { (ifEmpty: Byte, ifNotEmpty: Byte) =>
      whenever(ifEmpty != ifNotEmpty) {
        OptionalByte.empty.fold(ifEmpty)(_ => ifNotEmpty) shouldBe ifEmpty
      }
    }
  }

  property("Non empty values always apply the map portion of a fold") {
    forAll(bytes, bytes, mapFunctionsFrom[Byte]) { (ifEmpty, value, functions) =>
      whenever(value != Byte.MinValue) {
        import functions._

        OptionalByte(value).fold(ifEmpty)(mapToByte) shouldBe mapToByte(value)
      }
    }
  }

	property("forAll on the empty value always returns false") {
		OptionalByte.empty.forAll(_ => false) shouldBe true
	}

	property("forAll on non empty values evaluates the passed in function") {
		forAll { x: Byte =>
			whenever(x != Byte.MinValue) {
				OptionalByte(x).forAll(x => x == x) shouldBe true
				OptionalByte(x).forAll(x => x == x + 1) shouldBe false
			}
		}
	}
}