fn main() {
    println!("Hello, world!");

    let a  = 1..10;
    println!("{:?}", a);
    
    for i in a {
        println!("{}", i);
    }

    let i =2;
    match i {
        1 ... 10 => println!("ok"),
        _ => println!("Err"),
    }
}
