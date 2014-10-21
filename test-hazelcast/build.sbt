import AssemblyKeys._

name := "test-hazelcast"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.3"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7", "-Xlint")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  "com.hazelcast" % "hazelcast" % "3.3.1",
  "com.hazelcast" % "hazelcast-client" % "3.3.1",
  "me.itang" %% "scatang" % "0.1",
  "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

resolvers += "itang's repos" at "http://itang.github.io/maven-repo"

incOptions := incOptions.value.withNameHashing(true)

assemblySettings

mainClass in assembly := Some("test_hazelcast.server")

net.virtualvoid.sbt.graph.Plugin.graphSettings

initialCommands in console := "import scatang._;import scatang.string._"


