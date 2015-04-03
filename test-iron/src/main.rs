extern crate iron;

use std::env;

use iron::prelude::*;
use iron::status;

fn main() {
  let _ = _hostname(); // TODO: String => &'a str

  Iron::new(|_: &mut Request| {
  Ok(Response::with((status::Ok, "Hello world!")))
  }).http(("localhost", _port())).unwrap();
}

fn _port() -> u16 {
  let key = "PORT";
  match env::var(key) {
    Ok(val) => val.parse::<u16>().ok().expect("error"),
    Err(_) => 8080
  }
}

fn _hostname() -> String {
  let key = "HOST";
  match env::var(key) {
    Ok(val) => val,
    Err(_) => "localhost".to_string()
  }
}
