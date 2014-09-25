fn main() {
  // |...| { ... } syntax
  let add_one = |x| { 1i + x };

  println!("The 5 plus 1 is {}.", add_one(5));

  let x = 5i;
  let printer = || { println!("x is: {}", x); };
  printer();

  let mut x = 5i;

  let printer = || { 
    //x = 100i;
    println!("x is: {}", x); 
  };

  //x = 6i; // error: cannot assign to `x` because it is borrowed
  println!("x is: {}", x);
  printer();
  //x = 6i;   // error: cannot assign to `x` because it is borrowed


  //Procs
  // Procs have a big difference from closures: they may only be called once
  let x = 5i;
  let p = proc() { x * x };
  println!("{}", p());
  //println!("{}", p()); // `p` moved here because it has type `proc():Send -> int`, which is non-copyable (perhaps you meant to use clone()?)


  // Accepting closures as arguments
  fn twice(x: int, f: |int| -> int) -> int {
    f(x) + f(x)
  }

  let square = |x: int| { x * x };
  assert_eq!(50i, twice(5i, square));

  // A named function's name can be used wherever you'd use a closure
  fn square1(x: int) -> int { x * x }
  println!("{}", twice(10i, square1));
}
