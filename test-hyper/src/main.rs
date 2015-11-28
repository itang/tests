extern crate hyper;

use hyper::Server;
use hyper::server::{Request, Response};

fn hello(_: Request, res: Response) {
    res.send(b"Hello World!").unwrap();
}

fn main() {
    let _ = Server::http("127.0.0.1:8080").unwrap().handle(hello);
}
