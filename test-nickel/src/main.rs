#[macro_use]
extern crate nickel;

use nickel::mimes::MediaType;
use nickel::Nickel;
use nickel::router::http_router::HttpRouter;

mod hello {
    pub fn hello(name: String) {
        println!("Hello, {}", name);
    }
}

fn main() {
    hello::hello("itang".to_string());

    let mut server = Nickel::new();
    server.get("/hello", middleware!("Hello, world!"));
    server.get("/test", middleware! { |_, mut res|
        res.set(MediaType::Json);
        r#"{"foo":"bar"}"#
    });
    server.listen("127.0.0.1:6767");
}
