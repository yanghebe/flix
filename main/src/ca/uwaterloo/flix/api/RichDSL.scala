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

import ca.uwaterloo.flix.runtime.{Model, Value}

// TODO: Take inspiration from WrappedValue, but not everything...

object RichDSL {

  // TODO: Names .to, .as, .get?

  implicit def model2rich(m: Model): RichModel = RichModel(m)


  /**
    * An enriched Flix model.
    */
  case class RichModel(m: Model) {

    /**
      * TODO: DOC
      *
      * @param fqn
      * @return
      */
    // TODO: Needs to take arguments.
    def eval(fqn: String): RichValue = new RichValue(m.getConstant(fqn))


  }


  class RichRelation() {

  }

  class RichLattice() {

  }

  /**
    * An enriched Flix value.
    *
    * @param ref the underlying value.
    */
  class RichValue(private val ref: AnyRef) {

    /**
      * Returns `true` if `this` value is the Unit value.
      */
    def isUnit: Boolean = ref match {
      case Value.Unit => true
      case _ => false
    }

    /**
      * Returns `true` if `this` value is a true boolean.
      */
    def isTrue: Boolean = toBool

    /**
      * Returns `false` if `this` value is a false boolean.
      */
    def isFalse: Boolean = !toBool

    //
    //  def getScalaEither: Either[IValue, IValue] = ref match {
    //    case o: Value.Tag => o.tag match {
    //      case "Ok" => Right(new WrappedValue(o.value))
    //      case "Err" => Left(new WrappedValue(o.value))
    //      case tag => throw new RuntimeException(s"Unexpected non-result tag: '$tag'.")
    //    }
    //    case _ => throw new RuntimeException(s"Unexpected non-result value: '$ref'.")
    //  }
    //
    //
    //  def getScalaList: immutable.List[IValue] = ref match {
    //    case o: Value.Tag => o.tag match {
    //      case "Nil" => Nil
    //      case "Cons" =>
    //        val tuple = o.value.asInstanceOf[Value.Tuple]
    //        val hd = tuple.elms(0)
    //        val tl = tuple.elms(1)
    //        new WrappedValue(hd) :: new WrappedValue(tl).getScalaList
    //      case tag => throw new RuntimeException(s"Unexpected non-list tag: '$tag'.")
    //    }
    //    case _ => throw new RuntimeException(s"Unexpected non-list value: '$ref'.")
    //  }
    //
    //  def getScalaSet: immutable.Set[IValue] = Value.cast2set(ref).map(e => new WrappedValue(e)).toSet
    //
    //
    //  def getScalaMap: immutable.Map[IValue, IValue] = Value.cast2map(ref).foldLeft(Map.empty[IValue, IValue]) {
    //    case (macc, (k, v)) => macc + (new WrappedValue(k) -> new WrappedValue(v))
    //  }

    /**
      * Returns `this` value as a primitive boolean.
      */
    def toBool: Boolean = ref match {
      case b: java.lang.Boolean => b.booleanValue()
      case _ => throw new IllegalStateException(s"Value has non-boolean type: ${ref.getClass.getCanonicalName}.")
    }

    /**
      * Returns `this` value as a primitive character.
      */
    def toChar: Char = ref match {
      case c: java.lang.Character => c.charValue()
      case _ => throw new IllegalStateException(s"Value has non-character type: ${ref.getClass.getCanonicalName}.")
    }

    /**
      * Returns `this` value as a primitive float.
      */
    def toFloat32: Float = ref match {
      case f: java.lang.Float => f.floatValue()
      case _ => throw new IllegalStateException(s"Value has non-float type: ${ref.getClass.getCanonicalName}.")
    }

    /**
      * Returns `this` value as a primitive double.
      */
    def toFloat64: Double = ref match {
      case d: java.lang.Double => d.doubleValue()
      case _ => throw new IllegalStateException(s"Value has non-double type: ${ref.getClass.getCanonicalName}.")
    }

    def toInt8: Byte = ???

    def toInt16: Short = ???

    def toInt32: Int = ???

    def toInt64: Long = ???

    def toBigInt: java.math.BigInteger = ???

    def toStr: String = ???

    //ref match {
    //    case o: Value.Tag => o.tag match {
    //      case "None" => scala.None
    //      case "Some" => scala.Some(new WrappedValue(o.value))
    //      case tag => throw new RuntimeException(s"Unexpected non-option tag: '$tag'.")
    //    }
    //    case _ => throw new RuntimeException(s"Unexpected non-option value: '$ref'.")
    //  }

    /**
      * Returns `this` value as a 2-tuple.
      */
    def toTuple2: (RichValue, RichValue) = ref match {
      case Value.Tuple(elms) =>
        if (elms.length != 2)
          throw new IllegalStateException(s"Tuple has size ${elms.length}.")
        else
          (new RichValue(elms(0)), new RichValue(elms(1)))
      case _ => throw new IllegalStateException(s"Value has non-tuple type: ${ref.getClass.getCanonicalName}.")
    }

    //
    //  def getTagName: String = Value.cast2tag(ref).tag
    //
    //  def getTagValue: IValue = new WrappedValue(Value.cast2tag(ref).value)

    def toOption: Option[RichValue] = ???

    def toEither: Either[RichValue, RichValue] = ???

    def toList: List[RichValue] = ???

    /**
      * Returns `true` if `this` and `that` value shared the same underlying ref.
      */
    override def equals(other: Any): Boolean = other match {
      case that: RichValue => ref == that.ref
      case _ => false
    }

    /**
      * Returns the hash code of `this` value.
      */
    override def hashCode(): Int = ref.hashCode()

  }

  object Foo {

    def bar: Unit = {

      import RichModel._

      val m: Model = null

      m.eval("foo").toOption

    }

  }

}