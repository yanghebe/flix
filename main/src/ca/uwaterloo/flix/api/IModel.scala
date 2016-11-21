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

  // TODO: To be removed.
  def getRoot: ExecutableAst.Root

  // TODO: Rename
  def getConstant(name: String, args: AnyRef*): AnyRef

  // TODO: Rename and change types.
  def getRelation(name: String): Iterable[List[AnyRef]]

  // TODO: Rename and change types.
  def getLattice(name: String): Iterable[(List[AnyRef], AnyRef)]

}
