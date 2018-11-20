use actix_web::{http::Method, server, App, HttpRequest, Responder};

fn ping(_: &HttpRequest) -> impl Responder {
    "Pong"
}

fn index(_: &HttpRequest) -> impl Responder {
    "index"
}

fn main() {
    server::new(|| {
        vec![
            App::new()
                .prefix("/api")
                .resource("/ping", |r| r.method(Method::GET).f(ping)),
            App::new().resource("/", |r| r.f(index)),
        ]
    })
    .bind("127.0.0.1:3000")
    .expect("Can not bind to port 3000")
    .run();
}
