#[derive(Debug)]
struct User {
    name: String,
}

impl User {
    fn say_hello(&self) {
        println!("Hello, {}", self.name);
    }
}

fn main() {
    let mut user = User { name: "itang".to_owned() };

    {
        let ru3 = &mut user;
        ru3.say_hello();
    }//ru3 out scope, mut ref 结束

    let ru1 = &user;

    ru1.say_hello();

    let ru2 = &user;
    ru2.say_hello();
}

// - 同时可多个Read Reference
// - read ref（immutable ref） 和 write（mutable ref）不能共存
//
