import java.io.File
import java.nio.file.Paths
import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, Flow, Framing, Source}
import akka.util.ByteString
import org.scalacheck.Gen

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success
import scala.util.matching.Regex
import TasksImplicits._
import com.typesafe.config.ConfigFactory

object Task1 extends Settings(ConfigFactory.parseFile(new File("src/main/resources/app.conf")))
  with App
  with DataGenerator
  with DataExtractor {

  val number: Regex = "(\\d+)".r
  val digitsSet = ('0' to '9').toSet

  def containsAllNumbers(number: String): Boolean =
    (digitsSet -- number.toSet).isEmpty

  def isNumber(maybeNumber: String): Boolean = maybeNumber match {
    case number(_) => true
    case _ => false
  }

  def calculatePandigitalNumbers(path: String)= {
    getDataSource(path)
      .collect { case string if isNumber(string) && containsAllNumbers(string) =>
        println(string)
        1
      }.runFold(0)(_ + _)
  }

  val result = for {
    _ <- generateNumbers(paths().numbersFilePath(), extractor().frameLength)
    value <- calculatePandigitalNumbers(paths().numbersFilePath())
  } yield value

  result.onComplete {
    case Success(value) =>
      println(s"Amount is = $value")
      system.terminate()
    case _ =>
      system.terminate()
  }
}
