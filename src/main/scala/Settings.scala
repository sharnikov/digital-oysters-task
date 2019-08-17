import com.typesafe.config.Config
import net.ceedubs.ficus.Ficus._


abstract class Settings(config: Config) {

  def extractor(): Extractor = new ExtractorImpl(config.as[Config]("extractor"))
  def paths(): Paths = new PathsImpl(config.as[Config]("paths"))
}

trait Extractor {
  def frameLength: Int
}

class ExtractorImpl(config: Config) extends Extractor {
  override def frameLength: Int = config.getInt("frame.length")
}

trait Paths {
  def numbersFilePath(): String
  def filmsFilePath(): String
}

class PathsImpl(config: Config) extends Paths {
  override def numbersFilePath(): String = config.getString("numbers.file")
  override def filmsFilePath(): String = config.getString("films.file")
}
