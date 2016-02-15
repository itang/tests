#[derive(Debug)]
struct Foo<T>(T);

#[derive(Debug)]
struct Bar<T: ?Sized>(T);

// struct FooUse(Foo<[i32]>); // the trait `core::marker::Sized` is not implemented for the type `[i32]`
#[derive(Debug)]
struct FooUse2(Foo<Vec<i32>>);

#[derive(Debug)]
struct BarUse(Bar<[i32]>);

fn main() {
    let foo = Foo(1);
    println!("foo: {:?}", foo);

    let bar = Bar(10);
    println!("bar {:?}", bar);

    let foo_use2 = FooUse2(Foo(vec![1, 2, 3]));
    println!("foo_use2:{:?}", foo_use2);

    // let bar_use = BarUse(Bar(*(&[1]))); // TODO: How to init [i32] type?
    // println!("bar_use: {:?}", bar_use);

    println!("Hello, world!");
}
