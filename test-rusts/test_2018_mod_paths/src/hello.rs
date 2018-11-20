use self::f::Future;
use futures as f;
use uuid::Uuid;

pub fn hello() {
    println!("Hello, World! from hello. {}", Uuid::new_v4());
}

pub mod sub_hello {
    use futures::Future;

    pub fn hello2() {
        println!("Hello, World! from hello2");
    }
}

