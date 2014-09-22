import AssemblyKeys._

name := "test-spray-json"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  "io.spray" %%  "spray-json" % "1.3.0",
  "me.itang" %% "scatang" % "0.1",
  "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

resolvers += "itang's repos" at "http://itang.github.io/maven-repo"

resolvers += "spray repos" at "http://repo.spray.io/"

incOptions := incOptions.value.withNameHashing(true)

assemblySettings

mainClass in assembly := Some("Main")

net.virtualvoid.sbt.graph.Plugin.graphSettings

initialCommands in console := "import scatang._;import scatang.string._"


