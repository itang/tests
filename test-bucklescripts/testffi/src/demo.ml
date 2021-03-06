

let () = Js.log "Hello, BuckleScript"

(* external value-name : typeexpr = external-declaration attributes *)
external imul : int -> int -> int = "Math.imul" [@@bs.val]
type dom
(* Abstract type for the DOM *)
external dom : dom = "document" [@@bs.val]

(* bs.val attribute is used to bind to a JavaScript value, it can be a function or plain value *)

(* Scoped values: bs.scope *)
type param
external executeCommands : string -> param array -> unit = "" [@@bs.module "vscode"] [@@bs.scope "commands"] [@@bs.splice]

(*let f a b c =
  executeCommands "hi" [|a;b;c|];; *)
type buffer
external makeBuffer : int -> buffer = "Buffer" [@@bs.new] [@@bs.scope "global"]
external hi : string = "" [@@bs.module "z"] [@@bs.scope "a0", "a1", "a2"]
external ho : string = "" [@@bs.val] [@@bs.scope "a0", "a1" , "a2"]

let f2 () =
  makeBuffer 20, hi, ho, imul 1 2;;

(* Binding to JavaScript constructor: bs.new *)
type t
external create_date : unit -> t = "Date" [@@bs.new]
let date = create_date ()

(* Binding to a vlaue from a module: bs.module *)
external add : int -> int -> int = "add" [@@bs.module "x"]
external add2 : int -> int -> int = "" [@@bs.module "y", "U"]
let f = add 3 4
let g = add2 3 4

type http
external http : http = "http" [@@bs.module]


(* Binding to method: bs.send, bs.send.pipe *)

(* bs.send

The object is always the first argument and actual arguments follow.

*)
type id 
external get_by_id: dom -> string -> id = "getElementById" [@@bs.send];;

get_by_id dom "xx";

(*
bs.send.pipe is similar to bs.send except that the first argument, i.e. the object is put
in the position of last argument to help user write in a chaining style
*)

(* For the [@bs] attribute in the callback *)
external map : ('a -> 'a [@bs]) -> 'b array = "" [@@bs.send.pipe: 'a array]
external forEach: ('a -> unit [@bs]) -> 'a array = "" [@@bs.send.pipe: 'a array]

let test arr = 
  arr
  |> map (fun [@bs] x -> x + 1)
  |> forEach (fun [@bs] x -> Js.log x)

(* 
Binding to dynamic key access/set: bs.set_index, bs.get_index
*)

type arr
external create : int -> arr = "Int32Array" [@@bs.new]
external get: arr -> int -> int = "" [@@bs.get_index]
external set: arr -> int -> int -> unit  = "" [@@bs.set_index];;

let a = create 100 in
set a 0 100;;

(* Binding to Getter/Setter: bs.get, bs.set *)
type textarea
external set_name : textarea -> string -> unit = "name" [@@bs.set]
external get_name : textarea -> string = "name" [@@bs.get]
external create_ta : unit -> textarea = "TextArea" [@@bs.new];;

let a = create_ta () in 
 set_name a "yourname";
 a |> get_name |> print_endline;;

 (*
 Splice call convention : bs.splice
 *)

 external join : string array -> string = "" [@@bs.module "path"] [@@bs.splice]
 let v = join [|"a"; "b"|]

 (* 
 Special types on external declarations : bs.string, bs.int, bs.ignore

 Using polymorphic variant to model enums and string types
 *)
 external readFileSync :
   name:string ->
   (
     [ `utf8
     |`my_name [@bs.as "ascii"]
     ] [@bs.string]
   ) ->
   string = "" [@@bs.module "fs"]

let _ = 
    readFileSync ~name:"xx.txt" `my_name;;

external test_int_type :
 (  [ `on_closed
    | `on_open [@bs.as 3]
    | `in_bin ] 
    [@bs.int]) -> int = "" [@@bs.val];;

