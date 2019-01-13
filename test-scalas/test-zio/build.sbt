name := "test-zio"

version := "1.0-SNAPSHOT"

organization := "deftype.com"

scalaVersion := "2.12.8"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings", "-Ypartial-unification", "-Ydelambdafy:method", "-target:jvm-1.8")

libraryDependencies ++= Seq(
  //"org.scala-lang.modules" %% "scala-java8-compat" % "0.9.0",
  //"org.scalaz" %% "scalaz-core" % "7.2.22",
  //"com.chuusai" %% "shapeless" % "2.3.3",
  //"me.itang" %% "scatang" % "0.1",
  "org.scalaz" %% "scalaz-zio" % "0.5.3",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  //,"com.lihaoyi" %% "ammonite-repl" % "1.1.0" % "test"  cross CrossVersion.full
)

resolvers ++= Seq(
  Resolver.jcenterRepo,
  DefaultMavenRepository,
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

reStartArgs := Seq("-x")
//mainClass in reStart := Some("Main")
//javaOptions in reStart += "-Xmx2g"
envVars in reStart := Map("ENV_USER_TOKEN" -> "2359298356239")

//mainClass in assembly := Some("Main")
//assemblyMergeStrategy in assembly := {
//  //case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
//  //case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
//  //case "application.conf"                            => MergeStrategy.concat
//  //case "unwanted.txt"                                => MergeStrategy.discard
//  case x =>
//    val oldStrategy = (assemblyMergeStrategy in assembly).value
//    oldStrategy(x)
//}

// no need for v0.8
//net.virtualvoid.sbt.graph.Plugin.graphSettings

//EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

//initialCommands in console := "import scatang._;"

//initialCommands in (Test, console) := """ammonite.repl.Repl.run("")"""

//publishTo := {
//  val nexus = "http://120.24.68.174:8081/nexus/content/repositories"
//  if (isSnapshot.value)
//   Some("snapshots" at nexus + "/snapshots")
//  else
//    Some("releases"  at nexus + "/releases")
//}

//credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")


