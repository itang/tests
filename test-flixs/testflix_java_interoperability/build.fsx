#r "paket:
nuget Fake.DotNet.Cli
nuget Fake.IO.FileSystem
nuget Fake.Core.Target //"
#load ".fake/build.fsx/intellisense.fsx"

open Fake.Core
open Fake.IO
open Fake.IO.Globbing.Operators
open Fake.Core.TargetOperators

Target.initEnvironment ()

Target.create "Clean" (fun _ -> !! "build" ++ "target" |> Shell.cleanDirs)

Target.create "All" ignore

Target.create "Run" (fun _ ->
    Shell.Exec("D:\\dev-env\\flix\\flix.bat", "run", ".")
    |> ignore)

"Clean" ==> "Run" ==> "All"


Target.runOrDefault "All"
