name := "test-vertx-core-scala"

version := "1.0-SNAPSHOT"

organization := "deftype.com"

scalaVersion := "2.12.0"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings", "-Ypartial-unification", "-Ydelambdafy:method", "-target:jvm-1.8")

libraryDependencies ++= Seq(
  //"org.scala-lang.modules" %% "scala-java8-compat" % "0.7.0",
  //"org.scalaz" %% "scalaz-core" % "7.2.1",
  //"com.chuusai" %% "shapeless" % "2.3.0",
  //"me.itang" %% "scatang" % "0.1",
  "io.vertx" % "vertx-core" % "3.3.3",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  //,"com.lihaoyi" %% "ammonite-repl" % "0.6.2" % "test"  cross CrossVersion.full
)

resolvers ++= Seq(
  Resolver.jcenterRepo,
  DefaultMavenRepository,
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

//resolvers += "itang's repos" at "http://www.haoshuju.net:8078"

incOptions := incOptions.value.withNameHashing(true)

mainClass in assembly := Some("Main")

assemblyMergeStrategy in assembly := {
  //case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  //case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  //case "application.conf"                            => MergeStrategy.concat
  //case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

// no need for v0.8
//net.virtualvoid.sbt.graph.Plugin.graphSettings

//EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

//initialCommands in console := "import scatang._;"

//initialCommands in (Test, console) := """ammonite.repl.Repl.run("")"""

publishTo := {
  val nexus = "http://120.24.68.174:8081/nexus/content/repositories"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "/snapshots")
  else
    Some("releases" at nexus + "/releases")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")


