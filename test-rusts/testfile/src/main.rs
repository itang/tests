use std::env;
use std::error::Error;
use std::fs::File;
use std::io::Read;

// Box<dyn Error> to mean “any kind of error.”
fn main() -> Result<(), Box<dyn Error>> {
    let path = env::var("HOME")? + "/.zshrc";
    let mut content = String::new();
    File::open(path)?.read_to_string(&mut content)?;

    println!("{}", content);
    println!("{}", content.lines().count());

    Ok(())
}
