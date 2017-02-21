package ca.uwaterloo.flix.relational

class NaiveBackend extends QueryBackend {
    private var facts: List[Evaluator.Fact] = List()
    
    def hasFact(fact: Evaluator.Fact): Boolean = facts contains fact
    def addFact(fact: Evaluator.Fact): Unit = facts +:= fact
    def join(parts: List[Evaluator.BodyAtom], fact: Evaluator.Fact): List[Map[String, Any]] = {
        val knownPart = parts.find(_.predicate == fact.predicate).getOrElse(
                throw new IllegalArgumentException("The given fact must match one of the given body atoms"))
        val initialBindings = Map[String, Any]() ++ knownPart.keys.map(_.string).zip(fact.keys.map(_.thing))
        var possibilities: List[Map[String, Any]] = List()
        rec(parts, initialBindings)
        def rec(rem: List[Evaluator.BodyAtom], bindings: Map[String, Any]): Unit = {
            if (rem.isEmpty) {
                possibilities +:= bindings
            } else {
                val first = rem.head
                val rest = rem.tail
                for {
                    f <- facts
                    if f.predicate == first.predicate
                } subrec(first.keys, f.keys, bindings + (first.elmVar.string -> f.elt))
                
                def subrec(keys: List[Evaluator.KeyVar], values: List[Evaluator.Key], bind: Map[String, Any]): Unit = {
                    if (keys.isEmpty || values.isEmpty) {
                        require(keys.isEmpty && values.isEmpty)
                        rec(rest, bind)
                    } else {
                        val k = keys.head.string
                        val v = values.head.thing
                        val already = bind.get(k)
                        if (already == None || already == Some(v)) {
                            subrec(keys.tail, values.tail, bind + (k -> v))
                        }
                    }
                }
            }
        }
        possibilities
    }
    def allFacts: List[Evaluator.Fact] = facts
}