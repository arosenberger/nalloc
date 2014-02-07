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

package com.bitb.kcits.sandbox.memoryusage

import com.bitb.kcits.optional._
import java.util.Random
import scala.annotation.switch

object FilterMemoryUsage extends MemoryUsageApp {
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

    if (OptionalLong(10).filter(x => x != x) == OptionalLong(10)) sys.error("")
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
        case 0 => sum += OptionalLong(optional).filter(x => x + i % 2 == 0 || x + i % 2 == 1).value
        case 1 => sum += OptionalLong(optional).filter(x => x * i % 2 == 0 || x * i % 2 == 1).value
        case 2 => sum += OptionalLong(optional).filter(x => x - i % 2 == 0 || x - i % 2 == 1).value
      }

      i += 1
    }
    customOptionValues(pass) = sum
    recordMemoryAfter(pass)
  }
}
