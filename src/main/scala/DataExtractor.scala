import java.nio.file.Paths

import TasksImplicits._
import akka.stream.IOResult
import akka.stream.scaladsl.{FileIO, Framing, Source}
import akka.util.ByteString

import scala.concurrent.Future

trait DataExtractor {

  def getDataSource(path: String): Source[String, Future[IOResult]] = {
    FileIO.fromPath(Paths.get(path))
      .via(Framing.delimiter(ByteString("\n"), allowTruncation = true, maximumFrameLength = 1000))
      .mapAsync(4)(value => Future { value.utf8String })
  }
}
