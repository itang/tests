use std::sync::Arc;

fn main() {
    let s = "hello".to_string();
    let s = Arc::new(&s);

    println!("{}", s.len());
    println!("{}", (*s).len());
    println!("{}", (**s).len());
}
