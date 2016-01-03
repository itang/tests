use std::thread;

use std::cell::Cell;

fn main() {
    println!("Hello, world! 2016");

    test_variable_binding();
    test_functions();
    primitive_types();
    test_comments();
    test_if();
    test_loop_while_for();
    test_ownership();
    test_references_and_borrowing();
    test_lifetimes();
    test_mutability();
    test_structs();
    test_enums();
    test_matchs();
    test_patterns();
    test_method_syntaxs();
    test_vectors();
}

// #[test]
fn test_variable_binding() {
    let x = 100; // x: i32
    assert_eq!(x, 100);

    // By default, bindings are immutable
    let x: i32 = 5;
    assert_eq!(x, 5);

    let mut _x = 1;
    _x = 10;
    let b = _x == 10;
    assert!(b);
}

// #[test]
fn test_functions() {
    fn foo() {}
    foo();

    fn print_number(x: i32) {
        println!("x is {}", x);
    }
    print_number(5);

    fn print_sum(x: i32, y: i32) {
        println!("sum is: {}", x + y);
    }
    print_sum(100, 200);

    fn add_one(x: i32) -> i32 {
        x + 1
    }
    assert_eq!(add_one(100), 101);

    // A diverging function can be used as any type
    fn diverges() -> ! {
        panic!("This function never returns!");
    }
    thread::spawn(|| {
        let _x: String = diverges();
    });

    // function pointers
    let f: fn(i32) -> i32 = add_one;
    assert_eq!(f(100), 101);
    let f2 = add_one;
    assert_eq!(f2(10), 11);


    ()
}

// #[test]
fn primitive_types() {
    // Booleans
    let x = true;
    let y: bool = false;
    assert!(x);
    assert!(!y);
    assert!(x || y);
    assert!(!(x && y));


    // Char
    // Rust’s char is not a single byte, but four.
    let x = 'x';
    let two_hearts = '💕';
    println!("{},{}", x, two_hearts);


    // Numeric types
    //
    // i8
    // i16
    // i32
    // i64
    // u8
    // u16
    // u32
    // u64
    // isize
    // usize
    // f32
    // f64
    //

    // Fixed size types: [i|u]length
    // signed types: use i
    // unsigned types: use u
    // Variable sized types: types whose size depends on the size of a pointer of
    // the underlying machine
    //   isize, usize
    // Floating-point types: f32, f64
    let _x = 43; // x has type i32
    let _y = 1.0; // y has type f64


    // Arrays
    // a fixed-size list of elements of the same type
    //   Arrays have type [T; N]
    //   By default, arrays are immutable
    let a = [1, 2, 3]; // a: [i32; 3]
    let mut m: [i32; 3] = [1, 2, 3]; // m: [i32; 3]
    assert_eq!(3, a.len());
    assert_eq!(3, m[2]);
    m[2] = 100;
    assert_eq!(100, m[2]);

    let a = [0; 20]; // a: [i32; 20]
    assert_eq!(a[0], 0);
    assert_eq!(a[19], 0);
    assert_eq!(20, a.len());

    let names = ["Graydon", "Brian", "Niko"]; // names: [&str; 3]
    assert_eq!(names[0], "Graydon");


    // Slices
    //  Slices have type &[T]
    //  A 'slice' is a reference to (or 'view' into) another data structure.
    // They are useful for allowing safe, efficient access to a portion of an
    // array without copying.
    let a = [0, 1, 2, 3, 4];
    let middle = &a[1..4]; // A slice of a: just the elements 1, 2, and 3
    assert_eq!(middle.len(), 4 - 1);
    assert_eq!(middle[0], 1);


    // Str
    //    Rust's str type is the most primitive string type.
    // As an unsized type, it’s not very useful by itself, but becomes useful
    // when placed behind a reference, like &str


    // Tuples
    let x = (1, "hello");
    assert_eq!(x.0, 1);
    assert_eq!(x.1, "hello");

    let (f, _) = x;
    assert_eq!(f, 1);


    // Functions
    fn foo(x: i32) -> i32 {
        x
    }
    let x: fn(i32) -> i32 = foo;
    assert_eq!(x(100), 100);
}

