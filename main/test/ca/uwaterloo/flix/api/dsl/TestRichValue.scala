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

import java.math.BigInteger

import ca.uwaterloo.flix.TestUtils
import ca.uwaterloo.flix.api.Flix
import ca.uwaterloo.flix.util.Options
import org.scalatest.FunSuite

class TestRichValue extends FunSuite with TestUtils {

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

  test("RichValue.isChar.01") {
    val input = "def f: Char = 'a'"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isChar)
  }

  test("RichValue.isFloat32.01") {
    val input = "def f: Float32 = 42.0f32"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isFloat32)
  }

  test("RichValue.isFloat64.01") {
    val input = "def f: Float64 = 42.0f64"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isFloat64)
  }

  test("RichValue.isInt8.01") {
    val input = "def f: Int8 = 42i8"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isInt8)
  }

  test("RichValue.isInt16.01") {
    val input = "def f: Int16 = 42i16"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isInt16)
  }

  test("RichValue.isInt32.01") {
    val input = "def f: Int32 = 42i32"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isInt32)
  }

  test("RichValue.isInt64.01") {
    val input = "def f: Int64 = 42i64"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isInt64)
  }

  test("RichValue.isBigInt.01") {
    val input = "def f: BigInt = 42ii"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isBigInt)
  }

  test("RichValue.isStr.01") {
    val input = "def f: Str = \"foo\""
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isStr)
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
    assert(model.eval2("f").isSome)
  }

  test("RichValue.isOk.01") {
    val input = "def f: Result[Int, Int] = Ok(42)"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isOk)
  }

  test("RichValue.isErr.01") {
    val input = "def f: Result[Int, Int] = Err(42)"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").isErr)
  }

  test("RichValue.toBool.01") {
    val input = "def f: Bool = true"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assert(model.eval2("f").toBool)
  }

  test("RichValue.toFloat32.01") {
    val input = "def f: Float32 = 42.0f32"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(42.0)(model.eval2("f").toFloat32)
  }

  test("RichValue.toFloat64.01") {
    val input = "def f: Float64 = 42.0f64"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(42.0)(model.eval2("f").toFloat64)
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

  test("RichValue.toBigInt.01") {
    val input = "def f: BigInt = 42ii"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(new BigInteger("42"))(model.eval2("f").toBigInt)
  }

  test("RichValue.toStr.01") {
    val input = "def f: Str = \"foo\""
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult("foo")(model.eval2("f").toStr)
  }

  test("RichValue.toTag.01") {
    val input = "def f: Option[Int] = Some(42)"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    val (tag, value) = model.eval2("f").toTag
    assertResult("Some")(tag)
    assertResult(42)(value.toInt32)
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
    assertResult(42)(model.eval2("f").toOption.get.toInt32)
  }

  test("RichValue.toEither.01") {
    val input = "def f: Result[Int, Int] = Ok(42)"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(42)(model.eval2("f").toEither.right.get.toInt32)
  }

  test("RichValue.toEither.02") {
    val input = "def f: Result[Int, Int] = Err(42)"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(42)(model.eval2("f").toEither.left.get.toInt32)
  }

  test("RichValue.toList.01") {
    val input = "def f: List[Int] = Nil"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(Nil)(model.eval2("f").toList)
  }

  test("RichValue.toList.02") {
    val input = "def f: List[Int] = 1 :: 2 :: 3 :: Nil"
    val flix = new Flix().setOptions(opts).addStr(input)
    val model = flix.solve().get
    assertResult(3)(model.eval2("f").toList.length)
  }

}
