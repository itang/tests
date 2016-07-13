extern crate mqttc;
extern crate netopt;

use mqttc::*;
use netopt::*;
use std::time::Duration;


fn main() {
    let netopt = NetworkOptions::new();
    let mut opts = ClientOptions::new();


    opts.set_reconnect(ReconnectMethod::ReconnectAfter(Duration::from_secs(1)));
    let mut client = opts.connect("127.0.0.1:1883", netopt).expect("Can't connect to server");

    client.subscribe("mqtt/test").unwrap();
    loop {
        match client.await().unwrap() {
            Some(message) => {
                println!("{:?}", message);

                let payload: &Vec<u8> = &*(message.payload.clone());
                let payload: String = String::from_utf8(payload.clone()).expect("转码出错了");
                println!("topic:{}, payload:{}", message.topic.path, payload)
            }
            None => {}
        }
    }
}
