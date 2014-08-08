import AssemblyKeys._

name := "test-undertow"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  "me.itang" %% "scatang" % "0.1.0-SNAPSHOT",
  "org.springframework" % "spring-web" % "4.0.6.RELEASE", 
  //"org.springframework.android" % "spring-android-rest-template" % "1.0.1.RELEASE",
  "io.undertow" % "undertow-core" % "1.1.0.Beta6",
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

