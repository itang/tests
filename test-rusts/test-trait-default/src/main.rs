#[derive(Default, Debug)]
struct SomeOptions {
    foo: i32,
    bar: f32,
    s: String,
    b: bool,
}

impl SomeOptions {
    fn default_custom() -> SomeOptions {
        SomeOptions { foo: 100, ..Default::default() }
    }
}

fn main() {
    let o1: SomeOptions = Default::default();
    println!("o1: {:?}", o1);

    let o2 = SomeOptions::default();
    println!("o2: {:?}", o2);

    let o3 = SomeOptions::default_custom();
    println!("o3: {:?}", o3);
}
