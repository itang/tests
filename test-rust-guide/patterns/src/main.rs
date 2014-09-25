fn main() {
  let x = 1i;

  match x {
    1 => println!("one"),
    2 => println!("two"),
    _ => println!("anything"),
  }

  // match multiple patterns with |
  match x {
    1 | 2 => println!("one or two"),
    _ => println!("anything"),
  }

  //  match a range of values with ..
  match x {
    1 .. 5 => println!("one through five"),
    _ => println!("anything"),
  }

  // bind the value to a name with @
  match x {
    x @ 1 .. 5 => println!("got {}", x),
    _ => println!("anything"),
  }

  enum OptionalInt {
    Value(int),
    Missing,
  }

  // .. to ignore the value in the variant
  let x = Value(5i);
  match x {
    Value(..) => println!("Got an int!"),
    Missing => println!("No such luck."),
  }

  // introduce match guards with if
  match x {
    Value(x) if x > 5 => println!("Got an int bigger than five."),
    Value(..) => println!("Got an int!"),
    Missing => println!("No such luck."),
  }

  // matching on a pointer, you can use the same syntax as you declared it with. First, &
  let x = &5i;
  match x {
    &x => println!("Got a value: {}", x),
  }

  // to get a reference, use the ref keyword
  let x = 5i;

  match x {
    // the x inside the match has the type &int
    ref x => println!("Got a reference to {}", x),
  }

  // a mutable reference, ref mut will work in the same way:
  let mut x = 5i;
  match x {
    ref mut x => println!("Got a mutable reference to {}", x),
  }

  // If you have a struct, you can destructure it inside of a pattern
  struct Point {
    x: int,
    y: int,
  }

  let origin = Point { x: 0i, y: 0i };
  match origin {
    Point { x: x, y: y } => println!("({},{})", x, y),
  }

  match origin {
    Point { x: x, .. } => println!("x is {}", x),
  }

  match origin {
    Point {x : ref x, .. } => println!("x is {}", x),
  }
}
