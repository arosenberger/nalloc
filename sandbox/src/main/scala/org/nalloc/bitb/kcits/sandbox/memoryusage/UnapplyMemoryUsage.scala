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

package org.nalloc.bitb.kcits.sandbox.memoryusage

import java.util.Random
import org.nalloc.bitb.kcits.optional.OptionalLong
import org.nalloc.bitb.kcits.optional._
import scala.annotation.switch

object UnapplyMemoryUsage extends MemoryUsageApp {

  protected def passes = 25
  private[this] val passes_ = passes
  private[this] val iterations = 1e7.toInt
  private[this] val random = new Random
  private[this] val seedValues = (1 to iterations).map(_ => random.nextLong()).toArray
  private[this] val seedValuesReverse = seedValues.reverse
  private[this] val seedValuesSum = seedValues.sum

  private[this] val customOptionValues = new Array[Long](iterations)

  forceGc()
  touchCode()
  forceGc()
  Thread.sleep(1000)
  forceGc()
  private[this] var i = 0
  while (i < passes_) {
    forceGc()
    runCustomOptionsTest(i)
    i += 1
  }

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

    println(OptionalLong(1).map(_ + 1).value)

    if (seedValuesSum == 0) sys.error("")
    if (customOptionValues(0) != 0) sys.error("")
    initMemory()
  }

  def runCustomOptionsTest(pass: Int) {
    var i = 0
    val limit = iterations
    recordMemoryBefore(pass)
    while (i < limit) {
      val optional = (pass % 2: @switch) match {
        case 0 => seedValues(i)
        case 1 => seedValuesReverse(i)
      }

      optional match {
        case OptionalLong(x) => customOptionValues(i) = x
        case _               =>
      }

      i += 1
    }
    recordMemoryAfter(pass)
    var sum = 0L
    i = 0
    while (i < limit) {
      sum += customOptionValues(i)
      i += 1
    }

    if (sum != seedValuesSum)
      sys.error("")
  }
}
