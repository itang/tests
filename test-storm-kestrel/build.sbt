name := "storm-kestrel"

version := "1.0-SNAPSHOT"

organization := "deftype.com"

scalaVersion := "2.11.7"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings", "-YclasspathImpl:flat", "-Xexperimental")

libraryDependencies ++= Seq(
  //"org.scalaz" %% "scalaz-core" % "7.1.5",
  //"com.chuusai" %% "shapeless" % "2.2.5",
  "me.itang" %% "scatang" % "0.1",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "storm" % "storm-kestrel" % "0.9.0-wip5-multischeme",
  //"org.apache.thrift" % "libthrift" % "0.9.3",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "storm" % "libthrift7" % "0.7.0-2",
  "com.lihaoyi" %% "ammonite-repl" % "0.4.8" % "test"  cross CrossVersion.full
)

resolvers ++= Seq(
  DefaultMavenRepository,
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

resolvers += "itang's repos" at "http://www.haoshuju.net:8078"

resolvers += "clojars.org" at "http://clojars.org/repo"

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

net.virtualvoid.sbt.graph.Plugin.graphSettings

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

initialCommands in console := "import scatang._;"

initialCommands in (Test, console) := """ammonite.repl.Repl.run("")"""

publishTo := {
  val nexus = "http://120.24.68.174:8081/nexus/content/repositories"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "/snapshots")
  else
    Some("releases"  at nexus + "/releases")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
