pub use self::foo::foo_function;

pub mod bar;  
pub mod foo;

pub fn say_hello() {  
    println!("Hello from greetings3");
}