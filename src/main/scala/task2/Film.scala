package task2

case class Film(name: String, info: Set[String]) extends Mergeable[Film] {
  override def merge(value: Film): Film = Film(name, info ++ value.info)
}

object Film {

  implicit val filmOrd = new Ordering[Film] {
    override def compare(x: Film, y: Film): Int =
      x.name.compare(y.name)
  }

}