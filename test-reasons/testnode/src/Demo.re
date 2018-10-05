Js.log("Hello, BuckleScript and Reason1!");

type http;

[@bs.deriving abstract]
type req = {url: string};

type res;

[@bs.send] external _end: (res, string) => unit = "end";

type handler = (req, res) => unit;

type server;

[@bs.send] external listen: (server, int) => unit = "";
[@bs.send] external createServer: (http, handler) => server = "";
[@bs.module] external http: http = "";

let server =
  http->createServer((req, res) =>
    switch (req->urlGet) {
    | "/" => res->_end("Frontpage")
    | "/about" => res->_end("About")
    | _ => res->_end("Hello")
    }
  );

server->listen(9000);