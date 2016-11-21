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

import ca.uwaterloo.flix.api.dsl.RichValue
import ca.uwaterloo.flix.language.ast.{ExecutableAst, Symbol}
import ca.uwaterloo.flix.runtime.Model

import scala.collection.JavaConverters._
import scala.languageFeature.implicitConversions
import scala.util.Try

// TODO: Take inspiration from WrappedValue, but not everything...

object RichDSL {

  // TODO: Names .to, .as, .get?

  implicit def model2rich(m: IModel): RichModel = new RichModel(m)

  // TODO: Provide some kind of equivalence on Scala values and Flix values.

  def eq(o1: AnyRef, o2: AnyRef): Boolean = (o1, o2) match {
    case (None, v2: RichValue) => v2.isNone
    case (v1: RichValue, None) => v1.isNone
  }

  /**
    * An enriched Flix model.
    */
  class RichModel(m: IModel) {

    val r: ExecutableAst.Root = m.asInstanceOf[Model].root

    /**
      * TODO: DOC
      *
      * @param fqn
      * @return
      */
    // TODO: Needs to take arguments.
    // TODO: Name?
    def eval2(fqn: String, args: AnyRef*): RichValue = new RichValue(m.eval(fqn, args))


    // TODO: DELETE
    def getRelation(fqn: String): Iterator[List[AnyRef]] = {
      m.relationOf(fqn).asScala.map {
        case xs => xs.asScala.toList
      }
    }

    def getRelationX(fqn: String): RichRelation = {
      val sym = Symbol.mkTableSym(fqn)
      val attributes = r.tables(sym) match {
        case ExecutableAst.Table.Relation(_, attr, _) =>
          attr.map(_.name)
      }
      val data = m.relationOf(fqn).asScala.map {
        case row => row.asScala.toList
      }
      new RichRelation(sym, attributes.toList, data.toIterable)
    }

    def getRelation2(fqn: String): Relation2 = {
      val data = getRelationX(fqn).data.map {
        case List(x1, x2) => (new RichValue(x1), new RichValue(x2))
      }
      new Relation2(data)
    }

    def getRelation3(fqn: String): Relation3 = {
      val data = getRelationX(fqn).data.map {
        case List(x1, x2, x3) => (new RichValue(x1), new RichValue(x2), new RichValue(x3))
      }
      new Relation3(data)
    }


    // TODO: Replace by better alternative.
    def getRelationOpt(fqn: String): Option[Iterator[List[AnyRef]]] = Try(m.getRelation(fqn)).toOption

    // TODO: Replace by better alternative.
    def getLatticeOpt(fqn: String): Option[Iterable[(List[AnyRef], AnyRef)]] = Try(m.getLattice(fqn)).toOption

  }

  // TODO: Relation1, Relation2, etc.
  case class RichRelation(symbol: Symbol.TableSym, attributes: List[String], data: Iterable[List[AnyRef]]) {
    def attributesOf: List[String] = attributes
  }

  // TODO: Case classes or not? Implement iterable/traversable themselves?
  class Relation1(data: Iterable[RichValue])

  class Relation2(data: Iterable[(RichValue, RichValue)]) extends Iterable[(RichValue, RichValue)] {
    override def iterator: Iterator[(RichValue, RichValue)] = data.iterator
  }

  class Relation3(data: Iterable[(RichValue, RichValue, RichValue)])

  case class Relation4(data: Iterable[(RichValue, RichValue, RichValue, RichValue)])

  case class Relation5(data: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue)])

  case class Relation6(data: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue, RichValue)])

  case class Relation7(data: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue, RichValue)])

  case class Relation8(data: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue, RichValue)])

  case class Relation9(data: Iterable[(RichValue, RichValue, RichValue, RichValue, RichValue, RichValue, RichValue)])

  class RichLattice() {

  }


}