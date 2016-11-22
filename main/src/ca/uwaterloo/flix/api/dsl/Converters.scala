/*
 *  Copyright 2016 Magnus Madsen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package ca.uwaterloo.flix.api.dsl

import ca.uwaterloo.flix.runtime.Value

object Converters {

  def toScala(ref: AnyRef): AnyRef = ref match {

    case java.lang.Byte => ref

    case o: Value.Tag => o.tag match {
      // Option
      case "None" => None
      case "Some" => Some(toScala(o.value))
      // Result
      case "Ok" => Right(toScala(o.value))
      case "Err" => Left(toScala(o.value))
      // List
      case "Nil" => Nil
      case "Cons" =>
        val tuple = o.value.asInstanceOf[Value.Tuple].elms
        val hd = tuple(0)
        val tl = tuple(1)
        toScala(hd) :: toScala(tl).asInstanceOf[List[AnyRef]]
    }
  }

  def toFlix(o: AnyRef): AnyRef = ???

}
