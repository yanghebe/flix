package ca.uwaterloo.flix.relational

import ca.uwaterloo.flix.relational.Evaluator.{HeadAtom, KeyVar, LatticeElement, Predicate}
import org.scalatest.FunSuite

class EvaluatorTest extends FunSuite {
  test("evaluator api") {
    println(LatticeElement(3))
    val myHeadAtom: HeadAtom = HeadAtom(
      Predicate("x"),
      List(KeyVar("y"), KeyVar("z"), KeyVar("d")),
      (x: List[Any]) => x)
    println(myHeadAtom)
  }
}
