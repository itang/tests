extern crate sha2;

use sha2::Digest;
use sha2::Sha256;

fn main() {
    let mut hasher = Sha256::new();

    hasher.input_str("hello world");

    let hex = hasher.result_str();

    assert_eq!(64, hex.len());

    println!("hex: {}, size:{}", hex, hex.len());
}
