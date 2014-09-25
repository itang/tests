fn main() {
  // parametric polymorphism
  // types or functions that have multiple forms ("poly" is multiple, "morph" is form) over a given parameter ("parametric").
  let x : Option<int> = Some(5i);
  let y : Option<f64> = Some(5.0f64);
  println!("{}", x);
  println!("{}", y);

  let x : Result<f64, String> = Ok(2.3f64);
  let y : Result<f64, String> = Err("There was an error.".to_string());

  println!("{}", x);
  println!("{}", y);

  fn inverse(x: f64) -> Result<f64, String> {
    if x == 0.0f64 {
      return Err("x cannot be zero!".to_string());
    }
    Ok(1.0f64 / x)
  }

  println!("{}", inverse(0.0));
  println!("{}", inverse(2.0));

  let x  = inverse(25.0f64);
  match x {
    Ok(x) => println!("the inverse of 2 is {}", x),
    Err(msg) => println!("Error: {}", msg),
  }
}
