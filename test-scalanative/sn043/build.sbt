scalaVersion := "3.1.0"

// Set to false or remove if you want to show stubs as linking errors
nativeLinkStubs := true

enablePlugins(ScalaNativePlugin)

//libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "3.0.0"

resolvers += Resolver.sonatypeRepo("snapshots")