test_int_type `on_open;;

(* 
Using polymorphic variant to model event listener
*)

type readline 
external on :
(
  [ `close of unit -> unit 
  | `line of string -> unit
  ] [@bs.string]
)
  -> readline = "" [@@bs.send.pipe: readline]

let register rl = 
  rl
  |> on (`close (fun _event -> ()))
  |> on (`line (fun line -> print_endline line));;

(*
 Phantom Arguments and ad-hoc polymorphism
*)

(*
bs.ignore allows arguments to be erased after passing to JS functional call, the side effect will still be recorded.
*)
external add : (int [@bs.ignore]) -> int -> int -> int = "" [@@bs.val]

let v = add 0 1 2

(* this is very useful to combine GADT *)

type _ kind = 
  | Float : float kind 
  | String : string kind
external add2 : ('a kind [@bs.ignore]) -> 'a -> 'a -> 'a = "add" [@@bs.val];;

let () =
  Js.log (add2 Float 3.0 2.0);
  Js.log (add2 String "x" "y");


(* Fixed Arguments *)
(* Contrary to the Phantom arguments, _[@bs.as] is introduced to attach the const data. *)
external process_on_exit : (_ [@bs.as "exit"]) -> (int -> unit) -> unit = "on" [@@bs.scope "process"] [@@bs.val];;

process_on_exit (fun exit_code -> 
  Js.log("error code: " ^ string_of_int exit_code)
);;

type process
external on_exit : (_ [@bs.as "exit"]) -> (int -> unit) -> unit = "on" [@@bs.send.pipe: process];;

let register (p : process) =
  p |> on_exit (fun i -> Js.log i);;

external io_config :
    stdio:(_ [@bs.as "inherit"]) ->
    cwd:string ->
    unit ->
    _ = "" [@@bs.obj];;

let config = io_config ~cwd:"." () 

(* Fixed Arguments with arbitrary JSON literal (@since 1.7.0) *)
external on_exit_slice5 :
  int 
  -> (_ [@bs.as 3])
  -> (_ [@bs.as {json|true|json}])
  -> (_ [@bs.as {json|false|json}])
  -> (_ [@bs.as {json|"你好"|json}])
  -> (_ [@bs.as {json|["你好", 1,2,3]|json}])
   -> (_ [@bs.as {json| [{ "arr" : ["你好",1,2,3], "encoding" : "utf8"}] |json}])
    -> (_ [@bs.as {json| [{ "arr" : ["你好",1,2,3], "encoding" : "utf8"}] |json}])
-> (_ [@bs.as "xxx"])
    -> ([`a|`b|`c] [@bs.int])
    -> (_ [@bs.as "yyy"])
    -> ([`a|`b|`c] [@bs.string])
    -> int array
    -> unit
    =
    "xx" [@@bs.send.pipe: t] [@@bs.splice]

 let _ = (create_date ()) |> on_exit_slice5 __LINE__ `a `b [|1;2;3;4;5|]


 (* 
 Binding to NodeJS special variable: bs.node
 *)
 let dirname : string option = [%bs.node __dirname]
 let filename : string option = [%bs.node __filename]
 let _module : Node.node_module option = [%bs.node _module]
 let require : Node.node_require option = [%bs.node require]

 (* 
 Binding to callback (High-order function)

 High order functions are functions where the callback can be another function. For
 exaple, suppose JS has a map function as below
 *)
 (* Here ('a → 'b → 'c [@bs]) will always be of arity 2 *)
 external map : 'a array -> 'b array -> ('a -> 'b -> 'c [@bs]) -> 'c array = "" [@@bs.val]

 (*
 Note the [@bs] annotation already solved the problem completely, but it has a drawback that it requires users to write [@bs] both in definition site and call site. 
 *)
 external map2 : 'a array -> ('a -> 'b [@bs]) -> 'b array = "" [@@bs.send];;

 map2 [|1;2;3|] (fun [@bs] x -> x + 1);;

 (*
 introduce another implicit annotation [@bs.uncurry]  so that the compiler will automatically wrap
 the curried callback (from OCaml side) to JS uncurried callback. In this way, the [@bs.uncurry] annotation is defined only once.
 *)

 external map3 : 'a array -> ('a -> 'b [@bs.uncurry]) -> 'b array = "" [@@bs.send];;
 map3 [|1;2;3|] (fun x -> x + 1);;

 let app f x = f x [@bs];;
 let app2 f x = f x;;

type x
external set_onload : x -> (x -> int -> unit [@bs.this]) -> unit = "onload" [@@bs.set];;
external resp : x -> int = "response" [@@bs.get];;
external createx : unit -> x = "Date" [@@bs.new];;

set_onload (createx ()) begin fun [@bs.this] o v ->
   Js.log(resp o + v)
end;;

(* Binding to JS objects *)
external demo : < height : int; width : int > Js.t = "" [@@bs.module];;

(* 
Complex object type
*)
class type _rect = object 
  method height : int 
  method width : int 
  method draw : unit -> unit
end [@bs];;

type rect = _rect Js.t;;

type rect2 = < height : int; width : int ; draw : unit -> unit [@bs.meth]  > Js.t;;

(*
 How to consume JS property and methods
  as we said: ## is used in both object method dispath and field access.
*)
(*
f##property;;
f##property #= v
f##js_method args0 args1 args2
*)

(* Create JS objects using bs.obj *)
let u = [%bs.obj { x = { y =  { z = 3}}}];;