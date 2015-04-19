package scalaZExamples

import scalaz._,Scalaz._
import scala.concurrent.stm._

object ZipperExamples {
  /**
    * A Zipper is a list with a cursor. All operations happen at the
    * cursor (e.g. next, previous, modify, delete) and are all O(1)
    * with the exception of nextC and previousC when they
    * wrap. Wrapper from end to beginning or beginning to end is O(n).
    *
    *  Zippers can be intialized from a List using toZipper to
    *  constructed directly from a Left Stream, a focus value and a
    *  Right stream. Because Zipper is implemented using Streams it
    *  might be infinite.
    */
  val dayOfWeek: Zipper[String] = NonEmptyList("Sunday", "Monday",
                                               "Tuesday", "Wednesday",
                                               "Thursday", "Friday",
                                               "Saturday").toZipper

  val ints: Option[Zipper[Int]] = List(1, 2, 3, 4, 5).toZipper

  val numberLine: Zipper[Int] = Zipper(Stream.from(-1, -1),
                                       0,
                                       Stream.from(1))

  val wed = dayOfWeek.nextC.nextC.nextC // the zipper now has Wednesday at the focus


}

object AdSlotZipper {
  trait Slot
  case class SlotA(info: String) extends Slot
  case class SlotB(info: String) extends Slot

  val slots = Ref(Option.empty[Zipper[Slot]])

  def setSlots(ss: List[Slot]) = slots.single() = ss.toZipper
  def addSlot(s: Slot) =
    slots.single.transform {
      _.map (_.insert(s))
       .orElse (Zipper(Stream(), s, Stream()).some) }
  def nextSlot(): Option[Slot] = {
    val ss = slots.single.getAndTransform { _.map(_.nextC) }
    ss.map (_.focus)
  }
}
