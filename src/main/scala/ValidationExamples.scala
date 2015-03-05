package scalaZExamples

import scalaz._, Scalaz._
import scalaz.Validation.FlatMap._

object ValidationExamples {
  /**
    * Validation is similar to Either (\/) but it doesn't 'fail
    * fast'. It performs all the operations and either returns a value
    * inside Success or all of the errors inside a Failure.
    *
    * Unlike \/, Validation is not a monad so it doesn't work with a
    * for comprehension. Validation is an Applicative Functor and it
    * is most straight-forward to use it via the ApplicativeBuilder
    * syntax. It is also common to use a NonEmptyList (Nel) to store the
    * errors.
    */

  case class Person(firstName: String, lastName: String, age: Int)

  def validateNonEmptyString(field: String, value: String): ValidationNel[String, String] =
    if(value.isEmpty) s"$field cannot be empty.".failureNel
    else value.successNel

  def validateNonNegativeInteger(field: String, value: String): ValidationNel[String, Int] =
    value.parseInt
      .leftMap { e => s"$field must be an integer value.".wrapNel }
      .flatMap { i => if (i >= 0) i.successNel else s"$field cannot be negative".failureNel }

  def makePerson(firstName: String, lastName: String, ageStr: String) =
    (validateNonEmptyString("First name", firstName)
      |@| validateNonEmptyString("Last name", lastName)
      |@| validateNonNegativeInteger("Age", ageStr))(Person.apply)

  val p1 = makePerson("First", "Last", "42") // Success(Person("First", "Last", 42))
  val p2 = makePerson("", "", "42") // Failure(NonEmptyList("First name cannot be empty.", "Last name cannot be empty."))
  val p3 = makePerson("First", "Last", "age") // Failure(NonEmptyList("Age must be an integer"))
  val p4 = makePerson("", "", "-42") // Failure(NonEmptyList("First name cannot be empty.", "Last name cannot be empty.", "Age cannot be negative."))
}