// #[test]
fn test_comments() {
    // Line comments are anything after '//'  and extend to the end of the line.

    /// Adds one to the number given.
    ///
    /// # Examples
    ///
    /// ```
    /// let five = 5;
    ///
    /// assert_eq!(6, add_one(5));
    /// # fn add_one(x: i32) -> i32 {
    /// #     x + 1
    /// # }
    /// ```
    fn add_one(x: i32) -> i32 {
        x + 1
    }
    assert_eq!(100, add_one(99));
}

// #[test]
fn test_if() {
    let x = 5;

    if x == 5 {
        println!("x is five!");
    } else if x == 6 {
        println!("x is six!");
    } else {
        println!("x is not five or six");
    }

    let y = if x == 5 {
        10
    } else {
        15
    };
    assert_eq!(y, 10);
}

// #[test]
fn test_loop_while_for() {
    let mut i = 0;
    loop {
        println!("Loop {}", i);
        if i > 10 {
            break;
        }
        i += 1;
    }
    assert_eq!(i, 11);

    let mut x = 5; // mut x: i32
    let mut done = false; // mut done: bool
    while !done {
        x += x - 3;
        println!("{}", x);

        if x % 5 == 0 {
            done = true;
        }
    }

    // for var in expression {
    //     code
    // }
    // The expression is an iterator. The iterator gives back a series of elements.
    // Each element is one iteration of the loop.
    let mut sum = 0;
    for x in 0..10 {
        sum += x;
    }
    assert_eq!(sum, 45);

    // Enumerate
    // On ranges
    for (i, j) in (5..10).enumerate() {
        println!("i = {} and j = {}", i, j);
    }

    for x in 0..10 {
        if x % 2 == 0 {
            continue;
        }
        println!("{}", x);
    }

    'outer: for x in 0..10 {
        'inner: for y in 0..10 {
            if x % 2 == 0 {
                continue 'outer;
            } // continues the loop over x
            if y % 2 == 0 {
                continue 'inner;
            } // continues the loop over y
            println!("x: {}, y: {}", x, y);
        }
    }
}

// #[test]
fn test_ownership() {
    // Variable bindings have a property in Rust: they 'have ownership' of what
    // they're bound to.
    // This means that when a binding goes out of scope, Rust will free the bound
    // resources.
    fn foo() {
        let v = vec![1, 2, 3];
        // when v comes into scope, a new Vec<T> is created. In this case, the vector
        // also allocates space on the heap, for the three elements.
        // when v goes out of scope at the end of foo(), Rust will clean up everythin
        // related to the vector, even the heap-allocated memory.
        // This happens deterministically, at the end of the scope.
        println!("{:?}", v);
    }

    foo();

    // Move semantics
    //   Rust ensures that there is exactly one binding to any given resource.
    let v = vec![1, 2, 3];
    let v2 = v;
    // println!("{:?}", v); // compile error: use moved value: `v`
    assert_eq!(v2, vec![1, 2, 3]);

    fn take(_v: Vec<i32>) {}

    take(v2);
    // println!("v[0] is: {}", v2[0]);
    // take(v2); // compile error: use moved value.

    // ////////////////////////////////////////////
    // let v = vec![1, 2, 3];
    // let v2 = v;
    // The first line allocates memory for the vector object, v, and for the data
    // it contains.
    // The vector object is stored on the stack and contains a pointer to the
    // content ([1, 2, 3]) stored on the heap.
    // When we move v to v2, it creates a copy of that pointer, for v2
    // Which means that there would be two pointers to the content of the vector
    // on the heap
    // It would violate Rust’s safety guarantees by introducing a data race.
    // Therefore, Rust forbids using v after we’ve done the move.


    // Copy types
    // We’ve established that when ownership is transferred to another binding, you
    // cannot use the original binding
    // However, there’s a trait that changes this behavior, and it’s called Copy.
    //
    let v = 1;
    // This means that, just like a move, when we assign v to v2, a copy of the
    // data is made.
    let v2 = v;
    // v is an i32, which implements the Copy trait, But, unlike a move, we can
    // still use v afterward,
    // This is because an i32 has no pointers to data somewhere else, copying it is
    // a full copy
    println!("{} {}", v, v2);

    // All primitive types implement the Copy trait and their ownership is
    // therefore not moved like one would assume, following the ´ownership rules´
    // If we would have used types that do not implement the Copy trait, we would
    // have gotten a compile error because we tried to use a moved value
    fn foo2(v: Vec<i32>) -> Vec<i32> {
        // do stuff with v

        // hand back ownership
        v
    }

    let d = vec![1, 2, 3];
    let c = foo2(d);
    assert_eq!(c[2], 3);
}

