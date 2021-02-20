package part1recap

import scala.concurrent.Future
import scala.util.{Failure, Success}

object ScalaRecap extends App{

  // values and variables
  val aBoolean: Boolean = false

  // expressions
  val anIfExpression = if(2<3) "smaller" else "bigger"

  // instructions vs expressions
  val theUnit: Unit = println("stuff")

  // functions
  def myFunc(x: Int) = 42

  // OOP
  class Animal
  class Cat extends Animal
  trait Carnivore{
    def eat(animal: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(animal: Animal): Unit = println("crl")
  }

  // singleton pattern
  object MySingleton

  // companions
  object Carnivore

  // generics
  trait MyList[A]

  // method notation
  val x = 1 + 2
  val y = 1.+(2)

  // functional programming
  val incrementer: Int => Int = x => x +1
  val incremented = incrementer(42)

  // map, flatmap, filter
  val processedList = List(1,2,4).map(incrementer)

  // pattern matching
  val unknown:Any = 45
  val ordinary = unknown match {
    case 1 => "one"
    case 2 => "two"
    case _ => "unknown"
  }

  // try-catch
  try{
    throw new NotImplementedError()
  } catch {
    case _: NullPointerException => "npe"
    case _: NotImplementedError => "nie"
  }

  // future
  import scala.concurrent.ExecutionContext.Implicits.global
  val aFuture = Future {
    42 // expensive computation, runs on another thread
  }
  aFuture.onComplete {
    case Success(value) => println(s"i've found $value")
    case Failure(exception) => println(s"$exception")
  }

  // val Partial functions
  val aPartialFunc: PartialFunction[Int, Int] = {
    case 1 => 43
    case 8 => 2
    case _ => 999
  }

  // Implicits
  // auto-injection by the compiler
  def methodWithImplicitArg(implicit crl: Int): Int = crl + 369
  implicit val crl: Int = 0
  val implicitCall = methodWithImplicitArg

  // implicit conversions - implicit defs
  case class Person(name: String) {
    def greet: Unit = println(s"zup, im $name")
  }
  implicit def stringToPerson(name: String): Person = Person(name)
  "bob".greet // stringToPerson("bob").greet

  // implicit conversion - implicit classes

  implicit class Dog(name: String){
    def bark: Unit = println("Bark!")
  }
  "Lassie".bark






}
