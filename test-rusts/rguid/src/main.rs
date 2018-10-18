extern crate uuid;

use std::env;
use uuid::Uuid;

fn main() {
    let number = env::args().nth(1).and_then(|x| x.parse().ok()).unwrap_or(1);

    for i in 0..number {
        println!("{}: {}", i, Uuid::new_v4().to_string().replace("-", ""));
    }
}
