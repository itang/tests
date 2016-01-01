fn foo() -> () {
    println!("helo");
}

fn main() {
    foo();

    let a = vec![1, 2, 3];
    let b: Vec<String> = a.into_iter()
                          .map(|x| format!("x: {}", x))
                          .collect();

    // println!("{:?}", a); //  use of moved value: `a`
    println!("{:?}", b);

    assert_eq!("a", "a");
}
