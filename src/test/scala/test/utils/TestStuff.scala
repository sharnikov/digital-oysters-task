package test.utils

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpecLike, Matchers}

trait TestStuff extends FlatSpecLike
  with Matchers
  with MockFactory
  with FutureUtils
  with TestSettings
