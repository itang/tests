#[rustler::nif]
fn add(a: i64, b: i64) -> i64 {
    a + b
}

#[rustler::nif]
fn panic() {
    panic!("Boom!")
}

#[rustler::nif]
fn crash() {
    let p: *const i32 = std::ptr::null();

    unsafe {
        println!("{:?}", *p);
    }
}
#[rustler::nif]
fn generate(num: i64, upper: i64) -> Vec<i64> {
    extern crate rand;
    use rand::Rng;

    let mut rng = rand::thread_rng();

    // rust ranges are "half open"
    // so (0..5) has a length of 5
    // https://doc.rust-lang.org/std/ops/struct.Range.html
    (0..num)
        .map(|_| {
            // gen_range includes the lower bound
            // but excludes the upper bound
            // so we add 1 to upper to match the elixir implementation
            // https://docs.rs/rand/0.7.3/rand/trait.Rng.html
            rng.gen_range(1, upper + 1)
        })
        .collect()
}

rustler::init!("Elixir.MyNif", [add, panic, crash, generate]);
