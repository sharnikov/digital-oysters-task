package task2

case class Film(name: String, info: Set[String])

object Film {

  def merge(film1: Film, film2: Film): Film = Film(film1.name, film1.info ++ film2.info)

  implicit val filmOrd = new Ordering[Film] {
    override def compare(x: Film, y: Film): Int =
      x.name.compare(y.name)
  }

}