/*
 * Copyright 2015-2016 Magnus Madsen
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

package ca.uwaterloo.flix.runtime

import ca.uwaterloo.flix.api.IModel
import ca.uwaterloo.flix.language.ast.{ExecutableAst, Symbol, Time}

import scala.collection.JavaConverters._

/**
  * A class representing the minimal model.
  *
  * @param root        the abstract syntax tree of the program.
  * @param definitions the definitions in the program.
  * @param relations   the relational facts in the model.
  * @param lattices    the lattice facts in the model.
  */
final class Model(val root: ExecutableAst.Root,
                  time: Time,
                  definitions: Map[Symbol.DefnSym, () => AnyRef],
                  relations: Map[Symbol.TableSym, Iterable[List[AnyRef]]],
                  lattices: Map[Symbol.TableSym, Iterable[(List[AnyRef], AnyRef)]]) extends IModel {

  // TODO: Hide this behind an interface, IModel?

  // TODO: To be removed.
  def getRoot: ExecutableAst.Root = root


  override def eval(fqn: String, args: AnyRef*): AnyRef = {
    val sym = Symbol.mkDefnSym(fqn)
    definitions.get(sym) match {
      case None => throw new IllegalArgumentException(s"Undefined function '$fqn'.")
      case Some(fn) => fn()
    }
  }

  def relationIterator(fqn: String): java.util.Iterator[java.util.List[AnyRef]] = {
    val sym = Symbol.mkTableSym(fqn)
    relations.get(sym) match {
      case None => ??? // TODO
      case Some(rel) => {
        rel.map {
          case xs => xs.asJava
        }.asJava
      }.iterator()
    }
  }

  // TODO: Java iterable?
  // TODO: Should throw a java exception if the lattice does not exist.
  /**
    *
    * @param name
    * @return
    * @throws IllegalArgumentException if a lattice with the given `name` does not exist.
    */
  def getLattice(name: String): Iterable[(List[AnyRef], AnyRef)] = {
    // TODO: Use match
    val sym = Symbol.mkTableSym(name)
    lattices.getOrElse(sym, throw new IllegalArgumentException(s"Undefined lattice '$name."))
  }

}