package task2

import java.io.File
import java.util.concurrent.TimeUnit

import cats.instances.map._
import cats.instances.set._
import cats.syntax.semigroup._
import com.typesafe.config.ConfigFactory
import utils.TasksImplicits._
import utils.{DataExtractor, DataGenerator, Settings}

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration
import scala.util.Success


//TODO generator
//@TODO test
//@TODO config
object Task2 extends Settings(ConfigFactory.parseFile(new File("src/main/resources/app.conf")))
  with App
  with DataExtractor
  with DataGenerator {

  def aggregateFilmsInfo(path: String) = {
    getDataSource(path, extractor.frameLength, extractor.delimiter)
      .map(_.split(',').toList)
      .groupedWithin(10, FiniteDuration(1, TimeUnit.SECONDS))
      .mapAsync(4)(sample => Future { sample.groupMapReduce(_.head)(_.tail.toSet)(_ |+| _) })
      .runFold(Map.empty[String, Set[String]])(_ |+| _)
  }

   val result = aggregateFilmsInfo(paths.filmsFilePath)

  result.onComplete {
    case Success(value) =>
      println(value.foreach(println))
      system.terminate()
  }


}
