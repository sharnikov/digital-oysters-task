package utils

import com.typesafe.config.Config
import net.ceedubs.ficus.Ficus._
import Settings._

import scala.concurrent.duration.{Duration, FiniteDuration}

abstract class Settings(config: Config) {
  def extractor: Extractor = new ExtractorImpl(config.as[Config]("extractor"))
  def paths: Paths = new PathsImpl(config.as[Config]("paths"))
  def films: Films = new FilmsImpl(config.as[Config]("films"))
}

trait Extractor {
  def frameLength: Int
  def delimiter: String
}

class ExtractorImpl(config: Config) extends Extractor {
  override def frameLength: Int = config.getInt("frame.length")
  override def delimiter: String = config.getString("delimiter")
}

trait Paths {
  def numbersFilePath: String
  def filmsFilePath: String
}

class PathsImpl(config: Config) extends Paths {
  override def numbersFilePath: String = config.getString("numbers.file")
  override def filmsFilePath: String = config.getString("films.file")
}

trait Films {
  def chunkSize: Int
  def chunkTime: FiniteDuration
  def streamsParallelism: Int
}

class FilmsImpl(config: Config) extends Films {
  override def chunkSize: Int = config.getInt("chunk.size")
  override def chunkTime: FiniteDuration = config.getDuration("chunk.time").toScalaDuration()
  override def streamsParallelism: Int = config.getInt("streams.parallelism")
}



object Settings {
  implicit class duration(duration: java.time.Duration) {
    def toScalaDuration(): FiniteDuration = Duration.fromNanos(duration.toNanos)
  }
}