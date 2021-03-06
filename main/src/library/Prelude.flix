///
/// The Option type.
///
/// An option represents an optional value.
/// The constructor `None` represents an absent value,
/// whereas the constructor `Some(v)` represents the value `v`.
///
enum Option[t] {
    case None,
    case Some(t)
}

///
/// The Result type.
///
/// A result represents a successful value or an error value.
/// The constructor `Ok(v)` represents the successful value `v`,
/// whereas the constructor `Err(v)` represents the error value `v`.
///
enum Result[t, e] {
    case Ok(t),
    case Err(e)
}

///
/// The List type.
///
/// A list is either the empty list represented by `Nil`, or
/// an element `v` followed by a list `vs` represented by `v :: vs`.
///
enum List[t] {
    case Nil,
    case Cons(t, List[t])
}

///
/// The Set type.
///
/// A set is currently represented internally as a list.
///
enum Set[t] {
    case Set(List[t])
}

///
/// The Map type.
///
/// A map is currently represented internally as a list.
///
enum Map[k, v] {
    case Map(List[(k, v)])
}

///
/// Backwards function composition. Applies the function on the right first.
///
/// Given the functions `f: b -> c` and `g: a -> b` returns a function `a -> c`
///
def <<[a, b, c](f: b -> c, g: a -> b): a -> c = x -> f(g(x))

///
/// Forwards function composition. Applies the function on the left first.
///
/// Given the functions `f: a -> b` and `g: b -> c` returns a function `a -> c`
///
def >>[a, b, c](f: a -> b, g: b -> c): a -> c = x -> g(f(x))

///
/// Pipes the given value `x` into the function `f`.
///
/// Given a value `x: a` and a function `f: a -> b` returns `f(x)`.
///
def |>[a, b](x: a, f: a -> b): b = f(x)

///
/// Pipes the given pair `p` into the function `f`.
///
/// Given a pair `x: (a1, a1)` and a function `f: ((a1, a2)) -> b` returns `f(x)`.
///
def ||>[a1, a2, b](x: (a1, a2), f: ((a1, a2)) -> b): b = f(x)

///
/// Pipes the given value `x` into the function `f`.
///
/// Given a value `x: a` and a function `f: a -> b` returns `f(x)`.
///
def <|[a, b](f: a -> b, x: a): b = f(x)

///
/// Pipes the given pair `p` into the function `f`.
///
/// Given a pair `x: (a1, a1)` and a function `f: ((a1, a2)) -> b` returns `f(x)`.
///
def <||[a1, a2, b](f: ((a1, a2)) -> b, x: (a1, a2)): b = f(x)

///
/// Alias for logical conjunction.
///
def ∧(x: Bool, y: Bool): Bool = x && y

///
/// Alias for logical disjunction.
///
def ∨(x: Bool, y: Bool): Bool = x || y

///
/// Alias for logical implication.
///
def →(x: Bool, y: Bool): Bool = !x ∨ y

///
/// Alias for logical biconditional.
///
def ↔(x: Bool, y: Bool): Bool = (x → y) ∧ (y → x)

///
/// Alias for exclusive disjunction.
///
// TODO: Add test cases
def ⊕(x: Bool, y: Bool): Bool = (x ∨ y) ∧ !(x ∧ y)

///
/// Alias for the Sheffer stroke.
///
// TODO: Add test cases
def ↑(x: Bool, y: Bool): Bool = !(x ∧ y)

///
/// Asserts that the given boolean `b` is `true`.
///
// TODO: Name?
def assert!(b: Bool): Bool =
    if (b) true else ???

///
/// Asserts that the given boolean `b` is `false`.
///
// TODO: Name?
def assertNot!(b: Bool): Bool =
    if (!b) true else ???

///
/// Asserts that the given values `x` and `y` are equal.
///
def assertEq![a](x: a, y: a): Bool = if (x == y) true else ???

///
/// Returns a freshly generated integer. (NB: Requires the --Ximpure feature flag.)
///
def genSym!: Int = genSymHook()

///
/// Prints the given value `a`. (NB: Requires the --Ximpure feature flag.)
///
def print![a](a: a): a = printHook(a)

///
/// Prints the given value `a` on a new line. (NB: Requires the --Ximpure feature flag.)
///
def println![a](a: a): a = printlnHook(a)

///
/// The commutative law asserts that for a binary operator `f` the result of `f(x, y)` is equal to `f(y, x)`.
///
law commutative[a, b](f: (a, a) -> b): Bool =
    ∀(x: a, y: a). f(x, y) == f(y, x)

///
/// The associative law asserts that for a binary operator `f` the result of `f(x, f(y, z))` is equal to `f(f(x, y), z)`.
///
law associative[a](f: (a, a) -> a): Bool =
    ∀(x: a, y: a, z: a). f(x, f(y, z)) == f(f(x, y), z)

///
/// The reflexivity law asserts that any element is less than or equal to itself.
///
law reflexive[e](⊑: (e, e) -> Bool): Bool = ∀(x: e). x ⊑ x

///
/// The anti-symmetry law asserts that if `x` is less than or equal to `y` and vice versa then the
/// two elements must be equal.
///
law antiSymmetric[e](⊑: (e, e) -> Bool): Bool = ∀(x: e, y: e). ((x ⊑ y) ∧ (y ⊑ x)) → (x == y)

///
/// The transitivity law asserts that if `x` less than or equal to `y` and `y` is less than or equal
/// to `z` then `x` must be less than or equal to `z`.
///
law transitive[e](⊑: (e, e) -> Bool): Bool = ∀(x: e, y: e, z: e). ((x ⊑ y) ∧ (y ⊑ z)) → (x ⊑ z)