// #[test]
fn test_references_and_borrowing() {
    // We call the &T type a ‘reference’,
    // and rather than owning the resource, it borrows ownership.
    // A binding that borrows something does not deallocate the resource when it
    // goes out of scope.
    // References are immutable
    fn foo(v1: &Vec<i32>, v2: &Vec<i32>) -> i32 {
        // do stuff with v1 and v2
        // return the answer
        if v1.len() > 0 && v2.len() > 0 {
            v1[0] + v2[0]
        } else {
            -1
        }
    }

    let v1 = vec![1, 2, 3];
    let v2 = vec![1, 2, 3];
    let answer = foo(&v1, &v2);

    // we can use v1 and v2 here!
    assert_eq!(v1[0], v2[0]);

    assert_eq!(answer, 2);

    // &mut references
    // There’s a second kind of reference: &mut T
    // A ‘mutable reference’ allows you to mutate the resource you’re borrowing.
    // Otherwise, &mut references are just like references
    // *ref for accessing the contents of a reference
    //
    let mut x = 5;
    {
        let y = &mut x;
        *y += 1;
    }
    assert_eq!(x, 6);

    // Rules
    // 1. borrow must last for a scope no greater than that of the owner
    // 2. you may have one or the other of these two kinds of borrows, but not both
    // at the same time
    //    one or more references (&T) to a resource
    //    exactly one mutable reference (&mut T)
    // the mutable borrow prevents subsequent moves, borrows, or modification of
    // `x` until the borrow ends
    // you can only have one &mut at a time
}

// #[test]
fn test_lifetimes() {
    // The ownership system in Rust does this through a concept called lifetimes,
    // which describe the scope that a reference is valid for.

    // When we have a function that takes a reference by argument,
    // we can be implicit or explicit about the lifetime of the reference

    // implicit
    fn foo(_x: &i32) {}

    // explicit
    // 'a reads ‘the lifetime a’
    fn bar<'a>(_x: &'a i32) {}

    foo(&100);
    bar(&200);

    struct Foo<'a> {
        // ensure that any reference to a Foo cannot outlive the reference to an i32 it contains.
        x: &'a i32,
    }

    impl<'a> Foo<'a> {
        fn x(&self) -> &'a i32 {
            self.x
        }
    }

    let y = &5; // this is the same as `let _y = 5; let y = &_y;`
    let foo = Foo { x: &y };
    println!("{}", foo.x);
    println!("x is: {}", foo.x());

    fn x_or_y<'a>(_x: &'a str, _y: &'a str) -> &'a str {
        ""
    }

    println!("{}", x_or_y("hello", "world"));

    let y = &5;

    {
        let foo = Foo { x: y };
        println!("{}", foo.x);
    }

    // 'static
    // The lifetime named ‘static’ is a special lifetime


    // It signals that something has the lifetime of the entire program

    let x: &'static str = "Hello, world.";
    println!("{}", x);

    // they are baked into the data segment of the final binary
    static FOO: i32 = 5;
    let _x: &'static i32 = &FOO;


    // When talking about lifetime elision, we use the term input lifetime and
    // output lifetime. An input lifetime is a lifetime associated with a parameter
    // of a function,
    // and an output lifetime is a lifetime associated with the return value of a
    // function

    fn frob<'a, 'b>(_s: &'a str, _t: &'b str) -> &'a str {
        ""
    }

    frob("a", "b");

    ()
}

