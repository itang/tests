use std::env;
//use std::error::Error;
use std::fs::File;
use std::io::Read;
use std::path::Path;

use anyhow::{Context, Result};
// Box<dyn Error> to mean “any kind of error.”
//fn main() -> Result<(), Box<dyn Error>> {
fn main() -> Result<()> {
    let path = {
        let home = env::var("HOME")?;
        Path::new(&home).join(".zshrc")
    };
    //let path = env::var("HOME")? + "/.zshrc";
    let mut content = String::new();
    File::open(&path)
        .with_context(|| format!("can't open file: {:?}", path))?
        .read_to_string(&mut content)?;

    println!("lines: {}", content.lines().count());
    println!("{}", "-".repeat(100));
    for (i, item) in content.lines().enumerate() {
        println!("{:3}: {}", i + 1, item);
    }

    Ok(())
}
