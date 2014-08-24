
extern crate uuid;

use uuid::Uuid;

fn main() {
  for i in range(1u, 10000) {
    let uuid1 = Uuid::new_v4();
    let ret = uuid1.to_string();
    println!("{}, len: {}", ret, ret.len());
  }
}
