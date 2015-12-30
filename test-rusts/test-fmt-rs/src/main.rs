use std::fmt;

#[derive(Debug)]
struct User {
    name: String,
    age: u8,
}

impl User {
    fn new() -> User {
        User {
            name: "Itang".to_string(),
            age: 18,
        }
    }
}

impl fmt::Display for User {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "User(name={}, age={})", self.name, self.age)
    }
}

fn main() {
    let user = User::new();
    println!("{:?}", user);
    println!("{}", user);
}
