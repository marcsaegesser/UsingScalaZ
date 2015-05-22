package scalaZExamples

import scala.language.implicitConversions

trait Plusser[A] {
  def plus(x: A, y: A): A
}

object Plusser {
  implicit val IntPlusser = new Plusser[Int] {
    def plus(x: Int, y: Int): Int = x + y
  }

  implicit val DoublePlusser = new Plusser[Double] {
    def plus(x: Double, y: Double): Double = x + y
  }

  implicit def ListPlusser[A] = new Plusser[List[A]] {
    def plus(x: List[A], y: List[A]): List[A] = x ++ y
  }

  implicit def ToIntPlusserOps(x: Int): PlusserOps[Int] = new PlusserOps[Int](x)
  implicit def ToDoublePlusserOps(x: Double): PlusserOps[Double] = new PlusserOps[Double](x)
  implicit def ToListPlusserOps[A](x: List[A]): PlusserOps[List[A]] = new PlusserOps[List[A]](x)
}

class PlusserOps[A](val x: A)(implicit val P: Plusser[A]) {
  def plus(y: A) = P.plus(x, y)
}

object PlusserExample {
  import Plusser._
  def intUse(a: Int, b: Int) = a plus b
  def listUse[A](a: List[A], b: List[A]) = a plus b
}
