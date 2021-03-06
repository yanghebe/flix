/*
 * Copyright 2015-2016 Ming-Ho Yee
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

package ca.uwaterloo.flix.language.phase

import ca.uwaterloo.flix.language.ast.SimplifiedAst.Expression
import ca.uwaterloo.flix.language.ast.{SimplifiedAst, Type}
import ca.uwaterloo.flix.util.InternalCompilerException

/**
  * Assigns stack offsets to each variable symbol in the program.
  *
  * On the JVM, each method has a local variable array, and each variable is referenced by a 0-based offset. The first
  * few slots in the array are initialized to the values of the parameters. Normally each value takes up a single
  * slot, but longs and doubles require two consecutive slots. Thus, the n-th variable may not necessarily be the
  * n-th slot. This phase computes the specific offsets used by each formal parameter and local variable.
  */
object VarNumbering {

  /**
    * Assigns a stack offset to each variable symbol in the program.
    */
  def number(root: SimplifiedAst.Root): SimplifiedAst.Root = {
    val t = System.nanoTime()

    // Compute stack offset for each definition.
    for ((sym, defn) <- root.definitions) {
      number(defn)
    }

    // TODO: Compute stack offsets for each fact and rule.

    val e = System.nanoTime() - t
    root.copy(time = root.time.copy(varNumbering = e))
  }

  /**
    * Assigns stack offsets to the given definition.
    *
    * Returns Unit since the variable symbols are mutated to store their stack offsets.
    */
  def number(defn: SimplifiedAst.Definition.Constant): Unit = {
    /**
      * Returns the next available stack offset.
      *
      * @param e0 the current expression.
      * @param i0 the current stack offset.
      */
    def visitExp(e0: Expression, i0: Int): Int = e0 match {
      case Expression.Unit => i0
      case Expression.True => i0
      case Expression.False => i0
      case Expression.Char(lit) => i0
      case Expression.Float32(lit) => i0
      case Expression.Float64(lit) => i0
      case Expression.Int8(lit) => i0
      case Expression.Int16(lit) => i0
      case Expression.Int32(lit) => i0
      case Expression.Int64(lit) => i0
      case Expression.BigInt(lit) => i0
      case Expression.Str(lit) => i0
      case Expression.LoadBool(n, o) => i0
      case Expression.LoadInt8(b, o) => i0
      case Expression.LoadInt16(b, o) => i0
      case Expression.LoadInt32(b, o) => i0
      case Expression.StoreBool(b, o, v) => i0
      case Expression.StoreInt8(b, o, v) => i0
      case Expression.StoreInt16(b, o, v) => i0
      case Expression.StoreInt32(b, o, v) => i0
      case Expression.Var(sym, tpe, loc) => i0
      case Expression.Ref(name, tpe, loc) => i0
      case Expression.Hook(hook, tpe, loc) => i0
      case Expression.MkClosureRef(ref, freeVars, tpe, loc) => i0
      case Expression.ApplyRef(name, args, tpe, loc) => visitExps(args, i0)
      case Expression.ApplyTail(name, formals, args, tpe, loc) => visitExps(args, i0)
      case Expression.ApplyHook(hook, args, tpe, loc) => visitExps(args, i0)
      case Expression.Apply(exp, args, tpe, loc) =>
        val i = visitExp(exp, i0)
        visitExps(args, i)
      case Expression.Unary(op, exp, tpe, loc) => visitExp(exp, i0)
      case Expression.Binary(op, exp1, exp2, tpe, loc) =>
        val i1 = visitExp(exp1, i0)
        visitExp(exp2, i1)
      case Expression.IfThenElse(exp1, exp2, exp3, tpe, loc) =>
        val i1 = visitExp(exp1, i0)
        val i2 = visitExp(exp2, i1)
        visitExp(exp3, i2)
      case Expression.Let(sym, exp1, exp2, tpe, loc) =>
        // Set the stack offset for the symbol.
        sym.setStackOffset(i0)

        // Compute the next free stack offset.
        val i1 = i0 + getStackSize(exp1.tpe)

        // Visit the let-bound value expression.
        val i2 = visitExp(exp1, i1)

        // Visit the let-body expression.
        visitExp(exp2, i2)
      case Expression.Is(exp, tag, loc) => visitExp(exp, i0)
      case Expression.Tag(enum, tag, exp, tpe, loc) => visitExp(exp, i0)
      case Expression.Untag(tag, exp, tpe, loc) => visitExp(exp, i0)
      case Expression.GetTupleIndex(exp, index, tpe, loc) => visitExp(exp, i0)
      case Expression.Tuple(elms, tpe, loc) => visitExps(elms, i0)
      case Expression.Existential(params, exp, loc) => visitExp(exp, i0)
      case Expression.Universal(params, exp, loc) => visitExp(exp, i0)
      case Expression.UserError(tpe, loc) => i0
      case Expression.MatchError(tpe, loc) => i0
      case Expression.SwitchError(tpe, loc) => i0
      case Expression.Lambda(args, body, tpe, loc) =>
        throw InternalCompilerException("Lambdas should have been converted to closures and lifted.")
      case Expression.MkClosure(lambda, freeVars, tpe, loc) =>
        throw InternalCompilerException("MkClosure should have been replaced by MkClosureRef after lambda lifting.")
    }

    /**
      * Returns the next available stack offset.
      */
    def visitExps(es: List[Expression], i: Int): Int = es match {
      case Nil => i
      case x :: xs =>
        val i2 = visitExp(x, i)
        visitExps(xs, i2)
    }

    // Compute the stack offset for each formal parameter.
    var offset = 0
    for (SimplifiedAst.FormalParam(sym, tpe) <- defn.formals) {
      // Set the stack offset for the symbol.
      sym.setStackOffset(offset)

      // Update the next available stack offset.
      offset += getStackSize(tpe)
    }

    // Compute stack offset for the body.
    visitExp(defn.exp, offset)
  }

  /**
    * Returns the stack size used by the given type.
    *
    * A double or float uses two slots on the stack.
    * Everything else uses one slot.
    */
  private def getStackSize(tpe: Type): Int = tpe match {
    case Type.Int64 | Type.Float64 => 2
    case _ => 1
  }

}
