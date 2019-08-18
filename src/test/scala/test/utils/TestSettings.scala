package test.utils

import org.scalamock.scalatest.MockFactory
import utils.{Extractor, Films, Paths, Settings}

trait TestSettings extends MockFactory {

  val extractor = stub[Extractor]
  val paths = stub[Paths]
  val films = stub[Films]

  val settings = stub[Settings]

  (settings.extractor _).when().returns(extractor)
  (settings.paths _).when().returns(paths)
  (settings.films _).when().returns(films)

}
