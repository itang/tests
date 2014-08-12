import AssemblyKeys._

name := "test-mqtt"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  "org.fusesource.mqtt-client" % "mqtt-client" % "1.10",
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "com.alibaba" % "fastjson" % "1.1.41",
  "me.itang" % "scatang_2.11" % "0.1",
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

incOptions := incOptions.value.withNameHashing(true)

assemblySettings

mainClass in assembly := Some("example.Example")

