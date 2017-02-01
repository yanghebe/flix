package ca.uwaterloo.flix.relational

object Evaluator {
  sealed trait Term

  case class Key(thing: Any) extends Term
  case class LatticeElement(thing: Any) extends Term
  case class KeyVar(string: String) extends Term
  case class ElmVar(string: String) extends Term

  case class Predicate(string: String)

  case class HeadAtom(predicate: Predicate, keys: List[KeyVar], transferFn: (List[Any]) => Any)
  case class BodyAtom(predicate: Predicate, keys: List[KeyVar], elmVar: ElmVar)

  case class Rule(headAtom: HeadAtom, bodyAtoms: List[BodyAtom], filterFn: (List[Any]) => Boolean)
}
