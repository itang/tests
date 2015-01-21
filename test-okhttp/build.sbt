name := "test-okhttp"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.5"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  "com.squareup.okhttp" % "okhttp" % "2.2.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

incOptions := incOptions.value.withNameHashing(true)

mainClass in assembly := Some("example.Example")

