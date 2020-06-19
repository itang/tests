use std::env;
use std::fs;
use std::path::Path;
use std::process::{Child, Command, Stdio};

use anyhow::Result;
use toml;
use toml::Value;

fn main() -> Result<()> {
    let path = Path::new(&env::var("HOME")?)
        .join("bin")
        .join("jiayou.toml");

    let content = fs::read_to_string(path)?;
    let r = content.parse::<Value>()?;

    for urls in r["urls"].as_array() {
        for urlV in urls {
            for url in urlV.as_str() {
                browser(url)?;
            }
        }
    }
    /*
    //TODO: 优化编写
    match r["urls"].as_array() {
        Some(urls) => urls
            .iter()
            .for_each(|x| browser(x.as_str().unwrap()).unwrap()),
        None => println!("no urls"),
    }
    */

    Ok(())
}

fn browser(url: &str) -> Result<()> {
    Command::new("x-www-browser").arg(url).stderr(Stdio::null()).stdout(Stdio::null()).spawn()?;
    Ok(())
}
