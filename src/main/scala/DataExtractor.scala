import java.nio.file.Paths

import TasksImplicits._
import akka.stream.IOResult
import akka.stream.scaladsl.{FileIO, Framing, Source}
import akka.util.ByteString

import scala.concurrent.Future

trait DataExtractor {

  def getDataSource(path: String,
                    maximumFrameLength: Int,
                    delimiter: String = "\n"): Source[String, Future[IOResult]] = {
    FileIO.fromPath(Paths.get(path))
      .via(Framing.delimiter(ByteString(delimiter), allowTruncation = true, maximumFrameLength = maximumFrameLength))
      .map(_.utf8String)
  }
}
