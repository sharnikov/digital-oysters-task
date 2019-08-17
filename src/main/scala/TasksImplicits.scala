import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

object TasksImplicits {
  implicit val system = ActorSystem("sys")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = ExecutionContext.fromExecutor(Executors.newWorkStealingPool())
}