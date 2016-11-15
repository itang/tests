extern crate clap;
extern crate glob;

use glob::glob;
use std::fs;

use clap::{Arg, App, AppSettings};

fn main() {
    let path_arg_name = "path";
    let args = App::new("cha-to-txt")
        .about("Rename .cha to .txt")
        .setting(AppSettings::ArgRequiredElseHelp)
        .arg(Arg::with_name(path_arg_name).help("path to the top directory with .cha files"))
        .get_matches();
    // let path = match args.value_of(path_arg_name) {
    //     Some(path) => path,
    //     None => panic!("You don't supply a path"),
    // };

    let path = args.value_of(path_arg_name).expect("You don't supply a path");

    let search = String::from(path) + "/**/*.cha";
    // let paths = match glob(&search) {
    //     Ok(paths) => paths.map(|p| p.expect("Bad path")),
    //     Err(reason) => panic!("{}", reason),
    // };

    let paths = glob(&search).unwrap().map(|p| p.expect("Bad path"));

    // for path in paths {
    //     match fs::rename(&path, &path.with_extension("txt")) {
    //         Ok(_) => (),
    //         Err(reason) => panic!("{}", reason),
    //     };
    // }

    for path in paths {
        fs::rename(&path, &path.with_extension("txt")).unwrap();
    }
}
