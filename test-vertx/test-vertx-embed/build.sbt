name := "test-vertx-embed"

version := "1.0-SNAPSHOT"

organization := "deftype.com"

scalaVersion := "2.12.0"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings", "-Xexperimental")

libraryDependencies ++= Seq(
  //"org.scalaz" %% "scalaz-core" % "7.1.0",
  //"com.chuusai" %% "shapeless" % "2.0.0",
  // "org.apache.storm" % "storm-core" % "0.10.0",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.6.1" % "compile",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.1",
  "io.vertx" % "vertx-codegen" % "3.1.0",
  "io.vertx" % "vertx-core" % "3.1.0"
)

resolvers ++= Seq(
  DefaultMavenRepository,
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

incOptions := incOptions.value.withNameHashing(true)


publishTo := {
  val nexus = "http://120.24.68.174:8081/nexus/content/repositories"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "/snapshots")
  else
    Some("releases" at nexus + "/releases")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
