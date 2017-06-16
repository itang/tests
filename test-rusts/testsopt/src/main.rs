extern crate structopt;

#[macro_use]
extern crate structopt_derive;

use structopt::StructOpt;

#[derive(StructOpt, Debug)]
#[structopt(name = "example", about = "An example of structopt usage.")]
struct Opt {
    #[structopt(short = "d", long = "debug", help = "Activate debug mode")]
    debug: bool,


    /// An argument of type float, with a default value.
    #[structopt(short = "s", long = "speed", help = "Set speed", default_value = "42")]
    speed: f64,

/// Needed parameter, the first on the command line.
    #[structopt(help = "Input file")]
    input: String, 

    /// An optional parameter, will be `None` if not present on the
    /// command line.
    #[structopt(help = "Output file")]
    output: Option<String>,

    from: Option<String>,
}
fn main() {
    println!("Hello, world!");

    let opt = Opt::from_args();
    println!("{:?}", opt);
}
