package com.bitb.kcits.optional

import com.bitb.kcits.macros.OptionalResolver

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
}