// #[test]
fn test_mutability() {
    // When a binding is mutable,
    // it means you’re allowed to change what the binding points to
    let mut x = 5;
    x = 6;
    println!("{}", x);
    assert_eq!(x, 6);

    // If you want to change what the binding points to, you’ll need a mutable reference
    let mut c = 5;
    {
        // y is an immutable binding to a mutable reference,
        // which means that you can’t bind y to something else (y = &mut z),
        // but you can mutate the thing that’s bound to y (*y = 5)
        //
        let y = &mut c;
        *y = 10;
    }
    assert_eq!(10, c);

    let mut d = 5;
    {
        let mut r: &mut i32 = &mut d;
        *r = 10;

        r = &mut c;
    }
    assert_eq!(d, 10);

    let (mut a, b) = (5, 6);
    assert_eq!(a, 5);

    // Mutability is a property of either a borrow (&mut) or a binding (let mut)
    // This means that, for example,
    // you cannot have a struct with some fields mutable and some immutable
    struct Point {
        x: i32,
        y: i32,
    }

    let mut p = Point { x: 5, y: 6 };
    // The mutability of a struct is in its binding:
    p.x = 10;

    assert_eq!(p.x, 10);


    struct Point2 {
        x: i32,
        y: Cell<i32>,
    }
    let p2 = Point2 {
        x: 5,
        y: Cell::new(10),
    };

    p2.y.set(7);
    assert_eq!(p2.y.get(), 7);

    let cell: Cell<i32> = Cell::new(100);
    assert_eq!(cell.get(), 100);
}

// #[test]
fn test_structs() {
    struct Point {
        x: i32,
        y: i32,
    }

    let origin = Point { x: 0, y: 0 };

    assert_eq!(0, origin.x);

    let mut point = Point { x: 10, y: 10 };
    point.y = 200;
    assert_eq!(200, point.y);

    // Update syntax
    struct Point3d {
        x: i32,
        y: i32,
        z: i32,
    }
    let mut p3 = Point3d { x: 0, y: 1, z: 2 };

    assert_eq!(p3.z, 2);

    let p31 = Point3d { y: 100, ..p3 };
    assert_eq!(p31.y, 100);
    assert_eq!(p31.z, 2);

    // Tuple struct
    struct Color(i32, i32, i32);

    let black = Color(0, 0, 0);
    assert_eq!(black.0, 0);
    assert_eq!(black.2, 0);

    // There is one case when a tuple struct is very useful,
    // though, and that’s a tuple struct with only one element.
    // We call this the ‘newtype’ pattern, because it allows you to create a new type,
    // distinct from that of its contained value and expressing its own semantic meaning:
    //
    struct Inches(i32);

    let length = Inches(10);

    let Inches(il) = length;
    assert_eq!(il, 10);

    // Unit-like structs
    #[derive(Debug, PartialEq)]
    struct Electron;

    let x = Electron;
    assert_eq!(x, Electron);
}

// #[test]
fn test_enums() {
    //  an enum is sometimes called a ‘sum type’:
    // the set of possible values of the enum is the sum of
    // the sets of possible values for each variant
    #[derive(Debug)]
    enum Message {
        Quit,
        ChangeColor(i32, i32, i32),
        Move {
            x: i32,
            y: i32,
        },
        Write(String),
    }

    // use the :: syntax to use the name of each variant
    let x: Message = Message::Move { x: 3, y: 4 };
    #[derive(Debug)]
    enum BoardGameTurn {
        Move {
            squares: i32,
        },
        Pass,
    }
    let y: BoardGameTurn = BoardGameTurn::Move { squares: 1 };
    println!("y: {:?}", y);

    // Constructors as functions
    let m = Message::Write("Hello, World".to_string());
    println!("m:{:?}", m);

    let v = vec!["Hello".to_string(), "World".to_string()];
    let v1: Vec<Message> = v.into_iter().map(Message::Write).collect();
    println!("v1: {:?}", v1);
}

