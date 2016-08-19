extern crate futures;
extern crate futures_io;
extern crate futures_mio;
extern crate futures_tls;

use std::net::ToSocketAddrs;
use std::thread;
use std::time::Duration;

use futures::Future;
use futures_mio::Loop;
use futures_tls::ClientContext;

fn main() {
    let mut lp = Loop::new().unwrap();
    let addr = "www.rust-lang.org:443".to_socket_addrs().unwrap().next().unwrap();

    println!("here1");
    let socket = lp.handle().tcp_connect(&addr);
    thread::sleep(Duration::from_secs(1));

    let tls_handshake = socket.and_then(|socket| {
        println!("in1, {}", thread::current().name().unwrap());

        let cx = ClientContext::new().unwrap();
        cx.handshake("www.rust-lang.org", socket)
    });
    thread::sleep(Duration::from_secs(1));
    println!("here2");
    let request = tls_handshake.and_then(|socket| {
        println!("in2, {}", thread::current().name().unwrap());
        futures_io::write_all(socket, "\
            GET / HTTP/1.0\r\n\
            Host: www.rust-lang.org\r\n\
            \r\n\
        ".as_bytes())
    });
    thread::sleep(Duration::from_secs(1));
    println!("here3");
    let response = request.and_then(|(socket, _)| {
        println!("in3, {}", thread::current().name().unwrap());
        futures_io::read_to_end(socket, Vec::new())
    });

    println!("here4");
    let data = lp.run(response).unwrap();
    println!("here5");
    println!("{}", String::from_utf8_lossy(&data));
}
