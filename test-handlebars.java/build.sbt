name := "test-handlebars.java"

version := "1.0-SNAPSHOT"

organization := "deftype.com"

scalaVersion := "2.11.6"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7", "-Xlint")

scalacOptions ++= Seq("-deprecation", "-feature", "-Yno-adapted-args", "-Xfatal-warnings", "-YclasspathImpl:flat")

libraryDependencies ++= Seq(
  //"org.scalaz" %% "scalaz-core" % "7.1.0",
  //"com.chuusai" %% "shapeless" % "2.0.0",
  "me.itang" %% "scatang" % "0.1",
  "com.github.jknack" % "handlebars" % "2.1.0",
  "org.scalatest" %% "scalatest" % "2.2.2" % "test",
  "com.lihaoyi" %% "ammonite-repl" % "0.3.0" % "test"  cross CrossVersion.full
)

resolvers ++= Seq(
  DefaultMavenRepository,
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

resolvers += "itang's repos" at "http://120.24.68.174:8081/nexus/content/groups/public"

incOptions := incOptions.value.withNameHashing(true)

mainClass in assembly := Some("Main")

net.virtualvoid.sbt.graph.Plugin.graphSettings

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

initialCommands in console := "import scatang._;import scatang.string._;ammonite.repl.Repl.main(null)"

publishTo := {
  val nexus = "http://120.24.68.174:8081/nexus/content/repositories"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "/snapshots")
  else
    Some("releases"  at nexus + "/releases")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")


