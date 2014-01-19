package com.bitb.kcits.optional

import com.bitb.kcits.optional.Longs.OptionalLong
import java.util.Random
import org.scalatest.PropSpec
import scala.annotation.switch

class MapMemorySpec extends PropSpec with GcSupport with MemoryRecorder {
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
      case OptionalLong(x) => fail("Sentinel value unapplied")
      case _               =>
    }

    if (customOptionValues(0) != 0) fail()
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
        case 0 => sum += optional.map(_ + i)
        case 1 => sum += optional.map(_ * i)
        case 2 => sum += optional.map(_ - i)
      }

      i += 1
    }
    customOptionValues(pass) = sum
    recordMemoryAfter(pass)
  }
}
