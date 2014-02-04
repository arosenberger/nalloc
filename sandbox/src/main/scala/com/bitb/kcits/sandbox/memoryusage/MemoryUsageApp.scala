package com.bitb.kcits.sandbox.memoryusage

import com.bitb.kcits.optional.OptionalImplicits

trait MemoryUsageApp extends App with GcSupport with MemoryRecorder with OptionalImplicits
