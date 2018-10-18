extern crate uuid;

use std::env;
use uuid::Uuid;

fn main() {
    let number = if let Some(arg1) = env::args().nth(1) {
        arg1.parse().unwrap()
    } else {
        1
    };

    for i in 0..number {
        let my_uuid = Uuid::new_v4();
        let s: &str = &format!("{}", my_uuid);
        println!("{}: {}", i, s.replace("-", ""));
    }
}
