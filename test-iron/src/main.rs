extern crate iron;

use std::env;

use iron::prelude::*;
use iron::status;

fn main() {
  Iron::new(|_: &mut Request| {
  Ok(Response::with((status::Ok, "Hello world!")))
  }).http("localhost:8080").unwrap();
}

fn _port() -> u16 {
  let key = "PORT";
  match env::var(key) {
    Ok(val) => val.parse::<u16>().ok().expect("error"),
    Err(_) => 8080
  }
}
