extern crate actix_web;
use actix_web::{http, server, App, HttpRequest, Path, Responder};

fn index(req: &HttpRequest) -> &'static str {
    "Hello, World"
}

fn main() {
    server::new(|| App::new().resource("/hello", |r| r.f(index)))
        .bind("127.0.0.1:8080")
        .unwrap()
        .run();
}
