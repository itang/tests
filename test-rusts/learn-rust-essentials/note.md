#

## Rust用在什么地方

客户端应用

低延时， 高性能系统（device drivers, games, and signal processing）

高可分布式和并发系统

实时和critical系统， （操作系统和内核）

嵌入式系统 （(that require a very minimal runtime footprint) or
a resource-constrained environment, such as a Raspberry Pi, Arduino,
or robotics）

工具或者服务

web框架

大规模高性能，资源敏感的灵活软件系统


## tips

* rustc welcome.rs -o start

-o 指定输出名字


* rustc –O
= rustc -C opt-level=2

* ///for doc (markdown)


/// Start of the Game

### println! named arguments
* println!("You have {points} % health", points=70);


### The numeric types i32 and f64 are the defaults if no suffix is given

let e = 7.0

let e = 7.0f64

let e: f64 = 7.0

### empty value () of zero size, which is the only value of the so-
called unit type ()


### the _ separates the
digits from the type to improve readability

let magic_number = 3.14f32
let magic_number = 3.14_f32

### The stack and the heap
Primitive values such as numbers (like 32 in the figure),
characters, and true/false values are stored on the stack, while the value of more
complex objects that could grow in size are stored in the heap memory. Heap values
are referenced by a variable on the stack, which contains the memory address of the
object on the heap

&str because strings are stored on the heap

### attributes

#[warn(unused_variables)]

#[cfg(test)]


#[cfg(target_os = "xyz")]

windows , macos , linux , android , freebsd , dragonfly , bitrig , or openbsd

###

Fixed-size (stack allocated) Slices                           Dynamic size(growable) (Heap allocated)

                             &str           is a view into     String
                             type: &[u8]    is a view into     Vector type: Vec<T>


### newtype

struct Kilograms(u32);


### Pointers carry out automatic dereferencing when accessing data structure elements,

let ps = &Player{ nname: "John", health: 95, level: 1 };
println!("{} == {}", ps.nname, (*ps).nname);

### return self type: Self;

trait Monster {
  fn new(hlt: u32, dam: u32) -> Self;


### where

fn multc<T: Trait1, U: Trait1 + Trait2>(x: T, y: U) {}

=>

fn multc<T, U>(x: T, y: U) where T: Trait1, U: Trait1 + Trait2 {}


### builtin Traits

Comparing objects (the Eq and PartialEq traits).
•	 Ordering objects (the Ord and PartialOrd traits).
•	 Creating an empty object (the Default trait).
•	 Formatting a value using {:?} (the Debug trait, which defines a fmt method).
•	 Copying an object (the Clone trait).
•	 Adding objects (the Add trait, which defines an add method)
•	 Freeing the resources of an object when it goes out of scope (the Drop trait in
other words, the object has a destructor)

### get a reference to a matched variable inside a match function

let n = 42;
match n {
    ref r => println!("Got a reference to {}", r),
}

let mut m = 42;
match m {
    ref mut mr => {
        println!("Got a mutable reference to {}", mr);
        *mr = 43;
    },
}


### Pointers

Pointers Pointer names Description
&T Reference This allows one or more references to read T.
&mut T Mutable reference This allows a single reference to read and write T.
Box<T> Box This is a heap-allocated T with a single owner that
may read and write T.
Rc<T> Rc pointer This is a heap-allocated T with many readers.
Arc<T> Arc pointer This is like Rc<T>, but enables safe mutable sharing
across threads (refer to Chapter 8, Concurrency and
Parallelism).
*const
T Raw pointer This allows unsafe read access to T (refer to Chapter 9,
Programming at the Boundaries).
*mut T Mutable raw pointer This allows unsafe read and write access to T (refer to
Chapter 9, Programming at the Boundaries).


### define macro

macro_rules! mac1 {
    (pattern) => (expansion);
    (pattern) => (expansion);
    ...
}

A matcher can contain an expression of the $arg:frag form:
•	 The $arg function binds an arg meta-variable to a value when the macro is
called. Variables used inside a macro such as $arg , are prefixed with a $ sign
to distinguish them from normal variables in the code.
•	 The frag function is a fragment specifier and can be either expr , item , block ,
stmt , pat , ty (type), ident , path , or tt .
(You can find more information on the meaning of these fragments in the
official documentation at http://doc.rust-lang.org/1.0.0/book .)
Any other Rust literals (tokens) that appear in a matcher must match exactly.
For example, the following mac1 macro:
macro_rules! mac1 {
($arg:expr) => (println!("arg is {}", $arg));
}


### use macro from crate

#[macro_use(mac1, mac2)]
extern crate abc;

### share mutable data

let data = Arc::new(Mutex::new(health));

And, in the for loop, we make a new pointer to the Mutex with clone :

for i in 2..5 {
    let mutex = data.clone();
        thread::spawn(move || {
        let mut health = mutex.lock().unwrap();
        *health *= i;
    });
}
