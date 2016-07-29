extern crate iron;

use std::env;

use iron::prelude::*;
use iron::status;

fn ok(content: &str) -> iron::IronResult<Response> {
    Ok(Response::with((status::Ok, content)))
}

fn hello_handler(_: &mut Request) -> iron::IronResult<Response> {
    ok("Hello, World!")
}

fn main() {
    let _h = _hostname(); // TODO: String => &'a str
    let port = _port();
    println!("INFO: hostname:{}, port: {}", _h, port);

    Iron::new(hello_handler)
        .http(("localhost", port))
        .unwrap();
}

fn _port() -> u16 {
    let key = "PORT";
    match env::var(key) {
        Ok(val) => val.parse::<u16>().ok().expect("error"),
        Err(_) => 8080,
    }
}

fn _hostname() -> String {
    let key = "HOST";
    match env::var(key) {
        Ok(val) => val,
        Err(_) => "localhost".to_string(),
    }
}
