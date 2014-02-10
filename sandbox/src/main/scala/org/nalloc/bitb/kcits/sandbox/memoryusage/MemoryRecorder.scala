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

trait MemoryRecorder {
  final private[this] val customOptionMemoryBefore = new Array[Long](passes)
  final private[this] val customOptionMemoryAfter = new Array[Long](passes)

  protected def passes: Int

  final protected def recordMemoryBefore(pass: Int) {
    customOptionMemoryBefore(pass) = freeMemory
  }

  final protected def recordMemoryAfter(pass: Int) {
    customOptionMemoryAfter(pass) = freeMemory
  }

  final protected def dumpMemoryStats() {
    customOptionMemoryBefore.zip(customOptionMemoryAfter).zipWithIndex.foreach {
      case ((before, after), index) =>
        println(s"Iteration $index: Before = $before After = $after Difference = ${before - after}")
    }
  }

  final protected def initMemory() {
    if (customOptionMemoryBefore(0) != 0) sys.error("")
    if (customOptionMemoryAfter(0) != 0) sys.error("")
    println(freeMemory)
  }

  final private def freeMemory = Runtime.getRuntime.freeMemory()
}
