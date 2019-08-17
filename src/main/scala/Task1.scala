import java.io.File

import scala.util.{Failure, Success}
import TasksImplicits._
import com.typesafe.config.ConfigFactory

object Task1 extends Settings(ConfigFactory.parseFile(new File("src/main/resources/app.conf")))
  with App
  with DataGenerator
  with DataExtractor {

  val mapPandigitalToOne = new PartialFunction[String, Int] {
    val digits = ('0' to '9').toSet
    override def isDefinedAt(input: String): Boolean =  digits.diff(input.toSet).isEmpty
    override def apply(value: String): Int = 1
  }

  def countPandigitalNumbers(path: String)= {
    getDataSource(path, extractor.frameLength, extractor.delimiter)
      .collect(mapPandigitalToOne)
      .runFold(0)(_ + _)
  }

  generateNumbers(paths.numbersFilePath, extractor.frameLength)
    .flatMap(_ => countPandigitalNumbers(paths.numbersFilePath))
    .onComplete {
      case Success(value) =>
        println(s"Amount is = $value")
        system.terminate()
      case Failure(exception) =>
        println(s"Failed with $exception")
        system.terminate()
    }
}
