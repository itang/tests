extern crate iron;
extern crate rustc_serialize;
extern crate router;


use iron::prelude::*;
use iron::status;
use rustc_serialize::json;
use router::Router;
use std::io::Read;
use std::sync::{Arc, Mutex};


#[derive(RustcEncodable, RustcDecodable)]
struct Greeting {
    msg: String,
}


fn main() {
    let greeting = Arc::new(Mutex::new(Greeting { msg: "Hello, World".to_string() }));
    let greeting_clone = greeting.clone();

    fn hello_world(_: &mut Request, greeting: &Greeting) -> IronResult<Response> {
        let playload = json::encode(greeting).unwrap();

        Ok(Response::with((status::Ok, playload)))
    }

    fn set_greeting(req: &mut Request, greeting: &mut Greeting) -> IronResult<Response> {
        let mut playload = String::new();
        req.body.read_to_string(&mut playload).unwrap();
        *greeting = json::decode(&playload).unwrap();

        Ok(Response::with(status::Ok))
    }

    let mut router = Router::new();
    router.get("/",
               move |r: &mut Request| hello_world(r, &greeting.lock().unwrap()));
    router.post("/set",
                move |r: &mut Request| set_greeting(r, &mut greeting_clone.lock().unwrap()));

    println!("Serve On 3000...");
    Iron::new(router).http("localhost:3000").unwrap();
}
