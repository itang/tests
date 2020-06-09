use std::env;
use std::error::Error;
use std::fs::File;
use std::io::Read;
use std::path::Path;

// Box<dyn Error> to mean “any kind of error.”
fn main() -> Result<(), Box<dyn Error>> {
    let path = Path::new(&env::var("HOME")?).join(".zshrc");
    //let path = env::var("HOME")? + "/.zshrc";
    let mut content = String::new();
    File::open(path)?.read_to_string(&mut content)?;

    println!("lines: {}", content.lines().count());
    println!("{}", "-".repeat(100));
    for (i, item) in content.lines().enumerate() {
        println!("{:3}: {}", i + 1, item);
    }

    Ok(())
}
