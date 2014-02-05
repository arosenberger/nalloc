package com.bitb.kcits.sandbox.memoryusage

import com.bitb.kcits.optional.OptionalResolverImplicits

trait MemoryUsageApp extends App with GcSupport with MemoryRecorder with OptionalResolverImplicits
