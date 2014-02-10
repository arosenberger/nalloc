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
