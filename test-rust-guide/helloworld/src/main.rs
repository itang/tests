use std::io;

use std::rc::Rc;

fn main() {
  println!("Hello, {:s}!", "Rust");

  variable_bindings();

  _if();

  functions();

  comments();

  compound_data_types();

  _match();

  loops();

  strings();

  vectors();

  standard_input();

  pointers();
}

fn variable_bindings() {
  let x = 5i;
  assert!(x == 5i);

  let (x, y) = (1i, 2i);
  assert!(x == 1i && y == 2i);

  let x: int = 5;
  assert!(x == 5i);

  // error: re-assignment of immutable variable `x`
  // x = 10i; 

  let mut x = 5i;
  assert!(x == 5);

  x = 10i;
  assert!(x == 10);
}

fn _if() {
  let x = 5i;

  if x == 5i {
    println!("x is five!");
  } else {
    println!("x is not five :(");
  }

  let y = if x == 5i {
    10i
  } else if x == 10i {
    12i
  } else {
    15i
  };
  assert!(y == 10);

  let y = if x != 5i { 10i } else { 15i };
  assert!(y == 15i);

}

fn functions() {
  fn foo() { }
  foo();

  fn println_number(x: int) {
    println!("x is: {}", x);
  }
  println_number(10);
  println_number(10i);

  fn println_number_i32(x: i32) {
    println!("x is: {}", x);
  }
  println_number_i32(100);

  fn println_sum(x: int, y: int) {
    println!("sum is: {}", x + y);
  }
  println_sum(10, 100);

  fn add_one(x: int) -> int {
    x + 1
  }
  assert!(add_one(100) == 101)
}

fn comments() {
  // Line comments are anything after '//' and extend to the end of the line.

  let x = 5i; // this is also a line comment.
  assert!(x == 5);

  // If you have a long explanation for something, you can put line comments next
  // to each other. Put a space between the // and your comment so that it's
  // more readable.

  // The other kind of comment is a doc comment. Doc comments use /// instead of //, and support Markdown notation inside

  /// `hello` is a function that prints a greeting that is personalized based on
  /// the name given.
  ///
  /// # Arguments
  ///
  /// * `name` - The name of the person you'd like to greet.
  ///
  /// # Example
  ///
  /// ```rust
  /// let name = "Steven";
  /// hello(name); // prints "Hello, Steve!"
  /// ```
  fn hello(name: &str) {
    println!("Hello, {}!", name);
  }
  hello("itang");
}

fn compound_data_types() {
  fn tuples() {
    let x: (int, &str) = (1i, "hello");
    println!("x is: {}", x);

    let (x, y, z) = (1i, 2i, 3i);
    assert!(x == 1);
    assert!(z == 3i);
    assert!(y == 2i);

    fn next_two(x: int) -> (int, int) {
      (x + 1, x + 2)
    }

    let (x, y) = next_two(2);
    assert!(x == 3);
    assert!(y == 4);
  }
  tuples();

  fn structs() {
    struct Point {
      x: int,
      y: int,
    }
    let origin = Point { x: 0i, y: 0i };
    assert!((origin.x, origin.y) == (0, 0));

    let mut point = origin;
    point.x = 100;
    assert!(point.x == 100);
  }
  structs();

  fn tuple_struts_and_newtypes() {
    struct Color(int, int, int);
    struct Point(int, int, int);

    let black = Color(0, 0, 0);
    let Color(x, _, _) = black;
    assert!(x == 0)

    let origin = Point(0, 0, 0);
    let Point(_, _, z) = origin;
    assert!(z == 0);


    //a tuple struct with only one element. We call this a 'newtype
    struct Inches(int);
    let length = Inches(10);
    let Inches(i) = length;
    assert!(i == 10);
  }
  tuple_struts_and_newtypes();

  fn enums() {
    enum Ordering {
      Less,
      Equal,
      Greater,
    }

    fn cmp(a: int, b: int) -> Ordering {
      if a < b { Less }
      else if a > b { Greater }
      else { Equal }
    }

    let x = 5i;
    let y = 10i;
    let ordering = cmp(x, y);

    // binary operation `==` cannot be applied to
    /*if(ordering == Less){
      println!("less");
    }*/

    match ordering {
      Less => println!("less"),
      Greater => println!("greater"),
      _ => println!("equal")
    }

    enum OptionalInt {
      Value(int),
      Missing,
    }

    let x = Value(5);
    let y = Missing;

    fn p(it: OptionalInt) {
      match it {
        Value(n) => println!("it is {:d}", n),
        Missing => println!("it is missing!"),
      }
    }
    p(x);
    p(y);
  }
  enums();
}

fn _match() {
  let x = 5i;

  match x {
    1 => println!("one"),
    2 => println!("two"),
    _ => println!("something else"),
  }

  let i = 10i;
  let result = match i {
    10i => i * 10,
    _ => i * 100,
  };

  assert!(result == 100);
}

