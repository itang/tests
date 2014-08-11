import AssemblyKeys._

name := "test-okhttp"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  //"org.brianmckenna" % "wartremover_2.10" % "0.3",
  "org.scalaz" %% "scalaz-core" % "7.0.6",
  "com.chuusai" %% "shapeless" % "2.0.0",
  "com.squareup.okhttp" % "okhttp" % "2.0.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

incOptions := incOptions.value.withNameHashing(true)

assemblySettings

mainClass in assembly := Some("example.Example")