// #[test]
fn test_matchs() {
    let x = 5;
    let xname = match x {
        1 => "one",
        2 => "tow",
        3 => "three",
        4 => "four",
        5 => "five",
        _ => "someelse",
    };
    assert_eq!(xname, "five");

    // matching on enums
    enum Message {
        Quit,
        ChangeColor(i32, i32, i32),
        Move {
            x: i32,
            y: i32,
        },
        Write(String),
    }

    fn quit() {
        // ...
    }
    fn change_color(r: i32, g: i32, b: i32) {
        // ...
    }
    fn move_cursor(x: i32, y: i32) {
        // ...
    }

    fn process_message(msg: Message) {
        match msg {
            Message::Quit => quit(),
            Message::ChangeColor(r, g, b) => change_color(r, g, b),
            Message::Move{ x: x, y: y } => move_cursor(x, y),
            Message::Write(s) => println!("{}", s),
        };
    }

    process_message(Message::Write("Hello".to_string()));
}


// #[test]
fn test_patterns() {
    let x = 1;
    let c = 'c';

    match c {
        x => println!("x: {} c: {}", x, c),
    }

    println!("x: {}", x);
    assert_eq!(x, 1);

    // Multiple patterns
    match x {
        1 | 2 => println!("one or two"),
        3 => println!("three"),
        _ => println!("anything"),
    }

    // Destructuring
    struct Point {
        x: i32,
        y: i32,
    }
    let origin = Point { x: 0, y: 0 };
    match origin {
        Point {x, y} => println!("({}, {})", x, y),
    }

    match origin {
        Point { x: x1, y: y1 } => println!("({},{})", x1, y1),
    }

    match origin {
        Point { x, .. } => println!("x is {}", x),
    }

    // Ignoring bindings
    let a: Result<&'static str, ()> = Ok("itang");
    match a {
        Ok(s) => println!("{}", s),
        Err(_) => println!("error"),
    }

    fn coordinate() -> (i32, i32, i32) {
        (1, 2, 3)
    }

    let (x, _, z) = coordinate();
    assert_eq!(x + z, 4);

    // use .. in a pattern to disregard multiple values.
    enum OptionalTuple {
        Value(i32, i32, i32),
        Missing,
    }

    let x = OptionalTuple::Value(5, -2, 3);

    match x {
        OptionalTuple::Value(..) => println!("Got a tuple!"),
        OptionalTuple::Missing => println!("No such luck."),
    }

    // ref and ref mut
    // if you want to get a reference , use the ref keyword.
    let x = 5;
    match x {
        ref r => println!("Got a reference to {}", r),
    }

    let mut x = 5;

    match x {
        ref mut mr => println!("Got a mutable reference to {}", mr),
    }

    // Ranges
    let x = 5;
    match x {
        1...5 => println!("one through five"),
        _ => println!("anything"),
    }

    let x = '💅';

    match x {
        'a'...'j' => println!("early letter"),
        'k'...'z' => println!("late letter"),
        _ => println!("something else"),
    }

    // Bindings
    let x = 1;
    match x {
        e @ 1 ... 5 => println!("got a range element {}", e),
        _ => println!("anything"),
    }

    #[derive(Debug)]
    struct Person {
        name: Option<String>,
    }

    let name = "Steve".to_string();
    let mut x: Option<Person> = Some(Person { name: Some(name) });
    match x {
        Some(Person { name: ref a @ Some(_), .. }) => println!("{:?}", a),
        _ => {}
    }
    let x = 5;

    match x {
        e @ 1 ... 5 | e @ 8 ... 10 => println!("got a range element {}", e),
        _ => println!("anything"),
    }

    enum OptionalInt {
        Value(i32),
        Missing,
    }

    let x = OptionalInt::Value(5);

    match x {
        OptionalInt::Value(i) if i > 5 => println!("Got an int bigger than five!"),
        OptionalInt::Value(..) => println!("Got an int!"),
        OptionalInt::Missing => println!("No such luck."),
    }

    let x = 4;
    let y = false;

    match x {
        4 | 5 if y => println!("yes"),
        _ => println!("no"),
    }

    struct Foo {
        x: Option<String>,
        y: i32,
    }

    let foo = Foo {
        x: Some("itang".to_string()),
        y: 30,
    };
    match foo {
        Foo {x: Some(ref name), y: 30} => println!("name: {}, age: 30", name),
        _ => println!("NO"),
    }
}


