package task2

trait Mergeable[T] {
  def merge(value: T): T
}
