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

package ca.uwaterloo.flix.api

import ca.uwaterloo.flix.language.ast.ExecutableAst

trait IModel {

  /**
    * Immediately evaluates the function with the given fully-qualified name `fqn` and the given raw arguments `args`.
    *
    * @param fqn  The fully-qualified name of the function to evaluate.
    * @param args the raw arguments to pass to the function.
    * @return the raw value returned by the function.
    * @throws IllegalArgumentException if the function does not exist.
    */
  def eval(fqn: String, args: AnyRef*): AnyRef

  def relationOf(fqn: String): java.util.Iterator[java.util.List[AnyRef]]

  // TODO: Rename and change types.
  def getLattice(name: String): Iterable[(List[AnyRef], AnyRef)]


  // TODO: To be removed.
  def getRoot: ExecutableAst.Root

}
