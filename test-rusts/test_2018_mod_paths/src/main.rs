#[allow(unused_imports)]
#[allow(unused_variables)]
mod hello;
mod paths;

fn main() {
    // println!("Hello, world!");
    crate::hello::hello();
    crate::hello::sub_hello::hello2();
    hello::sub_hello::hello2();
}

trait Foo {
    fn foo(&self, _: Box<dyn Foo>);
}
