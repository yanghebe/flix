/*
 * Copyright 2016 Magnus Madsen
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

package ca.uwaterloo.flix.api

import ca.uwaterloo.flix.api.dsl.{RichRelation, RichValue}
import ca.uwaterloo.flix.language.ast.{ExecutableAst, Symbol}
import ca.uwaterloo.flix.runtime.Model

import scala.collection.JavaConverters._
import scala.languageFeature.implicitConversions
import scala.util.Try

object RichDSL {

  implicit def model2rich(m: IModel): RichModel = new RichModel(m.asInstanceOf[Model])

  /**
    * An enriched Flix model.
    */
  class RichModel(m: Model) {

    /**
      * Evaluates the function with the given fully-qualified name `fqn` and the given raw arguments `args`.
      */
    def eval2(fqn: String, args: AnyRef*): RichValue = new RichValue(m.eval(fqn, args))

    /**
      * Returns the relation with the given fully-qualified name `fqn`.
      *
      * @throws IllegalArgumentException if the relation does not exist.
      */
    def getRelation(fqn: String): RichRelation = {
      // TODO: Put implementation inside the opt.
      val sym = Symbol.mkTableSym(fqn)
      val attributes = m.root.tables(sym) match {
        case ExecutableAst.Table.Relation(_, attr, _) =>
          attr.map(_.name)
      }
      // TODO: Rely on m directly.
      // TODO: Throw exception if non-existent
      val data = m.getRelationIter(fqn).asScala.map {
        case row => row.asScala.toList
      }
      RichRelation(sym, attributes.toList, data.toIterable)
    }

    /**
      * Optionally returns the relation with the given fully-qualified name `fqn`.
      */
    def getRelationOpt(fqn: String): Option[RichRelation] = Try(m.getRelation(fqn)).toOption

    // TODO: Replace by better alternative.
    def getLatticeOpt(fqn: String): Option[Iterable[(List[AnyRef], AnyRef)]] = Try(m.getLattice(fqn)).toOption

  }

}