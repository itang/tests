extern crate frank_jwt;

use frank_jwt::Header;
use frank_jwt::Payload;
use frank_jwt::encode;
use frank_jwt::decode;
use frank_jwt::Algorithm;


fn main() {
    let mut payload = Payload::new();
    payload.insert("key1".to_string(), "val1".to_string());
    payload.insert("key2".to_string(), "val2".to_string());
    payload.insert("key3".to_string(), "val3".to_string());

    let secret = "secret123";
    let header = Header::new(Algorithm::HS256);

    let jwt = encode(header, secret.to_string(), payload.clone());
    println!("token: {}", jwt);

    match decode(jwt, secret.to_string(), Algorithm::HS256) {
        Ok((_, p)) => println!("payload: {:?}", p),
        Err(_) => println!("error"),
    }
}
