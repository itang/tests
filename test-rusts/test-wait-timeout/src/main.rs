extern crate wait_timeout;

use std::process::Command;
use wait_timeout::ChildExt;

fn main() {
    let mut child = Command::new("ruby").arg("mock_wait.rb").spawn().unwrap();

    let status_code = match child.wait_timeout_ms(3_000).unwrap() {
        Some(status) => status.code(),
        None => {
            // child hasn't exited yet
            child.kill().unwrap();
            child.wait().unwrap().code()
        }
    };

    println!("status_code: {:?}", status_code);
}
