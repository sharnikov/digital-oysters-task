package task2

trait VectorMerger {

  def mergeIntoVector[T](vector: Vector[T],
                         newElement: T,
                         mergeFunction: (T, T) => T)
                        (implicit ord: Ordering[T]): Vector[T] = vector.size match {
    case 0 => Vector(newElement)
    case 1 if ord.equiv(vector.head, newElement) => Vector(mergeFunction(vector.head, newElement))
    case 1 => if (ord.gt(vector.head, newElement)) Vector(newElement, vector.head) else Vector(vector.head, newElement)
    case _ => goDeep(vector, vector(vector.size / 2), newElement, mergeFunction)
  }

  private def goDeep[T](vector: Vector[T],
                        middleElement: T,
                        newElement: T,
                        mergeFunction: (T, T) => T)
                       (implicit ord: Ordering[T]): Vector[T] = {
    if (ord.gteq(newElement, middleElement)) {
      vector.take(vector.size / 2) ++ mergeIntoVector(vector.drop(vector.size / 2), newElement, mergeFunction)
    } else {
      mergeIntoVector(vector.take(vector.size / 2), newElement, mergeFunction) ++ vector.drop(vector.size / 2)
    }
  }
}
