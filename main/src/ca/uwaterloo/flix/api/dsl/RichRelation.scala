/*
 * Copyright 2016 Magnus Madsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.uwaterloo.flix.api.dsl

import ca.uwaterloo.flix.language.ast.Symbol

case class RichRelation(symbol: Symbol.TableSym, attributes: List[String], data: Iterable[List[AnyRef]]) extends Iterable[List[AnyRef]] {

  // TODO: Should we really provide this one?
  def iterator: Iterator[List[AnyRef]] = data.iterator

  def as1: Iterable[RichValue] = data map {
    case List(x1) => new RichValue(x1)
  }

  def as2: Iterable[(RichValue, RichValue)] = data map {
    case List(x1, x2) => (new RichValue(x1), new RichValue(x2))
  }

  def as3: Iterable[(RichValue, RichValue, RichValue)] = data map {
    case List(x1, x2, x3) => (new RichValue(x1), new RichValue(x2), new RichValue(x3))
  }

  def as4: Iterable[(RichValue, RichValue, RichValue, RichValue)] = data map {
    case List(x1, x2, x3, x4) => (new RichValue(x1), new RichValue(x2), new RichValue(x3), new RichValue(x4))
  }

  def as5: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue)] = data map {
    case List(x1, x2, x3, x4, x5) => (new RichValue(x1), new RichValue(x2), new RichValue(x3), new RichValue(x4), new RichValue(x5))
  }

  def as6: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue, RichValue)] = data map {
    case List(x1, x2, x3, x4, x5, x6) => (new RichValue(x1), new RichValue(x2), new RichValue(x3), new RichValue(x4), new RichValue(x5), new RichValue(x6))
  }

  def as7: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue, RichValue, RichValue)] = data map {
    case List(x1, x2, x3, x4, x5, x6, x7) => (new RichValue(x1), new RichValue(x2), new RichValue(x3), new RichValue(x4), new RichValue(x5), new RichValue(x6), new RichValue(x7))
  }

  def as8: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue, RichValue, RichValue, RichValue)] = data map {
    case List(x1, x2, x3, x4, x5, x6, x7, x8) => (new RichValue(x1), new RichValue(x2), new RichValue(x3), new RichValue(x4), new RichValue(x5), new RichValue(x6), new RichValue(x7), new RichValue(x8))
  }

  def as9: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue, RichValue, RichValue, RichValue, RichValue)] = data map {
    case List(x1, x2, x3, x4, x5, x6, x7, x8, x9) => (new RichValue(x1), new RichValue(x2), new RichValue(x3), new RichValue(x4), new RichValue(x5), new RichValue(x6), new RichValue(x7), new RichValue(x8), new RichValue(x9))
  }

}


