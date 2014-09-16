fn main() {
  println!("Hello, {:s}!", "Rust");

  variable_bindings();

  _if();

  functions();

  comments();

  compound_data_types();
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
}