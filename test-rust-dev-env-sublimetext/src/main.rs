#![feature(thread_sleep)]
#![feature(std_misc)]

extern crate time;

use std::thread::sleep;
use std::time::duration::Duration;

fn main() {
  util::time(||{
    println!("Hello...");
    sleep(Duration::seconds(1));
    println!("world!");
  });
}

mod util {
  use time;

  pub fn time<F: Fn() -> ()>(f: F){
    let a = time::now();
    f();
    let b = time::now();
    println!("{}", b - a)
  }
}
