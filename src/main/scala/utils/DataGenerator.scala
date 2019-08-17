package utils

import java.nio.file.Paths

import akka.stream.IOResult
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import org.scalacheck.Gen
import utils.TasksImplicits._


import scala.concurrent.Future

trait DataGenerator {

  def generateNumbers(path: String, rowsAmount: Int): Future[IOResult] = {
    generateRows(path, rowsAmount, Gen.numStr.sample.get)
  }

  def generateRows(path: String, rowsAmount: Int, generateRule: => String): Future[IOResult] = {
    val file = Paths.get(path)
    val realRowsAmount = scala.math.max(1, rowsAmount)

    Source(1 to realRowsAmount)
      .map(_ => generateRule)
      .map(number => ByteString(s"$number\n"))
      .runWith(FileIO.toPath(file))
  }
}
