package task2

import test.utils.TestStuff

class VectorMergerTest extends TestStuff {

  "mergeIntoVector" should "insert data correctly" in {

    val merger = new VectorMerger {}

    val input = Vector(
      Film("B", Set("Info2")),
      Film("F", Set("Info6")),
      Film("E", Set("Info5")),
      Film("A", Set("Info")),
      Film("C", Set("Info3")),
      Film("H", Set("Info8")),
      Film("D", Set("Info4")),
      Film("A", Set("Info3")),
      Film("F", Set("Info25")),
      Film("A", Set("Info2")),
      Film("G", Set("Info7")),
      Film("J", Set("Info10")),
      Film("I", Set("Info9"))
    )

    val expected = Vector(
      Film("A", Set("Info", "Info3", "Info2")),
      Film("B", Set("Info2")),
      Film("C", Set("Info3")),
      Film("D", Set("Info4")),
      Film("E", Set("Info5")),
      Film("F", Set("Info6", "Info25")),
      Film("G", Set("Info7")),
      Film("H", Set("Info8")),
      Film("I", Set("Info9")),
      Film("J", Set("Info10"))
    )

    input.foldLeft(Vector.empty[Film])(merger.mergeIntoVector) shouldBe expected
  }

}
