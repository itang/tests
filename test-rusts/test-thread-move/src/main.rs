use std::thread;


fn main() {
    let s = "from the page";
    let d: &str = s.clone(); // 引用默认为copy语义

    let ss = "from".to_string();
    let dd: String = ss.clone();
    thread::spawn(move || {
        println!("child {}", d); // 将 d move过来.
        println!("child {}", dd);
    });

    println!("{}", s);

    thread::sleep(std::time::Duration::from_secs(1));

    test_1().join().unwrap();
    test_2().join().unwrap();
}

fn test_1() -> thread::JoinHandle<String> {
    let s = "hello".to_string();// s默认为move语义
    let it = thread::spawn(|| s + ",world");
    // println!("{}", s);// use moved s
    it
}

fn test_2() -> thread::JoinHandle<()> {
    let s = "hello".to_string();

    // thread::spawn(|| println!("{}", s)) //闭包的println函数借用了s变量， 但是闭包的生存周期可能比当前函数owned的周期长。 closure may outlive the current function, but it borrows `s`, which is owned by the current function
    thread::spawn(move || println!("{}", s))
}
