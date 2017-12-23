open Nact;

open Nact.Operators;

let system = start();

type greetingMsg = {name: string};

let greeter =
  spawnStateless(~name="greeter", system, ({name}, _) =>
    print_endline("Hello " ++ name) |> Js.Promise.resolve
  );

dispatch(greeter, {name: "Erlich Bachman"});

greeter <-< {name: "itang"};