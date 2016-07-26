extern crate iron;

use iron::prelude::*;
use std::process::Command;
use std::time;
use std::net::TcpListener;
use std::thread;

const PORT: u16 = 3000u16;

fn hello_world(_: &mut Request) -> IronResult<Response> {
    Ok(Response::with((iron::status::Ok, "Hello world!")))
}

fn tcp_port_available(port: u16) -> bool {
    match TcpListener::bind(("0.0.0.0", port)) {
        Err(_) => false,
        Ok(l) => {
            drop(l);
            true
        }
    }
}

fn try_open_browser() {
    thread::spawn(|| {
        loop {
            thread::sleep(time::Duration::from_millis(200));
            if !tcp_port_available(PORT) {
                println!(">>tcp_port_available false");
                Command::new("xdg-open")
                    .arg(format!("http://localhost:{}", PORT))
                    .output()
                    .unwrap_or_else(|e| { panic!("failed to execute process: {}", e) });
                break;
            }
            println!("try again...");
        }
    });
}

fn main() {
    let chain = Chain::new(hello_world);

    try_open_browser();

    println!("listening on :3000...");
    Iron::new(chain).http(("0.0.0.0", PORT)).unwrap();
}
