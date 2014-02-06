package com.bitb.kcits.optional

import com.bitb.kcits.macros._

trait OptionalResolverImplicits {
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