fn loops() {
  // for
  // for var in expression {
  //   code
  // }

  for x in range(0i, 5) {
    println!("{:d}", x);
  }

  // while

  let mut x = 5u;
  let mut done = false;
  while !done {
    x += x - 3;
    println!("{}", x);
    if x % 5 == 0 { done = true; }
  }

  let mut i = 0i;
  while true {
    i = i + 1;
    if i > 10 { break; }
  }

  //loop break
  i = 0i;
  loop {
    i = i + 1;
    if i > 10 { break; }
  }

  // continue
  for x in range(0i, 5) {
    if x % 2 == 0 { continue; }
    println!("x is:{:d}", x);
  }
}

fn strings() {
  // &str - string slice
  // String literals are of the type &str
  let string = "Hello there.";
  println!("{}", string);
  // this string is statically allocated
  // The string binding is a reference to this statically allocated string. String slices have a fixed size, and cannot be mutated

  // String, in-memory string. This string is growable, and is also guaranteed to be UTF-8
  let mut s = "Hello".to_string();
  println!("{}", s);
  s.push_str(", world.");
  println!("{}", s);

  // String -> &str with as_slice()
  fn takes_slice(slice: &str) {
    println!("Got: {}", slice);
  }

  let s = "Hello".to_string();
  takes_slice(s.as_slice());

  // To compare a String to a constant string, prefer as_slice()
  fn compare(string: String) {
    if string.as_slice() == "Hello" {
      println!("yes");
    }
  }
  compare("Hello".to_string());

  // Converting a String to a &str is cheap, but converting the &str to a String involves allocating memory
}

fn vectors() {
  //Vec<T> (a 'vector')
  //[T, .. N] (an 'array')
  //&[T] (a 'slice')

  // Vectors are similar to Strings: they have a dynamic length, and they allocate enough memory to fit. You can create a vector with the vec! macro
  let nums = vec![1i, 2i, 3i];
  println!("nums is: {}", nums);

  let nums1 = vec!(1i, 2, 3);
  println!("nums1 is: {}", nums1);

  // You can create an array with just square brackets
  let fixed_nums = [1i, 2i, 3i];
  //println!("{}", fixed_nums); // compile error:  failed to find an implementation of trait core::fmt::Show for [int, .. 3]
  println!("fixed_nums[0] is:{}", fixed_nums[0]);
  // println!("fixed_nums[100] is:{}", fixed_nums[100]); // runtime error: index out of bounds

  let mut nums = vec![1i, 2, 3];
  nums.push(4i); //works
  println!("nums is: {}", nums);

  //let mut nums = [1i, 2, 3]; // [int, .. 3]
  //nums.push(4i);

  // get a slice from a vector by using the as_slice() method
  let vec = vec![1i, 2i, 3i];
  let slice = vec.as_slice();
  println!("slice is:{}", slice);

  // All three types implement an iter() method, which returns an iterator
  for i in vec.iter() {
    print!("{}", i);
  }
  println!("");

  // access a particular element of a vector, array, or slice by using subscript notation
  let names = ["itang", "tqibm"];
  println!("The second name is : {}", names[1]);
}

fn standard_input() {
  println!("Type something!");

  let input = io::stdin().read_line().ok().expect("Failed to read line");

  println!("{}", input);
}

fn pointers() {
  // References
  // At runtime, these are the same as a raw machine pointer
  let x = 5i;
  let y = &x;

  assert_eq!(5i, *y);
  assert_eq!(5i, *y);

  let z = y;
  assert_eq!(5i, *z);

  assert_eq!(5i, *y);

  fn add_one(x: &int) -> int { *x + 1 }

  assert_eq!(6, add_one(&5));

  let mut x = 5i;
  let y = &mut x; // x must also be mutable
  *y = 100;
  assert_eq!(100, *y);

  /*

  Being an owner affords you some privileges:

You control when that resource is deallocated.
You may lend that resource, immutably, to as many borrowers as you'd like.
You may lend that resource, mutably, to a single borrower.
But it also comes with some restrictions:

If someone is borrowing your resource (either mutably or immutably), you may not mutate the resource or mutably lend it to someone.
If someone is mutably borrowing your resource, you may not lend it out at all (mutably or immutably) or access it in any way.
*/

  // In Rust, the simplest way to allocate heap variables is using a box
  let x = box 5i;
  assert_eq!(5i, *x);


  let mut x = box 10i;
  {
    let y = &mut x;
    **y = 100i;
    //*y = box 100i;
  }
  assert_eq!(100i, *x);

  let x = Rc::new(5i);
  let y = x.clone();

  assert_eq!(5i, *x);
  assert_eq!(5i, *y);
}