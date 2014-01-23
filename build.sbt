name := "mahoutScala"

version := "1.0"

scalaVersion := "2.10.2"

checksums in update := Nil

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.9.1" % "test",
  "org.apache.mahout" % "mahout-core" % "0.8",
  "org.slf4j" % "slf4j-api" % "1.6.4",
  "org.slf4j" % "slf4j-simple" % "1.6.4"
)
