use std::thread;

fn main() {
    println!("Hello, world!");
}

#[test]
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

#[test]
fn test_functions() {
    fn foo() {

    }
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

#[test]
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

#[test]
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

#[test]
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

#[test]
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

    //
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

#[test]
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

    fn take(_v: Vec<i32>) {

    }

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

#[test]
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

#[test]
fn test_lifetimes() {
    // The ownership system in Rust does this through a concept called lifetimes,
    // which describe the scope that a reference is valid for.

    // When we have a function that takes a reference by argument,
    // we can be implicit or explicit about the lifetime of the reference

    // implicit
    fn foo(_x: &i32) {
    }

    // explicit
    // 'a reads ‘the lifetime a’
    fn bar<'a>(_x: &'a i32) {
    }

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

    fn x_or_y<'a>(x: &'a str, y: &'a str) -> &'a str {
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

    fn frob<'a, 'b>(s: &'a str, t: &'b str) -> &'a str {
        ""
    }

    frob("a", "b");

    ()
}
