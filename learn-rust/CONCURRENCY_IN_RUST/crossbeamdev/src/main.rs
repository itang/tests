extern crate crossbeam;

fn main() {
    let mut array = [1, 2, 3];
    crossbeam::scope(|scope| {
        for i in &mut array {
            scope.spawn(move || {
                *i += 1;
                println!("elemnet: {}", i);
            });
        }
    });
}
