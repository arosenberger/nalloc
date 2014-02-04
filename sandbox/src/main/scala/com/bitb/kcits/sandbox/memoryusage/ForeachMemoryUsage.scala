package com.bitb.kcits.sandbox.memoryusage

import com.bitb.kcits.optional._
import java.util.Random
import scala.annotation.switch

object ForeachMemoryUsage extends MemoryUsageApp {
  protected def passes = 25
  private[this] val passes_ = passes
  private[this] val iterations = 1e7.toInt
  private[this] val random = new Random
  private[this] val seedValues = (1 to iterations).map(_ => random.nextLong()).toArray
  private[this] val seedValuesReverse = seedValues.reverse

  private[this] val customOptionValues = new Array[Long](passes)

  forceGc()
  touchCode()
  forceGc()
  private[this] var i = 0
  while (i < passes_) {
    forceGc()
    runCustomOptionsTest(i)
    i += 1
  }

  println(customOptionValues.sum)
  dumpMemoryStats()

  private def touchCode() {
    seedValues(0) match {
      case OptionalLong(x) => println(s"Seed Values first entry is $x")
      case _               =>
    }

    seedValuesReverse(0) match {
      case OptionalLong(x) => println(s"Seed Values Reverse first entry is $x")
      case _               =>
    }

    Long.MinValue match {
      case OptionalLong(x) => sys.error("Sentinel value unapplied")
      case _               =>
    }

    var y = 0L
    OptionalLong(10).foreach(l => y = l + 5)
    if (y != 15) sys.error("")
    if (customOptionValues(0) != 0) sys.error("")
    initMemory()
  }

  def runCustomOptionsTest(pass: Int) {
    var i = 0
    var sum = 0L
    val limit = iterations
    recordMemoryBefore(pass)
    while (i < limit) {
      val optional = (pass % 2: @switch) match {
        case 0 => seedValues(i)
        case 1 => seedValuesReverse(i)
      }

      (i % 3: @switch) match {
        case 0 => OptionalLong(optional).foreach(x => sum += x + i)
        case 1 => OptionalLong(optional).foreach(x => sum += x * i)
        case 2 => OptionalLong(optional).foreach(x => sum += x - i)
      }

      i += 1
    }
    customOptionValues(pass) = sum
    recordMemoryAfter(pass)
  }
}
