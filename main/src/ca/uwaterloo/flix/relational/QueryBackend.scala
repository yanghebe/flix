package ca.uwaterloo.flix.relational

trait QueryBackend {
    def hasFact(fact: Evaluator.Fact): Boolean
    
    // This is the only method which modifies state
    def addFact(fact: Evaluator.Fact): Unit
    
    def allFacts: List[Evaluator.Fact]
    
    // Preconditions:
    // - hasFact(fact)
    // - fact.predicate matches one of the predicates in parts
    // Returns a list of environments in which all symbols referenced
    // in parts have valid bindings, additionally satisfying the
    // condition that the atom which matches fact has its symbols
    // bound to the values in fact
    def join(parts: List[Evaluator.BodyAtom], fact: Evaluator.Fact): List[Map[String, Any]]
}