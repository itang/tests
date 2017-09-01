
trait Target<'a> {
    fn get_name(&self) -> String;
    const DEFAULT_NAME: &'a str;
}

#[derive(Debug)]
struct MyTarget {
    name: String,
}

impl Target<'static> for MyTarget {
    const DEFAULT_NAME: &'static str = "itang";
    fn get_name(&self) -> String {
        self.name.clone()
    }
}

fn main() {
    println!("Hello, world!");
    let my_target = MyTarget {
        name: "itang".to_string(),
    };

    println!("{:?}", my_target);
    println!("{}", my_target.get_name());
    println!("{}", MyTarget::DEFAULT_NAME);
}
