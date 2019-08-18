object Test extends App {
  def insertIntoVector(vector: Vector[Int], element: Int): Vector[Int] = {
    vector.size match {
      case 0 => Vector(element)
      case 1 => if (vector.head > element) Vector(element, vector.head) else Vector(vector.head, element)
      case _ =>
        val mid = vector(vector.size / 2)
        if (element > mid) vector.take(vector.size / 2) ++ insertIntoVector(vector.drop(vector.size / 2), element) else {
          insertIntoVector(vector.take(vector.size / 2), element) ++ vector.drop(vector.size / 2)
        }
    }
  }

  val list = List(6,3,87,1)
  val vector = Vector(1,4,6,2,7,2).sorted

  val s = vector.size / 2

  println(s)
  println(vector.take(s))
  println(vector.drop(s))
//  println(list.foldLeft(vector)(insertIntoVector))
}
