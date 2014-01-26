package com.bitb.kcits.sandbox.memoryusage

import java.lang.management.ManagementFactory

trait GcSupport {
  final private[this] var gcCount = 0L

  final protected def forceGc() {
    var localCount = gcCount

    while (localCount <= gcCount) {
      System.gc()
      localCount = currentGcCount
    }

    gcCount = localCount
  }

  final private def currentGcCount = {
    val beans = ManagementFactory.getGarbageCollectorMXBeans
    var i = 0
    val beanCount = beans.size()
    var collectionCount = 0L
    while (i < beanCount) {
      collectionCount += math.max(0, beans.get(i).getCollectionCount)
      i += 1
    }

    collectionCount
  }
}
