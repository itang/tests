struct User;

impl User {
    fn index(&self) {
        println!("user index");
    }

    fn move_it(self) {
        println!("move!");
    }
}

fn invoke<F: Fn()>(action: &F) {
    action()
}

fn invoke2(action: fn()) {
    action()
}

fn test() {
    println!("test");
}

fn main() {
    println!("Hello, world!");
    invoke(&test);
    invoke2(test);

    let user = User;
    user.move_it();// move user

    // user; // user of moved value
}
