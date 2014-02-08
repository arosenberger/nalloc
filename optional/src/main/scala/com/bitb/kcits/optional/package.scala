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

package com.bitb.kcits

import com.bitb.kcits.macros._

package object optional {
  implicit object OptionalByteResolver extends OptionalResolver[Byte] {
    type OptionalType = OptionalByte
  }

  implicit object OptionalShortResolver extends OptionalResolver[Short] {
    type OptionalType = OptionalShort
  }

  implicit object OptionalIntResolver extends OptionalResolver[Int] {
    type OptionalType = OptionalInt
  }

  implicit object OptionalLongResolver extends OptionalResolver[Long] {
    type OptionalType = OptionalLong
  }

  implicit object OptionalFloatResolver extends OptionalResolver[Float] {
    type OptionalType = OptionalFloat
  }

  implicit object OptionalDoubleResolver extends OptionalResolver[Double] {
    type OptionalType = OptionalDouble
  }

  implicit def OptionalRefResolver[A >: Null <: AnyRef]: OptionalResolver[A] {type OptionalType = Optional[A]} = new OptionalResolver[A] {
    type OptionalType = Optional[A]
  }

  implicit object ByteResolver extends PrimitiveResolver[OptionalByte] {
    type PrimitiveType = Byte
  }

  implicit object ShortResolver extends PrimitiveResolver[OptionalShort] {
    type PrimitiveType = Short
  }

  implicit object IntResolver extends PrimitiveResolver[OptionalInt] {
    type PrimitiveType = Int
  }

  implicit object LongResolver extends PrimitiveResolver[OptionalLong] {
    type PrimitiveType = Long
  }

  implicit object FloatResolver extends PrimitiveResolver[OptionalFloat] {
    type PrimitiveType = Float
  }

  implicit object DoubleResolver extends PrimitiveResolver[OptionalDouble] {
    type PrimitiveType = Double
  }

  implicit def AnyRefResolver[A >: Null <: AnyRef]: PrimitiveResolver[Optional[A]] {type PrimitiveType = A} = new PrimitiveResolver[Optional[A]] {
    type PrimitiveType = A
  }
}