#[derive(Debug)]
struct Person {
    name: String,
}

impl Person{
    fn new1<S: Into<String>>(into: S) -> Person {
        Person { name: into.into() }
    }

    fn new2<S>(into: S) -> Person
        where S: Into<String>
    {
        Person { name: into.into() }
    }
}

fn main() {
    let p1 = Person::new1("itang");
    let p11 = Person::new1("itang".to_string());

    println!("{:?}, {:?}", p1, p11);

    let p2 = Person::new2("tqibm");
    let p22 = Person::new2("tqibm");
    println!("{:?}, {:?}", p2, p22);
}
