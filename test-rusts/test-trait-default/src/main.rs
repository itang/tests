#[derive(Default, Debug)]
struct SomeOptions {
    foo: i32,
    bar: f32,
    s: String,
    b: bool,
}

impl SomeOptions {
    fn default() -> SomeOptions {
        Default::default()
    }
}

fn main() {
    let o1: SomeOptions = Default::default();
    println!("o1: {:?}", o1);

    let o2 = SomeOptions::default();
    println!("o2: {:?}", o2);
}
