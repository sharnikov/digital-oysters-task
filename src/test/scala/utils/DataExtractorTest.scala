package utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import akka.testkit.TestKit
import test.utils.TestStuff

class DataExtractorTest extends TestKit(ActorSystem("MySpec")) with TestStuff {

  "getDataSource" should "read all the data correctly" in {

    implicit val materializer = ActorMaterializer()
    val extractor = new DataExtractor {}

    val result = extractor.getDataSource("src/test/resources/test-numbers.txt", 1000)
      .runWith(Sink.collection)

    await(result) shouldBe List("924", "4314", "234324", "0123456789", "12124324", "12414", "672fddf", "543dffd",
      "3534fd", "90329403425", "48025828", "428528341", "098765432100", "3423432", "234234", "5543534", "234523",
      "1451", "1234455566667778899990000", "3453553"
    )

  }

}
