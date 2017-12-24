extern crate may;
extern crate thread_id;

use may::coroutine;
use may::net::TcpListener;
use std::io::{Read, Write};

fn main() {
    println!("Hello, world!");
    let h = coroutine::spawn(move || {
        let listener = TcpListener::bind("127.0.0.1:8000").unwrap();
        while let Ok((mut stream, _)) = listener.accept() {
            coroutine::spawn(move || {
                println!("main thread has id {}", thread_id::get());
                let mut buf = vec![0; 1024 * 16];
                while let Ok(n) = stream.read(&mut buf) {
                    println!("main thread has id {}", thread_id::get());
                    if n == 0 {
                        break;
                    }
                    stream.write_all(&buf[0..n]).unwrap();
                }
            });
        }
    });
    h.join().unwrap();
}
