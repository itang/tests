type req
type res
type server

external micro : (req -> res -> string) -> server = "micro" [@@bs.module]
external listen : server -> int -> unit = "listen" [@@bs.send]

let server = micro (fun req -> fun res -> "Hello, World" ^ " " ^ string_of_int(Add.add 200 300));;

listen server 1337;;
