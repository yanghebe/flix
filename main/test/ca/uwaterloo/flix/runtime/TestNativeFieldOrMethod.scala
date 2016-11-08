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

package ca.uwaterloo.flix.runtime

import ca.uwaterloo.flix.api.Flix
import ca.uwaterloo.flix.util.Options
import org.scalatest.FunSuite

class TestNativeFieldOrMethod extends FunSuite {

  val opts = Options.DefaultTest

  test("Expression.NativeField.Boolean.TRUE") {
    val input = "def f: Bool = #java.lang.Boolean.TRUE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(true)(model.getConstant("f"))
  }

  test("Expression.NativeField.Boolean.FALSE") {
    val input = "def f: Bool = #java.lang.Boolean.FALSE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(false)(model.getConstant("f"))
  }

  test("Expression.NativeField.Byte.MIN_VALUE") {
    val input = "def f: Int8 = #java.lang.Byte.MIN_VALUE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(Byte.MaxValue)(model.getConstant("f"))
  }

  test("Expression.NativeField.Byte.MAX_VALUE") {
    val input = "def f: Int8 = #java.lang.Byte.MAX_VALUE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(Byte.MaxValue)(model.getConstant("f"))
  }

  test("Expression.NativeField.Short.MIN_VALUE") {
    val input = "def f: Int16 = #java.lang.Short.MIN_VALUE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(Short.MaxValue)(model.getConstant("f"))
  }

  test("Expression.NativeField.Short.MAX_VALUE") {
    val input = "def f: Int16 = #java.lang.Short.MAX_VALUE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(Short.MaxValue)(model.getConstant("f"))
  }

  test("Expression.NativeField.Integer.MIN_VALUE") {
    val input = "def f: Int32 = #java.lang.Integer.MIN_VALUE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(Int.MaxValue)(model.getConstant("f"))
  }

  test("Expression.NativeField.Integer.MAX_VALUE") {
    val input = "def f: Int32 = #java.lang.Integer.MAX_VALUE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(Int.MaxValue)(model.getConstant("f"))
  }

  test("Expression.NativeField.Long.MIN_VALUE") {
    val input = "def f: Int64 = #java.lang.Long.MIN_VALUE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(Long.MaxValue)(model.getConstant("f"))
  }

  test("Expression.NativeField.Long.MAX_VALUE") {
    val input = "def f: Int64 = #java.lang.Long.MAX_VALUE"
    val model = new Flix().setOptions(opts).addStr(input).solve().get
    assertResult(Long.MaxValue)(model.getConstant("f"))
  }

}