// #[test]
fn test_method_syntaxs() {
    // method call syntax’ via the impl keyword
    #[derive(Debug)]
    struct Circle {
        x: f64,
        y: f64,
        radius: f64,
    }

    impl Circle {
        // Methods take a special first parameter, of which there are three variants:
        // self, &self, and &mut self.
        fn area(&self) -> f64 {
            std::f64::consts::PI * (self.radius * self.radius)
        }

        fn reference(&self) {
            println!("taking self by reference!");
        }

        fn mutable_reference(&mut self) {
            println!("taking self by mutable reference!");
        }

        fn takes_ownership(self) {
            println!("taking ownership of self!");
        }

        fn grow(&self, increment: f64) -> Circle {
            Circle {
                x: self.x,
                y: self.y,
                radius: self.radius + increment,
            }
        }


        fn new(x: f64, y: f64, radius: f64) -> Circle {
            Circle {
                x: x,
                y: y,
                radius: radius,
            }
        }
    }

    let mut c = Circle {
        x: 0.0,
        y: 0.0,
        radius: 2.0,
    };
    println!("{}", c.area());

    c.reference();

    {
        c.mutable_reference();
    }

    c.takes_ownership();

    // println!("{:?}", c); // use of moved value: `c`
    let cc = Circle {
        x: 0.0,
        y: 0.0,
        radius: 2.0,
    };
    let d = cc.grow(2.0).area();
    println!("{}", d);

    // Associated functions
    let dd = Circle::new(1.0, 1.0, 2.0);
    println!("{:?}", dd);

    // Builder Pattern
    struct CircleBuilder {
        x: f64,
        y: f64,
        radius: f64,
    }

    impl CircleBuilder {
        fn new() -> CircleBuilder {
            CircleBuilder {
                x: 0.0,
                y: 0.0,
                radius: 1.0,
            }
        }

        fn x(&mut self, coordinate: f64) -> &mut CircleBuilder {
            self.x = coordinate;
            self
        }

        fn y(&mut self, coordinate: f64) -> &mut CircleBuilder {
            self.y = coordinate;
            self
        }

        fn radius(&mut self, radius: f64) -> &mut CircleBuilder {
            self.radius = radius;
            self
        }

        fn finalize(&self) -> Circle {
            Circle {
                x: self.x,
                y: self.y,
                radius: self.radius,
            }
        }
    }

    let a = CircleBuilder::new().x(1.0).y(2.0).radius(2.0).finalize();
    println!("{:?}", a);
    println!("{}", a.area());
}


// #[test]
fn test_vectors() {
    // A ‘vector’ is a dynamic or ‘growable’ array,
    // implemented as the standard library type Vec<T>.
    // The T means that we can have vectors of any type (see the chapter on generics for more).
    // Vectors always allocate their data on the heap. You can create them with the vec! macro
    let v: Vec<i32> = vec![1, 2, 3, 4, 5, 6];
    println!("{:?}", v);

    let v1 = vec![0; 10];
    println!("{:?}", v1);

    // Accessing elements
    // must index with the usize type
    assert_eq!(3, v[2]);
    assert_eq!(0, v1[2]);
    let i: usize = 5;
    assert_eq!(6, v[i]);

    let i2: i32 = 2;
    // assert_eq!(3, v[i2]); // the trait `core::ops::Index<i32>` is not implemented
    // for the type `collections::vec::Vec<i32>

    // Iterating
    for i in &v {
        println!("A reference to {}", i);
    }

    let mut v = vec![1, 2, 3];
    for i in &mut v {
        println!("A mutable reference to {}", i);
    }

    for i in v {
        println!("Take ownership of the vector and its element {}", i);
    }

    //println!("{:?}", v); // use of moved value: `v`

}
