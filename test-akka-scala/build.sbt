import AssemblyKeys._

name := "test-akka-scala"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  //"org.brianmckenna" % "wartremover_2.10" % "0.3",
  "org.scalaz" %% "scalaz-core" % "7.1.0",
  "com.chuusai" %% "shapeless" % "2.0.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.5",
  "me.itang" %% "scatang" % "0.1",
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

resolvers += "itang's repos" at "http://itang.github.io/maven-repo"

incOptions := incOptions.value.withNameHashing(true)

assemblySettings

mainClass in assembly := Some("example.Example")

initialCommands in console := "import scatang._;import scatang.string._"


