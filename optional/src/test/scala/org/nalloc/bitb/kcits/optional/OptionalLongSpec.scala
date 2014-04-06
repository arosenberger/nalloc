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

class OptionalLongSpec extends OptionalTypeSuite {

  property("The empty value does not unapply") {
    Long.MinValue match {
      case OptionalLong(x) => fail(s"Empty value unapplied to $x")
      case _               =>
    }
  }

  property("The empty value maps to the empty value of its target type") {
    OptionalLong.empty.map(_.toByte) shouldBe OptionalByte.empty
    OptionalLong.empty.map(_.toShort) shouldBe OptionalShort.empty
    OptionalLong.empty.map(_.toInt) shouldBe OptionalInt.empty
    OptionalLong.empty.map(_ + 1.toByte) shouldBe OptionalLong.empty
    OptionalLong.empty.map(_ + 1.toShort) shouldBe OptionalLong.empty
    OptionalLong.empty.map(_ + 1) shouldBe OptionalLong.empty
    OptionalLong.empty.map(_ + 1L) shouldBe OptionalLong.empty
    OptionalLong.empty.map(_ + 1f).isEmpty shouldBe true
    OptionalLong.empty.map(_ + 1d).isEmpty shouldBe true
  }

  property("Non empty values unapply to themselves") {
    forAll { x: Long =>
      whenever(x != Long.MinValue) {
        x match {
          case OptionalLong(unapplied) => x shouldBe unapplied
          case _                       => fail(s"$x failed to unapply")
        }
      }
    }
  }

  property("Non empty values map using the passed in function") {
    forAll(longs, mapFunctionsFrom[Long]) { (value, functions) =>
      whenever(value != Long.MinValue) {
        import functions._

        OptionalLong(value).map(mapToByte) shouldBe OptionalByte(mapToByte(value))
        OptionalLong(value).map(mapToShort) shouldBe OptionalShort(mapToShort(value))
        OptionalLong(value).map(mapToInt) shouldBe OptionalInt(mapToInt(value))
        OptionalLong(value).map(mapToLong) shouldBe OptionalLong(mapToLong(value))
        OptionalLong(value).map(mapToFloat) shouldBe OptionalFloat(mapToFloat(value))
        OptionalLong(value).map(mapToDouble) shouldBe OptionalDouble(mapToDouble(value))
        OptionalLong(value).map(mapToString) shouldBe Optional(mapToString(value))
      }
    }
  }
  property("foreach on the empty value is a no-op") {
    OptionalLong.empty.foreach(_ => fail())
  }

  property("foreach acts on non empty values") {
    forAll { x: Long =>
      whenever(x != Long.MinValue) {
        var executed = false
        OptionalLong(x).foreach(_ => executed = true)
        executed shouldBe true
      }
    }
  }

  property("exists on the empty value always returns false") {
    OptionalLong.empty.exists(_ => true) shouldBe false
  }

  property("exists on non empty values evaluates the passed in function") {
    forAll { x: Long =>
      whenever(x != Long.MinValue) {
        OptionalLong(x).exists(x => x == x) shouldBe true
        OptionalLong(x).exists(x => x == x + 1) shouldBe false
      }
    }
  }

  property("filter on the empty value always returns the empty value") {
    OptionalLong.empty.filter(_ => false) shouldBe OptionalLong.empty
    OptionalLong.empty.filter(_ => true) shouldBe OptionalLong.empty
  }

  property("filter on non empty values evaluates the passed in function") {
    forAll { x: Long =>
      whenever(x != Long.MinValue) {
        OptionalLong(x).filter(x => x == x) shouldBe OptionalLong(x)
        OptionalLong(x).filter(x => x == x + 1) shouldBe OptionalLong.empty
      }
    }
  }

  property("getOrElse on the empty value returns the passed in alternative") {
    OptionalLong.empty.getOrElse(1.toByte) shouldBe 1
  }

  property("getOrElse on non empty values does not evaluate the passed in function") {
    forAll { x: Long =>
      whenever(x != Long.MinValue) {
        OptionalLong(x).getOrElse(throw new IllegalArgumentException) shouldBe x
      }
    }
  }

  property("orElse on the empty value returns the passed in alternative") {
    OptionalLong.empty.orElse(OptionalLong(1)) shouldBe OptionalLong(1)
  }

  property("orElse on non empty values does not evaluate the passed in function") {
    forAll { x: Long =>
      whenever(x != Long.MinValue) {
        OptionalLong(x).orElse(throw new IllegalArgumentException) shouldBe OptionalLong(x)
      }
    }
  }

  property("The empty value flatMaps to the empty value of its target type") {
    forAll(flatMapFunctionsFrom[Long]) { functions =>
      import functions._

      OptionalLong.empty.flatMap(mapToOptionalByte) shouldBe OptionalByte.empty
      OptionalLong.empty.flatMap(mapToOptionalShort) shouldBe OptionalShort.empty
      OptionalLong.empty.flatMap(mapToOptionalInt) shouldBe OptionalInt.empty
      OptionalLong.empty.flatMap(mapToOptionalLong) shouldBe OptionalLong.empty
      OptionalLong.empty.flatMap(mapToOptionalFloat).isEmpty shouldBe true
      OptionalLong.empty.flatMap(mapToOptionalDouble).isEmpty shouldBe true
      OptionalLong.empty.flatMap(mapToOptionalString) shouldBe Optional.empty[String]
    }
  }

  property("Non empty values flatMap using the passed in function") {
    forAll(longs, flatMapFunctionsFrom[Long]) { (value, functions) =>
      whenever(value != Long.MinValue) {
        import functions._

        OptionalLong(value).flatMap(mapToOptionalByte) shouldBe mapToOptionalByte(value)
        OptionalLong(value).flatMap(mapToOptionalShort) shouldBe mapToOptionalShort(value)
        OptionalLong(value).flatMap(mapToOptionalInt) shouldBe mapToOptionalInt(value)
        OptionalLong(value).flatMap(mapToOptionalLong) shouldBe mapToOptionalLong(value)
        OptionalLong(value).flatMap(mapToOptionalFloat) shouldBe mapToOptionalFloat(value)
        OptionalLong(value).flatMap(mapToOptionalDouble) shouldBe mapToOptionalDouble(value)
        OptionalLong(value).flatMap(mapToOptionalString) shouldBe mapToOptionalString(value)
      }
    }
  }

  property("The empty value always applies the ifEmpty portion of a fold") {
    forAll { (ifEmpty: Long, ifNotEmpty: Long) =>
      whenever(ifEmpty != ifNotEmpty) {
        OptionalLong.empty.fold(ifEmpty)(_ => ifNotEmpty) shouldBe ifEmpty
      }
    }
  }

  property("Non empty values always apply the map portion of a fold") {
    forAll(longs, longs, mapFunctionsFrom[Long]) { (ifEmpty, value, functions) =>
      whenever(value != Long.MinValue) {
        import functions._

        OptionalLong(value).fold(ifEmpty)(mapToLong) shouldBe mapToLong(value)
      }
    }
  }

	property("forAll on the empty value always returns false") {
		OptionalLong.empty.forAll(_ => false) shouldBe true
	}

	property("forAll on non empty values evaluates the passed in function") {
		forAll { x: Long =>
			whenever(x != Long.MinValue) {
				OptionalLong(x).forAll(x => x == x) shouldBe true
				OptionalLong(x).forAll(x => x == x + 1) shouldBe false
			}
		}
	}
}
