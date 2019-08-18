package task2

import cats.instances.set._
import cats.syntax.semigroup._

case class Film(name: String, info: Set[String])

object Film {

  implicit val filmOrd = new Ordering[Film] {
    override def compare(x: Film, y: Film): Int =
      x.name.compare(y.name)
  }

  def mergeIntoVector(vector: Vector[Film], newElement: Film)(implicit ord: Ordering[Film]): Vector[Film] = {
    vector.size match {
      case 0 => Vector(newElement)
      case 1 if vector.head.name == newElement.name => Vector(Film(newElement.name, newElement.info |+| vector.head.info))
      case 1 => if (ord.gt(vector.head, newElement)) Vector(newElement, vector.head) else Vector(vector.head, newElement)
      case _ => goDeep(vector, vector(vector.size / 2), newElement)(ord)
    }
  }

  private def goDeep(vector: Vector[Film],
                     middleElement: Film,
                     newElement: Film)
                    (implicit ord: Ordering[Film]): Vector[Film] = {
    if (ord.gteq(newElement, middleElement)) {
      vector.take(vector.size / 2) ++ mergeIntoVector(vector.drop(vector.size / 2), newElement)(ord)
    } else {
      mergeIntoVector(vector.take(vector.size / 2), newElement)(ord) ++ vector.drop(vector.size / 2)
    }
  }
}