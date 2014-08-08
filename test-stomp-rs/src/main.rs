extern crate stomp;
use stomp::frame::Frame;
use stomp::subscription::{Ack, AckOrNack, Client};

fn main() {
  let mut session = match stomp::connect("127.0.0.1", 61613){
    Ok(session) => session,
    Err(error) => fail!("Could not connect to the server: {}", error)
  };

  fn on_message(frame: Frame) -> AckOrNack {
    println!("Received a message:\n{}", frame);
    Ack
  }

  let topic ="/topic/messages";
  session.subscribe(topic, Client, on_message);

  session.send_bytes(topic, "text/plain", "Animal".as_bytes());

  session.send_text(topic, "Vegetable");
  session.send_text(topic, "Mineral");

  session.listen();
}