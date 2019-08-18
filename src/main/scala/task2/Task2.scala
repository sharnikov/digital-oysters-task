package task2

import java.io.File

import cats.instances.map._
import cats.instances.set._
import cats.syntax.semigroup._
import com.typesafe.config.ConfigFactory
import utils.TasksImplicits._
import utils.{DataExtractor, Settings}
import Film._

import scala.concurrent.Future
import scala.util.{Failure, Success}

//TODO test
object Task2 extends Settings(ConfigFactory.parseFile(new File("src/main/resources/app.conf")))
  with App
  with DataExtractor {

  val infoGetterV1 = new PartialFunction[String, List[String]] {
    override def isDefinedAt(value: String): Boolean = value.split(',').nonEmpty
    override def apply(value: String): List[String] = value.split(',').toList
  }

  val infoGetterV2 = new PartialFunction[String, Film] {
    override def isDefinedAt(value: String): Boolean = value.split(',').nonEmpty
    override def apply(value: String): Film = {
      val infoList = value.split(',').toList
      Film(infoList.head, infoList.tail.map(_.trim).toSet)
    }
  }

  def aggregateFilmsInfoParallel(path: String)=
    getDataSource(path, extractor.frameLength, extractor.delimiter)
      .collect(infoGetterV1)
      .groupedWithin(films.chunkSize, films.chunkTime)
      .mapAsync(films.streamsParallelism)(sample => Future { sample.groupMapReduce(_.head)(_.tail.toSet)(_ |+| _) })
      .runFold(Map.empty[String, Set[String]])(_ |+| _)
      .map(_.toList.sortBy(_._1))

  def aggregateFilmsInfoWithVector(path: String) =
    getDataSource(path, extractor.frameLength, extractor.delimiter)
      .collect(infoGetterV2)
      .runFold(Vector.empty[Film])(mergeIntoVector)

  aggregateFilmsInfoWithVector(paths.filmsFilePath)
    .onComplete {
      case Success(value) =>
        println(value.foreach(println))
        system.terminate()
      case Failure(exception) =>
        println(s"Failed with $exception")
        system.terminate()
    }

}
