#![feature(test)]

extern crate uuid;

extern crate test;

use uuid::Uuid;
use test::Bencher;

// const uuid: Uuid = Uuid::new_v4();

fn main() {
    let my_uuid = Uuid::new_v4();
    println!("{}", my_uuid);
}

#[bench]
fn bench_uuid(b: &mut Bencher) {
    let my_uuid = Uuid::new_v4();

    b.iter(|| {
        let _ = my_uuid.to_hyphenated_string();
    });
}