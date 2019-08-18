package task2

trait VectorMerger {

  def mergeIntoVector[T <: Mergeable[T]](vector: Vector[T],
                                         newElement: T)
                                        (implicit ord: Ordering[T]): Vector[T] = vector.size match {
    case 0 => Vector(newElement)
    case 1 if ord.equiv(vector.head, newElement) => Vector(vector.head.merge(newElement))
    case 1 => if (ord.gt(vector.head, newElement)) Vector(newElement, vector.head) else Vector(vector.head, newElement)
    case _ => goDeep(vector, vector(vector.size / 2), newElement)
  }

  private def goDeep[T <: Mergeable[T]](vector: Vector[T],
                                        middleElement: T,
                                        newElement: T)
                                       (implicit ord: Ordering[T]): Vector[T] = {
    if (ord.gteq(newElement, middleElement)) {
      vector.take(vector.size / 2) ++ mergeIntoVector(vector.drop(vector.size / 2), newElement)
    } else {
      mergeIntoVector(vector.take(vector.size / 2), newElement) ++ vector.drop(vector.size / 2)
    }
  }
}
