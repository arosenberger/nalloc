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

package org.nalloc.bitb.kcits.sandbox.foreach

import org.nalloc.bitb.kcits.sandbox.Inspectable

class MethodValue extends Inspectable {
  b.foreach(println)
  s.foreach(println)
  i.foreach(println)
  l.foreach(println)
  f.foreach(println)
  d.foreach(println)
  st.foreach(println)
}
