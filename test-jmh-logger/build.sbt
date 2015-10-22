name := """sbt-jmh-seed"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  // Add your own project dependencies in the form:
  // "group" % "artifact" % "version"
  "org.slf4j" % "slf4j-api" % "1.7.12",
  "ch.qos.logback" % "logback-classic" % "1.1.3"
)

enablePlugins(JmhPlugin)
