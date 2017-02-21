package ca.uwaterloo.flix.relational

import scala.collection.mutable.{HashMap, Set, MultiMap}

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
    case class Fact(predicate: Predicate, keys: List[Key], elt: LatticeElement)
    
    def substitute(atom: HeadAtom, environment: Map[String, Any]): Fact = {
        // TODO (transferFn?)
        throw new RuntimeException()
    }
    
    def evaluate(rules: List[Rule], facts: List[Fact]): List[Fact] = {
        val queue = new scala.collection.mutable.Queue[Fact]
        val backend: QueryBackend = new NaiveBackend
        // relevantRules takes a predicate and gives us the
        // list of all rules that should be checked when a new fact
        // of the given predicate is discovered
        val relevantRules: MultiMap[Predicate, Rule] = {
            def listToMultiMap[A, B](list: List[(A, B)]) = 
                list.foldLeft(new HashMap[A, Set[B]] with MultiMap[A, B]){(acc, pair) => acc.addBinding(pair._1, pair._2)}
            listToMultiMap {
                for {
                    rule <- rules
                    bodyAtom <- rule.bodyAtoms
                } yield (bodyAtom.predicate -> rule)
            }
        }
        while (!queue.isEmpty) {
            val newFact = queue.dequeue()
            for {
                relevant <- relevantRules.get(newFact.predicate)
                rule <- relevant
                bindings <- backend.join(rule.bodyAtoms, newFact)
            } {
                val deduction = substitute(rule.headAtom, bindings)
                if (!backend.hasFact(deduction)) {
                    backend.addFact(deduction)
                    queue += deduction
                }
            }
        }
        backend.allFacts
    }
}
