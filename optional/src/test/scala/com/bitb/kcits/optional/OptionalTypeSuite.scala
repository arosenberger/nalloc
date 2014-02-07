package com.bitb.kcits.optional

import org.scalacheck.Arbitrary._
import org.scalacheck._
import org.scalatest._
import org.scalatest.prop.GeneratorDrivenPropertyChecks

trait OptionalTypeSuite extends PropSpec with Matchers with GeneratorDrivenPropertyChecks {
  final def bytes = arbByte.arbitrary
  final def shorts = arbShort.arbitrary
  final def ints = arbInt.arbitrary
  final def longs = arbLong.arbitrary
  final def floats = arbFloat.arbitrary
  final def doubles = arbDouble.arbitrary
  final def strings = arbString.arbitrary

  final def mapFunctionsFrom[T] =
    for {
      b <- arbFunction1[T, Byte].arbitrary
      s <- arbFunction1[T, Short].arbitrary
      i <- arbFunction1[T, Int].arbitrary
      l <- arbFunction1[T, Long].arbitrary
      f <- arbFunction1[T, Float].arbitrary
      d <- arbFunction1[T, Double].arbitrary
      st <- arbFunction1[T, String].arbitrary
    } yield MapFunctions(b, s, i, l, f, d, st)

  final def flatMapFunctionsFrom[T] =
    for {
      b <- arbFunction1[T, OptionalByte].arbitrary
      s <- arbFunction1[T, OptionalShort].arbitrary
      i <- arbFunction1[T, OptionalInt].arbitrary
      l <- arbFunction1[T, OptionalLong].arbitrary
      f <- arbFunction1[T, OptionalFloat].arbitrary
      d <- arbFunction1[T, OptionalDouble].arbitrary
      st <- arbFunction1[T, Optional[String]].arbitrary
    } yield FlatMapFunctions(b, s, i, l, f, d, st)

  implicit def genOptionalByte: Arbitrary[OptionalByte] = Arbitrary(arbByte.arbitrary.map(x => OptionalByte(x)))
  implicit def genOptionalShort: Arbitrary[OptionalShort] = Arbitrary(arbShort.arbitrary.map(x => OptionalShort(x)))
  implicit def genOptionalInt: Arbitrary[OptionalInt] = Arbitrary(arbInt.arbitrary.map(x => OptionalInt(x)))
  implicit def genOptionalLong: Arbitrary[OptionalLong] = Arbitrary(arbLong.arbitrary.map(x => OptionalLong(x)))
  implicit def genOptionalFloat: Arbitrary[OptionalFloat] = Arbitrary(arbFloat.arbitrary.map(x => OptionalFloat(x)))
  implicit def genOptionalDouble: Arbitrary[OptionalDouble] = Arbitrary(arbDouble.arbitrary.map(x => OptionalDouble(x)))
  implicit def genOptional: Arbitrary[Optional[String]] = Arbitrary(arbString.arbitrary.map(x => Optional(x)))
}

final case class MapFunctions[T](
  mapToByte: T => Byte,
  mapToShort: T => Short,
  mapToInt: T => Int,
  mapToLong: T => Long,
  mapToFloat: T => Float,
  mapToDouble: T => Double,
  mapToString: T => String)

final case class FlatMapFunctions[T](
  mapToOptionalByte: T => OptionalByte,
  mapToOptionalShort: T => OptionalShort,
  mapToOptionalInt: T => OptionalInt,
  mapToOptionalLong: T => OptionalLong,
  mapToOptionalFloat: T => OptionalFloat,
  mapToOptionalDouble: T => OptionalDouble,
  mapToOptionalString: T => Optional[String])
