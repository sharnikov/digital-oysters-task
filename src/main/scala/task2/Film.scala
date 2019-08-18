package task2

import cats.instances.set._
import cats.syntax.semigroup._

case class Film(name: String, info: Set[String])

object Film {

  implicit val filmOrd = new Ordering[Film] {
    override def compare(x: Film, y: Film): Int =
      x.name.compare(y.name)
  }

  def mergeIntoVector(vector: Vector[Film], element: Film)(implicit ord: Ordering[Film]): Vector[Film] = {
    vector.size match {
      case 0 => Vector(element)
      case 1 if vector.head.name == element.name => Vector(Film(element.name, element.info |+| vector.head.info))
      case 1 => if (ord.gt(vector.head, element)) Vector(element, vector.head) else Vector(vector.head, element)
      case _ =>
        val mid = vector(vector.size / 2)
        if (ord.gteq(element, mid)) vector.take(vector.size / 2) ++ mergeIntoVector(vector.drop(vector.size / 2), element)(ord) else {
          mergeIntoVector(vector.take(vector.size / 2), element)(ord) ++ vector.drop(vector.size / 2)
        }
    }
  }
}