name := "kafka-avro-udemy-v2"

organization := "vinyasshetty"

version := "0.1"

scalaVersion := "2.12.10"

resolvers += "confluent" at "https://packages.confluent.io/maven/"

libraryDependencies ++= Seq(
  "io.confluent" % "kafka-avro-serializer" % "5.3.0",
  "org.apache.kafka" % "kafka-clients" % "2.4.0",
  "org.apache.avro" % "avro" % "1.8.1"
)
