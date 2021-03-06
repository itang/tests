#[test]
fn test_test() {
    assert_eq!(1, 1);
    assert!(true);
}

#[cfg(test)]
static MAX_HEALTH: i32 = 100;

#[cfg(test)]
use std::f32::consts;

#[test]
fn test_global_constants() {
    assert_eq!(100, MAX_HEALTH);
    // MAX_HEALTH = 200; // cannot assign to immutable static item
    // assert_eq!(200, MAX_HEALTH);

    println!("PI:{}", consts::PI);
}

#[test]
fn test_vecs() {
    let v1: Vec<i32> = Vec::new();
    assert_eq!(0, v1.len());

    let mut magic_numbers = vec![7i32, 42, 47, 45, 54];
    assert_eq!(5, magic_numbers.len());
    assert_eq!(54, magic_numbers[4]);
    magic_numbers[4] = 100;
    assert_eq!(100, magic_numbers[4]);

    let ids: Vec<i32> = Vec::with_capacity(10);
    assert_eq!(0, ids.len());
    assert_eq!(10, ids.capacity());

    let rgvec: Vec<u32> = (0..7).collect();
    assert_eq!(7, rgvec.len());
    assert_eq!(6, rgvec[rgvec.len() - 1]);

    let mut numbers: Vec<i32> = Vec::new();

    for i in rgvec {
        numbers.push(i as i32);
    }
    numbers.pop();
    assert_eq!(6, numbers.len());
}

#[test]
fn test_tuples() {
    let thor = ("Thor", true, 3500u32);
    assert_eq!("Thor", thor.0);
    assert_eq!(3500, thor.2);

    let (name, _, power) = thor;
    assert_eq!(name, "Thor");
    assert_eq!(3500, power);
}

#[test]
fn test_structs() {
    struct Score(i32, u8);
    let score = Score(100, 10);
    let Score(a, b) = score;
    assert_eq!(a, 100);
    assert_eq!(b, 10);

    struct Player {
        name: &'static str, // nickname
        health: i32,
        level: u8,
    }

    let mut pl1 = Player {
        name: "Dzenan",
        health: 73,
        level: 2,
    };
    assert_eq!(pl1.name, "Dzenan");
    assert_eq!(pl1.level, 2);

    pl1.health = 100;

    assert_eq!(pl1.health, 100);

    let Player{ health: ht, name: nn, .. } = pl1;

    assert_eq!(ht, 100);
    assert_eq!(nn, "Dzenan");
}


#[test]
fn test_hight_order_functions() {
    fn again<F: Fn(i32) -> i32>(f: F, s: i32) -> i32 {
        f(f(s))
    }

    assert_eq!(40, again(|x| x * 2, 10));
    assert_eq!(40, again(|x: i32| x * 2, 10));

    fn again_gen<T, F: Fn(T) -> T>(f: F, s: T) -> T {
        f(f(s))
    }
    assert_eq!(again_gen(|x| x + 10, 10), 30);

    // anonymous function or closure, |n| { 3 * n } , that takes an n
    // parameter and returns its tripled value. The || (vertical bars) mark the start of
    // a closure, and they contain the parameters that are passed to it (when there are
    // no parameters, it is written as || ). There is no need to indicate the type of the
    // parameters or the return value as a closure can infer their types from the context
    // in which it is called
    //
    let triples = |n| 3 * n;
    assert_eq!(again(triples, 10), 90);

    // There is also a special kind of closure called a moving closure, which is indicated by
    // the move keyword. A normal closure only needs a reference to the variables that it
    // encloses, but a moving closure takes ownership of all the enclosing variables
    //

    // Moving closures are mostly used when a program works with different concurrent
    // threads
    //

    let m: i32 = 42;
    let print_add_move = move |s| {
        println!("m is {}", m);
        m + s // m is copy
    };
    let strength = 78;
    let res = print_add_move(strength);
    assert_eq!(res, 120); // 42 + 702

    println!("{}", m);

    let add = |a: i32, b: i32| a + b;
    assert_eq!(100, add(50, 50));
}

#[cfg(test)]
use std::f32;

#[test]
fn test_iterators() {
    let mut rng = 0..7;
    assert_eq!(Some(0), rng.next());
    assert_eq!(Some(6), rng.last()); // rng move out scope here

    let r1 = 0..3;
    let rngvec = r1.collect::<Vec<i32>>();
    assert_eq!(3, rngvec.len());
    // println!("{:?}",r1);// use of moved value: `r1`

    let r2 = 0..10;
    let rng_even: Vec<i32> = r2.filter(|n| n % 2 == 0).collect();
    assert_eq!(rng_even[rng_even.len() - 1], 8);

    let r3 = 0..5;
    let rng_odd: Vec<i32> = r3.filter(|n| n % 2 == 1).map(|n| n * n * n).collect();
    assert_eq!(rng_odd[rng_odd.len() - 1], 27);
    assert_eq!(*rng_odd.last().unwrap(), 27);

    match rng_odd.last() {
        Some(&v) => assert_eq!(v, 27), // &v vs ref v : &&v
        None => assert!(false),
    }

    let r4 = 0..101;
    assert_eq!(5050, r4.fold(0, |sum, n| sum + n));

    let mut s = String::new();
    s.push_str("100");
    let r: Result<i32, _> = s.trim().parse();
    match r {
        Ok(i) => assert_eq!(i, 100),
        Err(_) => assert!(false),
    }


    fn sqroot(r: f32) -> Result<f32, String> {
        if r < 0.0 {
            return Err("Number cannot be negative!".to_string());
        }
        Ok(f32::sqrt(r))
    }
    let m = sqroot(42.0);
    match m {
        Ok(sq) => println!("The square root of 42 is {}", sq),
        Err(str) => println!("{}", str),
    }
}

