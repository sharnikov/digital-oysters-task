name := "digital-oysters-task"

version := "0.1"

scalaVersion := "2.12.0"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.23"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0-RC1"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0"
libraryDependencies += "com.iheart" %% "ficus" % "1.4.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % Test
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.24" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.24" % Test

