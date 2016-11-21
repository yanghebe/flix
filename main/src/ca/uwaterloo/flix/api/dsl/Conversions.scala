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

object Conversions {

  sealed trait Output[T] {
    def conv: T
  }

  case object Int8 extends Output[Byte] {
    def conv: AnyRef => Byte = ???
  }

  case object Str extends Output[String] {
    def conv: AnyRef => String = ???
  }

  case class Tuple2[A, B](o1: Output[A], o2: Output[B]) extends Output[(A, B)] {
    def conv: AnyRef => (A, B) = ???
  }

  case class Lst[T](o: Output[T]) extends Output[List[T]] {
    def conv: AnyRef => List[T] = ???
  }

  val foo: AnyRef = ???

  val bar: List[(Byte, String)] = Lst(Tuple2(Int8, Str)).conv("foo")

}
