package scalaZExamples

import scalaz._,Scalaz._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

object ApplicativeBuilderExamples {
  case class Something(s: String, i: Int)
  def doSomething(s: String, i: Int) = s"$s - $i"

  ("ABC".some |@| 1.some)(doSomething) // Some("ABC - 1")
  ("ABC".some |@| none)(doSomething)   // None

  val f1 = Future.successful("abc")
  val f2 = Future.successful(1)
  val f3 = (f1 |@| f2)(Something.apply) // Future[Something] = Success(Something("abc", 1))
}
