use std::thread;

use async_std::task;

async fn hello(key: &str) {
    println!("Hello, World");
    println!("{}: {:?}", key, thread::current().id());
}

fn main() {
    println!("{:?}", thread::current().id());
    let t1 = task::spawn(hello("a"));
    task::block_on(hello("b"));
}
