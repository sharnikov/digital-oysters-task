package test.utils

import java.util.concurrent.TimeUnit

import org.scalatest.Matchers.fail
import org.scalatest.exceptions.TestFailedException

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, Future}
import scala.reflect.ClassTag
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

trait FutureUtils {
  def await[T](future: Future[T]) = Await.result(future, FiniteDuration.apply(5, TimeUnit.SECONDS))
}
