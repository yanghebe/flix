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

package ca.uwaterloo.flix.api.dsl

import ca.uwaterloo.flix.runtime.Value

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

  /**
    * Returns `true` if `this` value is a character.
    */
  def isChar: Boolean = ref match {
    case c: java.lang.Character => true
    case _ => false
  }

  /**
    * Returns `true` if `this` value is a float.
    */
  def isFloat32: Boolean = ref match {
    case f: java.lang.Float => true
    case _ => false
  }

  /**
    * Returns `true` if `this` value is a double.
    */
  def isFloat64: Boolean = ref match {
    case d: java.lang.Double => true
    case _ => false
  }

  /**
    * Returns `true` if `this` value is a byte.
    */
  def isInt8: Boolean = ref match {
    case b: java.lang.Byte => true
    case _ => false
  }

  /**
    * Returns `true` if `this` value is a short.
    */
  def isInt16: Boolean = ref match {
    case s: java.lang.Short => true
    case _ => false
  }

  /**
    * Returns `true` if `this` value is an integer.
    */
  def isInt32: Boolean = ref match {
    case s: java.lang.Integer => true
    case _ => false
  }

  /**
    * Returns `true` if `this` value is a long.
    */
  def isInt64: Boolean = ref match {
    case s: java.lang.Long => true
    case _ => false
  }

  /**
    * Returns `true` if `this` value is a big integer.
    */
  def isBigInt: Boolean = ref match {
    case s: java.math.BigInteger => true
    case _ => false
  }

  /**
    * Returns `true` if `this` value is a string.
    */
  def isStr: Boolean = ref match {
    case s: String => true
    case _ => false
  }

  /**
    * Returns `true` if `this` value is the `None` value.
    */
  def isNone: Boolean = ref match {
    case o: Value.Tag => o.tag == "None"
    case _ => false
  }

  /**
    * Returns `true` if `this` value is `Some(v)`.
    */
  def isSome: Boolean = ref match {
    case o: Value.Tag => o.tag == "Some"
    case _ => false
  }

  /**
    * Returns `true` if `this` value is `Ok(v)`.
    */
  def isOk: Boolean = ref match {
    case o: Value.Tag => o.tag == "Ok"
    case _ => false
  }

  /**
    * Returns `true` if `this` value is `Err(v)`.
    */
  def isErr: Boolean = ref match {
    case o: Value.Tag => o.tag == "Err"
    case _ => false
  }

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

  /**
    * Returns `this` value as a primitive byte.
    */
  def toInt8: Byte = ref match {
    case b: java.lang.Byte => b.byteValue()
    case _ => throw new IllegalStateException(s"Value has non-byte type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a primitive short.
    */
  def toInt16: Short = ref match {
    case s: java.lang.Short => s.shortValue()
    case _ => throw new IllegalStateException(s"Value has non-short type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a primitive int.
    */
  def toInt32: Int = ref match {
    case i: java.lang.Integer => i.intValue()
    case _ => throw new IllegalStateException(s"Value has non-integer type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a primitive long.
    */
  def toInt64: Long = ref match {
    case l: java.lang.Long => l.longValue()
    case _ => throw new IllegalStateException(s"Value has non-long type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a BigInteger.
    */
  def toBigInt: java.math.BigInteger = ref match {
    case bi: java.math.BigInteger => bi
    case _ => throw new IllegalStateException(s"Value has non-bigint type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a String.
    */
  def toStr: String = ref match {
    case s: String => s
    case _ => throw new IllegalStateException(s"Value has non-str type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a 2-tuple.
    */
  def toTuple2: (RichValue, RichValue) = ref match {
    case Value.Tuple(elms) if elms.length == 2 =>
      (new RichValue(elms(0)), new RichValue(elms(1)))
    case Value.Tuple(elms) => throw new IllegalStateException(s"Tuple has size ${elms.length}.")
    case _ => throw new IllegalStateException(s"Value has non-tuple type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a tag.
    */
  def toTag: (String, RichValue) = ref match {
    case o: Value.Tag => (o.tag, new RichValue(o.value))
    case _ => throw new IllegalStateException(s"Value has non-tag type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a Scala Option.
    */
  def toOption: Option[RichValue] = ref match {
    case o: Value.Tag => o.tag match {
      case "None" => scala.None
      case "Some" => scala.Some(new RichValue(o.value))
      case tag => throw new IllegalStateException(s"Unexpected non-option tag: '$tag'.")
    }
    case _ => throw new IllegalStateException(s"Value has non-option type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a Scala Either.
    */
  def toEither: Either[RichValue, RichValue] = ref match {
    case o: Value.Tag => o.tag match {
      case "Ok" => Right(new RichValue(o.value))
      case "Err" => Left(new RichValue(o.value))
      case tag => throw new IllegalStateException(s"Unexpected non-result tag: '$tag'.")
    }
    case _ => throw new IllegalStateException(s"Value has non-result type: ${ref.getClass.getCanonicalName}.")
  }

  /**
    * Returns `this` value as a Scala List.
    */
  def toList: List[RichValue] = ref match {
    case o: Value.Tag => o.tag match {
      case "Nil" => Nil
      case "Cons" =>
        val tuple = o.value.asInstanceOf[Value.Tuple]
        val hd = tuple.elms(0)
        val tl = tuple.elms(1)
        new RichValue(hd) :: new RichValue(tl).toList
      case tag => throw new RuntimeException(s"Unexpected non-list tag: '$tag'.")
    }
    case _ => throw new IllegalStateException(s"Value has non-list type: ${ref.getClass.getCanonicalName}.")
  }

  // TODO: Add toSet

  // TODO: Add toMap

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