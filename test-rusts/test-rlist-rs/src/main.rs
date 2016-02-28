use std::fmt::Display;

#[derive(PartialEq)]
enum List<T> {
    Nil,
    Node(T, Box<List<T>>),
}

fn each<T: Display>(head: &List<T>) {
    let mut curr = head;
    loop {
        match curr {
            &List::Node(ref v, ref next) => {
                println!("{}", v);
                curr = next;
            }
            _ => break,
        }
    }
}

fn main() {
    let head: List<i32> = List::Node(200, Box::new(List::Node(100, Box::new(List::Nil))));

    each(&head);

    each(&head);

    println!("Hello, world!");
}
