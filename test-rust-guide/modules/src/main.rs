fn main() {
    println!("Hello, world!")
    hello::print_hello();
}

// a module named hello inside of our crate root
mod hello {
  pub fn print_hello() {
    println!("Hello, world! (from hello module)")
  }
}