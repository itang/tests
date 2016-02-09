extern crate hyper;

use hyper::*;


fn main() {
    let client = Client::new();
    let res = client.post("http://localhost:3000/set")
                    .body(r#"{ "msg": "Just trust the Rust" }"#)
                    .send()
                    .unwrap();
    assert_eq!(res.status, hyper::Ok);
}
