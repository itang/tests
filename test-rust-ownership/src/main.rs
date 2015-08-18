#[derive(Debug)]
struct A {
    name: String,
}

impl Drop for A {
    fn drop(&mut self) {
        println!("Drop: {}", self.name);
    }
}

fn main() {
    //1. Each object can be used exactly once. When you use an object it is moved to the new location and is no longer usable in the old
    let a = A{ name: "itang".to_string() };
    let b = a;

    println!("{:?}", b);
    //println!("{:?}", a); // error: use of moved value: `a`
    //let c = a; // error: use of moved value: `a`

    // 2. When an object passes out of scope, it is destroyed and is no longer usable.
    {
        let _a1 = A{ name: "tqibm".to_string() };
    }
    //let b1 = a1; // error: unresolved name `a`

    // 3. Blocks can produce a value which goes up one level of scope.
    let x = if true { 100 } else { 200 };
    println!("{}", x);
    // All objects have a lifetime which constrains which scopes they may be moved out of.
}
