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

package ca.uwaterloo.flix.api

import ca.uwaterloo.flix.TestUtils
import ca.uwaterloo.flix.util.Options
import org.scalatest.FunSuite

class TestRichDSL extends FunSuite with TestUtils {

  import ca.uwaterloo.flix.api.RichDSL._

  val opts = Options.DefaultTest

  test("RichValue.isUnit.01") {
    val input = "def f: Unit = ()"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isUnit)
  }

  test("RichValue.isTrue.01") {
    val input = "def f: Bool = true"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isTrue)
  }

  test("RichValue.isFalse.01") {
    val input = "def f: Bool = false"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isFalse)
  }

  test("RichValue.isNone.01") {
    val input = "def f: Option[Int] = None"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isNone)
  }

  test("RichValue.isSome.01") {
    val input = "def f: Option[Int] = Some(42)"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isNone)
  }

  test("RichValue.toBool.01") {
    val input = "def f: Bool = true"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").toBool)
  }

  test("RichValue.toInt8.01") {
    val input = "def f: Int8 = 42i8"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(42)(model.eval2("f").toInt8)
  }

  test("RichValue.toInt16.01") {
    val input = "def f: Int16 = 42i16"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(42)(model.eval2("f").toInt16)
  }

  test("RichValue.toInt32.01") {
    val input = "def f: Int32 = 42i32"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(42)(model.eval2("f").toInt32)
  }

  test("RichValue.toInt64.01") {
    val input = "def f: Int64 = 42i64"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(42)(model.eval2("f").toInt64)
  }

  test("RichValue.toTuple2.01") {
    val input = "def f: (Bool, Char) = (true, 'a')"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    val (a, b) = model.eval2("f").toTuple2
    assertResult(true)(a.toBool)
    assertResult('a')(b.toChar)
  }

  test("RichValue.toOption.01") {
    val input = "def f: Option[Int] = None"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(None)(model.eval2("f").toOption)
  }

  test("RichValue.toOption.02") {
    val input = "def f: Option[Int] = Some(42)"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertValue(Some(42))(model.eval2("f"))
  }

  // TODO: Ensure that everything is tested.


}
