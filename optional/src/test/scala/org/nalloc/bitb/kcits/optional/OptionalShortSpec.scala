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

class OptionalShortSpec extends OptionalTypeSuite {

  property("The empty value does not unapply") {
    Short.MinValue match {
      case OptionalShort(x) => fail(s"Empty value unapplied to $x")
      case _                =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    forAll(mapFunctionsFrom[Short]) { functions =>
      import functions._

      OptionalShort.empty.map(mapToByte) shouldBe OptionalByte.empty
      OptionalShort.empty.map(mapToShort) shouldBe OptionalShort.empty
      OptionalShort.empty.map(mapToInt) shouldBe OptionalInt.empty
      OptionalShort.empty.map(mapToLong) shouldBe OptionalLong.empty
      OptionalShort.empty.map(mapToFloat).isEmpty shouldBe true
      OptionalShort.empty.map(mapToDouble).isEmpty shouldBe true
      OptionalShort.empty.map(mapToString) shouldBe Optional.empty[String]
    }
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
    forAll(shorts, mapFunctionsFrom[Short]) { (value, functions) =>
      whenever(value != Short.MinValue) {
        import functions._

        OptionalShort(value).map(mapToByte) shouldBe OptionalByte(mapToByte(value))
        OptionalShort(value).map(mapToShort) shouldBe OptionalShort(mapToShort(value))
        OptionalShort(value).map(mapToInt) shouldBe OptionalInt(mapToInt(value))
        OptionalShort(value).map(mapToLong) shouldBe OptionalLong(mapToLong(value))
        OptionalShort(value).map(mapToFloat) shouldBe OptionalFloat(mapToFloat(value))
        OptionalShort(value).map(mapToDouble) shouldBe OptionalDouble(mapToDouble(value))
        OptionalShort(value).map(mapToString) shouldBe Optional(mapToString(value))
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

  property("orElse on the empty value returns the passed in alternative") {
    OptionalShort.empty.getOrElse(1.toShort) shouldBe 1
  }

  property("orElse on non empty values does not evaluate the passed in function") {
    forAll { x: Short =>
      whenever(x != Short.MinValue) {
        OptionalShort(x).getOrElse(throw new IllegalArgumentException) shouldBe x
      }
    }
  }

  property("The empty value flatMaps to the empty value of its target type") {
    forAll(flatMapFunctionsFrom[Short]) { functions =>
      import functions._

      OptionalShort.empty.flatMap(mapToOptionalByte) shouldBe OptionalByte.empty
      OptionalShort.empty.flatMap(mapToOptionalShort) shouldBe OptionalShort.empty
      OptionalShort.empty.flatMap(mapToOptionalInt) shouldBe OptionalInt.empty
      OptionalShort.empty.flatMap(mapToOptionalLong) shouldBe OptionalLong.empty
      OptionalShort.empty.flatMap(mapToOptionalFloat).isEmpty shouldBe true
      OptionalShort.empty.flatMap(mapToOptionalDouble).isEmpty shouldBe true
      OptionalShort.empty.flatMap(mapToOptionalString) shouldBe Optional.empty[String]
    }
  }

  property("Non empty values flatMap using the passed in function") {
    forAll(shorts, flatMapFunctionsFrom[Short]) { (value, functions) =>
      whenever(value != Short.MinValue) {
        import functions._

        OptionalShort(value).flatMap(mapToOptionalByte) shouldBe mapToOptionalByte(value)
        OptionalShort(value).flatMap(mapToOptionalShort) shouldBe mapToOptionalShort(value)
        OptionalShort(value).flatMap(mapToOptionalInt) shouldBe mapToOptionalInt(value)
        OptionalShort(value).flatMap(mapToOptionalLong) shouldBe mapToOptionalLong(value)
        OptionalShort(value).flatMap(mapToOptionalFloat) shouldBe mapToOptionalFloat(value)
        OptionalShort(value).flatMap(mapToOptionalDouble) shouldBe mapToOptionalDouble(value)
        OptionalShort(value).flatMap(mapToOptionalString) shouldBe mapToOptionalString(value)
      }
    }
  }

  property("The empty value always applies the ifEmpty portion of a fold") {
    forAll { (ifEmpty: Short, ifNotEmpty: Short) =>
      whenever(ifEmpty != ifNotEmpty) {
        OptionalShort.empty.fold(ifEmpty)(_ => ifNotEmpty) shouldBe ifEmpty
      }
    }
  }

  property("Non empty values always apply the map portion of a fold") {
    forAll(shorts, shorts, mapFunctionsFrom[Short]) { (ifEmpty, value, functions) =>
      whenever(value != Short.MinValue) {
        import functions._

        OptionalShort(value).fold(ifEmpty)(mapToShort) shouldBe mapToShort(value)
      }
    }
  }
}
