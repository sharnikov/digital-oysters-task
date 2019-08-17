import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, Framing, RunnableGraph, Source, SubFlow}
import akka.util.ByteString
import utils.TasksImplicits._


import scala.concurrent.{ExecutionContext, Future}

object Task2 extends App {

  implicit val system = ActorSystem("sys")
  implicit val materializer = ActorMaterializer()
  implicit val executors = ExecutionContext.Implicits.global

  val filePath = "src/main/resources/films.txt"

//  def calculatePandigitalNumbers(path: String) = {
//    FileIO.fromPath(utils.Paths.get(filePath))
//      .via(Framing.delimiter(ByteString("\n"), allowTruncation = true, maximumFrameLength = 1000))
//      .map(_.utf8String.split(','))
//      .groupBy(4, _.head)
//      .map(_.foldLeft("")())
//  }

}
