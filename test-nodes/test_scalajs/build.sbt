enablePlugins(ScalaJSPlugin)

name := "Scala.js Tutorial"
scalaVersion := "2.12.4" // or any other Scala version >= 2.10.2

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

scalaJSModuleKind := ModuleKind.CommonJSModule

libraryDependencies ++= Seq(
  "io.scalajs" %%% "nodejs" % "0.4.2"
)
