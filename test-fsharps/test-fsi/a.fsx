#r "nuget: Fake.IO.FileSystem"

open Fake.IO

open System.IO

File.replaceContent "a.txt" "hello, world2"

File.ReadAllText("a.txt") |> printfn "%s"

Shell.replaceInFiles [ "world", "itang" ] [
    "a.txt"
]

printfn "after replace."

File.ReadAllText("a.txt") |> printfn "%s"
