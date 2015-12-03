name := "test-undertow"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings")

libraryDependencies ++= Seq(
  "me.itang" %% "scatang" % "0.1",
  "org.springframework" % "spring-web" % "4.2.3.RELEASE",
  "io.undertow" % "undertow-core" % "1.3.7.Final",
  "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

resolvers += "itang's repos" at "http://itang.github.io/maven-repo"

incOptions := incOptions.value.withNameHashing(true)

mainClass in assembly := Some("test_undertow.Server")
