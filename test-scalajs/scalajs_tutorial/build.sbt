name := "scalajs-tutorial"

version := "1.0-SNAPSHOT"

organization := "deftype.com"

scalaVersion := "2.11.7"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings", "-YclasspathImpl:flat", "-Xexperimental")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "com.lihaoyi" %% "ammonite-repl" % "0.5.0" % "test"  cross CrossVersion.full
)

resolvers ++= Seq(
  DefaultMavenRepository,
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

resolvers += "itang's repos" at "http://www.haoshuju.net:8078"

incOptions := incOptions.value.withNameHashing(true)

enablePlugins(ScalaJSPlugin)

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.2"
libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.8.1"

// run with nodejs
// dom with phantomjs
scalaJSStage in Global := FastOptStage

// The Scala.js sbt plugin provides a mechanism for libraries to declare the plain JavaScript libraries they depend on and bundle them in a single file
skip in packageJSDependencies := false

// To make the DOM available
jsDependencies += RuntimeDOM
// cnpm install source-map-support

// Automatically Creating a Launcher
persistLauncher in Compile := true

persistLauncher in Test := false

// Test
libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")

mainClass in assembly := Some("tutorial.webapp.TutorialApp")

assemblyMergeStrategy in assembly := {
  //case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  //case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  //case "application.conf"                            => MergeStrategy.concat
  //case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

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
