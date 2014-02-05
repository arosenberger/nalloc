package com.bitb.kcits.sandbox

import com.bitb.kcits.optional._
import java.util.Random

abstract class Inspectable extends OptionalResolverImplicits {
  private[this] val random = new Random()

  protected[this] val b = OptionalByte(random.nextInt().toByte)
  protected[this] val s = OptionalShort(random.nextInt().toShort)
  protected[this] val i = OptionalInt(random.nextInt())
  protected[this] val l = OptionalLong(random.nextLong())
  protected[this] val f = OptionalFloat(random.nextFloat())
  protected[this] val d = OptionalDouble(random.nextDouble())
  protected[this] val st = Optional(random.nextDouble().toString)
}
