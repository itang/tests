//see: https://rust-lang-nursery.github.io/edition-guide/rust-2018/module-system/path-clarity.html

// Rust 2018 (uniform paths variant)

//a path starting from an external crate name
use futures::Future;

use self::foo::Bar;

mod foo {
    pub struct Bar;
}

// use a relative path from the current module

//a path starting from an external crate name
trait A {
    fn my_poll();
}

fn f1() {}

enum SomeEnum {
    V1(usize),
    V2(String),
}

pub fn func() {
    //a path starting from an external crate name
    let five = std::sync::Arc::new(5);
    let five = ::std::sync::Arc::new(10); // use ::name for an external crate name
    use self::SomeEnum::*; //self::name for a local module or item
    self::f1(); //or a path starting with [crate, super, or ]self
    crate::hello::hello(); // or a path starting with crate [super, or self]
}

mod sub_paths {
    fn foo() {
        super::f1(); // or a path starting with [crate ], [super], or self
        //my_poll(); //error
        //self::my_poll(); //error
    }
}
