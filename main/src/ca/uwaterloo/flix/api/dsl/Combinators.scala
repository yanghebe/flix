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

object Combinators {

  sealed trait Convert[T] {
    //def produce: T
    def consume: T => AnyRef
  }

  case object Int8 extends Convert[Byte] {
    def produce: (AnyRef) => Byte = ???
    def consume: (Byte) => AnyRef = ???
  }

  case object Str extends Convert[String] {
    def produce: (AnyRef) => String = ???
    def consume: (String) => AnyRef = ???
  }

  case class Tuple2[A, B](o1: Convert[A], o2: Convert[B]) extends Convert[(A, B)] {
    def produce: (AnyRef) => (A, B) = ???
    def consume: ((A, B))=> AnyRef = ???
  }

  case class Lst[T](o: Convert[T]) extends Convert[List[T]] {
    def produce: (AnyRef) => List[T] = ???
    def consume: (List[T]) => AnyRef = ???
  }

  val foo: AnyRef = ???

  val bar: List[(Byte, String)] = Lst(Tuple2(Int8, Str)).produce("foo")

}
