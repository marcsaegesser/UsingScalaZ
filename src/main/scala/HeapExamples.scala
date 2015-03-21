package scalaZExamples

import scalaz._, Scalaz._
import org.joda.time.DateTime
import org.joda.time.format._

trait HeapExamples {
  /**
    * A Heap is an efficient implementation of an immutable priority
    * queue. The type stored in the Heap must provide (via an
    * implicit) an Order. The Heap stores elements in increasing
    * Order. In ScalaZ an Order defines *how* values of a type are
    * compared to provide the concept of less than, equal and
    * greater. An Ordering is the result of comparing two items (EQ,
    * LT, GT).
    */
  case class Employee(id: Int, firstName: String, lastName: String, age: Int, hireDate: DateTime)

  import Ordering._

  implicit val nameOrder = new Order[Employee] {
    def order(x: Employee, y: Employee) = {
      if(x.lastName < y.lastName) LT
      else if(x.lastName > y.lastName) GT
      else if(x.firstName < y.firstName) LT
      else if(x.firstName > y.firstName) GT
      else EQ
    }
  }

  val fmt = DateTimeFormat.forPattern("MM/DD/YYY")
  val emp1 = Employee(1, "John", "Doe", 20, DateTime.parse("1/1/2014", fmt))
  val emp2 = Employee(2, "Jane", "Doe", 50, DateTime.parse("2/2/2001", fmt))
  val emp3 = Employee(3, "Bill", "Smith", 19, DateTime.parse("3/1/1990", fmt))

  // employees
  val employeesByName = Heap.fromData(List(emp3, emp2, emp1))
}

object EmployeesWithDateOrder extends HeapExamples {

  implicit val hireDateOrder = new Order[Employee] {
    def order(x: Employee, y: Employee) = Ordering.fromInt(x.hireDate.compareTo(y.hireDate))
  }

  // employees
  val employeesByHireDate = Heap.fromData(List(emp3, emp2, emp1))
}

object EmployeesWithIdOrder extends HeapExamples {

  // implicit val employeeIdOrder = Order.orderBy((e: Employee) => e.id)
  implicit val employeeIdOrder: Order[Employee] = Order.orderBy(_.id)


  // employees
  val employeesById = Heap.fromData(List(emp3, emp2, emp1))
}
