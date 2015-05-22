package scalaZExamples

import scalaz._, Scalaz._
import scala.util.control.NonFatal

object EitherExample {
  /**
    * Either is similar to Option in that instances of the Either type
    * are one of two types. For Option these are Some and None but
    * only Some can carry data. A None value simply indicates that no
    * value is available (generally because an error occurred). Either
    * is compossed of two types Left and Right, both of which can
    * carry data. By convention the Right value contains a value of a
    * successful computation and Left contains information about a
    * failed computation (e.g. an error message).
    */

  /** In ScalaZ Either is spelled \/. This is intended to be remaniscent
    * of the logical disjunction (OR). Right is spelled \/- (not that
    * the dash is on the right side.)
    */
  val aRight: String \/ Int = \/-(42)  // Note infix type signature

  // Left is spelled -\/ (note the dash is on the left.)
  val aLeft: \/[String, Int] = -\/("Something BAD happened!")

  val anotherRight = 42.right[String]              // String \/ Int
  val anotherLeft = "Something BAD happened!".left // String \/ Nothing

  /** Unlike Scala.Either, \/ is "right biased". This means that methods
    * like map, flatMap and filter operate on \/- values.
    */
  val mapped = aRight map (_ + 2) // == \/-(44)
  val flatMap = aRight flatMap(safeDiv(_, 2)) // \/-(21)
  val flatMap1 = aRight flatMap(safeDiv(_, 0)) // -\/("java.lang.ArithmeticException: / by zero")
  val filtered = aRight filter(_%2 == 0) // \/-(42)

  val notMapped = aLeft map (_ + 2) // -\/("Something BAD happened")
  val notFiltered = aLeft filter(_%2 == 0) // -\/("Something BAD happened")

  /** Either can be used to lift exception-throwing functions
    * into a referentially transparent form.
    */
  def safeDiv(a: Int, b: Int): Throwable \/ Int = {
    try {
      (a / b).right
    } catch {
      case NonFatal(e) => e.left
    }
  }

  /**
    * A for comprehension can be used to sequence through several
    * computations, any of which might fail. This computation will
    * 'fail fast'; it will stop at the first -\/ value encountered.
    * Recall that a Scala for comprehension is really a combination of
    * flatMap and map.
    */
  def sillyFunction(a: Int, b: Int, c: Int, d: Int): Throwable \/ Int =
    for {
      x <- safeDiv(a, b)
      y <- safeDiv(x, c)
      z <- safeDiv(y, d)
    } yield z

  /**
    * Pattern matching can access the values inside an \/.
    */
  val result =
    safeDiv(4, 2) match {
      case -\/(e) => println(s"Error: ${e.getMessage}"); None
      case \/-(v) => Some(v)
    }

  // getOrElse and orElse provide default values for -\/.
  val result2 = safeDiv(4, 2) getOrElse (0) // 2
  val result3 = safeDiv(4, 0) getOrElse (0) // 0
  val result4 = safeDiv(4, 2) orElse(\/-(0)) // \/-(2)
  val result5 = safeDiv(4, 0) orElse(\/-(0)) // \/-(0)
}
