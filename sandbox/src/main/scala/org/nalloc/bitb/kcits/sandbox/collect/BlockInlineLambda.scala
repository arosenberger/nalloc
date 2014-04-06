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

package org.nalloc.bitb.kcits.sandbox.collect

import org.nalloc.bitb.kcits.optional._
import org.nalloc.bitb.kcits.sandbox.Inspectable

class BlockInlineLambda extends Inspectable {
	private[this] val be = b.collect { case x =>
		println(x)
		x
	}
	private[this] val se = s.collect { case x =>
		println(x)
		x
	}
	private[this] val ie = i.collect { case x =>
		println(x)
		x
	}
	private[this] val le = l.collect { case x =>
		println(x)
		x
	}
	private[this] val fe = f.collect { case x =>
		println(x)
		x
	}
	private[this] val de = d.collect { case x =>
		println(x)
		x
	}
	private[this] val ste = st.collect { case x =>
		println(x)
		x
	}
}