#[test]
fn test_traits() {
    #[derive(Debug)]
    struct A {
        value: i32,
    }
    let a = A { value: 100 };
    let b = a;
    println!("{:?}", b);
    // println!("{:?}",a);// which is moved by default
}

#[test]
fn test_ownership_borrowing() {
    let a = "hello".to_string();
    let a1 = &a;
    println!("{}", a1);
    let a2 = &a;
    println!("{}", a2);
    println!("{}", a);
}

mod game1 {
    // all of the module's code items go in here
    fn _func1() {
        println!("Am I visible?");
    }
    pub fn func2() {
        println!("You called func2 in game1!");
    }

    pub mod subgame1 {
        pub fn subfunc1() {
            println!("You called subfunc1 in subgame1!");
        }
    }

    #[derive(Debug)]
    pub struct Magician {
        pub name: String,
        pub age: i32,
        power: i32,
    }

    impl Magician {
        pub fn new(name: String, age: i32) -> Magician {
            Magician {
                name: name,
                age: age,
                power: age * 2,
            }
        }
    }
}

use std::mem;

fn main() {
    println!("PI:{}", std::f64::consts::PI);
    println!("You have {points} % health", points = 70);
    println!("Magic_number: {}", 3.14_f32);

    let a = 1;
    let b = 2;
    let c = "hello";
    let d = 3i64;
    println!("{}: {:p} {}:{:p} {}:{:p} {}:{:p}",
             a,
             &a,
             b,
             &b,
             c,
             &c,
             d,
             &d);

    let hello = "Hello, World 世界!";
    for c in hello.chars() {
        println!("{}", c);
    }

    for word in hello.split(" ") {
        println!("{}", word);
    }

    let aliens: [&str; 4] = ["Cherfer", "Fynock", "Shirack", "Zuxu"];
    for a in aliens.iter() {
        println!("{}", a);
    }

    let zuxus = ["Zuxu"; 3];
    for z in zuxus.iter() {
        println!("{}", z);
    }

    for z in &zuxus {
        println!("{}", z);
    }

    for i in 0..3 {
        println!("i: {}", i);
    }

    game1::func2();
    game1::subgame1::subfunc1();

    let mag1 = game1::Magician::new("itang".to_string(), 30);
    println!("{:?}", mag1);


    // Macros
    //
    // macro_rules! mac1 {
    // (pattern) => (expansion);
    // (pattern) => (expansion);
    // ...
    // }
    //

    macro_rules! welcome {
        () => (
            println!("Welcome to the Game!");
        )
    }

    welcome!();

    macro_rules! mac1 {
        ($arg:expr) => (println!("arg is {}", $arg));
    }

    mac1!("hello");
    // Write a mac2 macro that triples its argument. Test it out for these arguments:
    // 5 and 2 + 3.
    // •	 Write a mac3 macro that takes an identifier name and replaces it with
    // a binding of that name to 42. (As a hint, use $arg:ident instead of
    // $arg:expr; ident is used for variable and function names.)
    // •	 Write a mac4 macro that when invoked like mac4!("Where am I?"); , prints
    // out start - Where am I? - end
    //

    macro_rules! m1 {
      ($arg1: expr, $arg2: expr) => (
          if $arg1==$arg2 {
              println!("Assert true");
          }else {
              println!("left {}, rigth {}, not equal", $arg1, $arg2);
          }
      )
    }
    m1!(5, 2 + 3);

    m1!(5, 2 + 4);

    // ident is used for variable and function names
    macro_rules! m2 {
        ($arg1:ident) => (
            let $arg1 = 42;
        )
    }
    m2!(number);

    println!("number is {}", number);

    macro_rules! m3 {
        ($arg1: expr) => (
            print!("start - ");
            print!($arg1);
            print!(" - end\n")
        )
    }

    m3!("Where am I?");

    macro_rules! printall {
        ( $( $arg: expr), * ) => (
            {
                $(print!("{} /", $arg)); *
            }
        );
    }

    printall!("hello", 32, 3.14);
    println!("");

    macro_rules! create_fn {
        ($fname: ident) => (
            fn $fname() {
                println!("Called the function {:?}()", stringify!($fname));
            }
        )
    }
    create_fn!(hello1);
    hello1();

    macro_rules! massert {
        ($arg:expr) => (
            if $arg {}
            else {
                panic!("Assertion failed!");
            }
        )
    }
    massert!(true);

    macro_rules! unless{
        ($arg: expr, $branch: expr) => (
            if !$arg{
                $branch
            };
        )
    }
    unless!([10, 30].contains(&20), println!("v don't contains 20"));


    /// //////////////////////////////////////////////////////////////
    fn test_threads() {
        use std::thread;
        let t = thread::spawn(move || {
            println!("Hello from the goblin in the spawned thread!");
            thread::sleep_ms(10);
        });
        t.join().unwrap();
    }
    test_threads();

    let args: Vec<String> = std::env::args().collect();
    println!("The program's name is: {}", args[0]);

    for (k, v) in std::env::vars() {
        println!("{}:\t {}", k, v);
    }

    let v: &[u8] = unsafe {
        mem::transmute("Gandalf")
    };
    println!("{:?}", v);
}

macro_rules! test_eq {
    ($name:ident, $left:expr, $right:expr) => {
        #[test]
        fn $name() {
            assert_eq!($left, $right);
        }
    }
}

test_eq!(seven_times_six_is_not_forty_three, 7 * 6, 42);
