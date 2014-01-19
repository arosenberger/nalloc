package com.bitb.kcits.optional

import org.scalatest.Suite

trait MemoryRecorder {self: Suite =>
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
    if (customOptionMemoryBefore(0) != 0) fail()
    if (customOptionMemoryAfter(0) != 0) fail()
    println(freeMemory)
  }

  final private def freeMemory = Runtime.getRuntime.freeMemory()
}
