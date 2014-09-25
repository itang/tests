use shapes::HasArea1;

fn main() {
  struct Circle {
    x: f64,
    y: f64,
    radius: f64,
  }

  trait HasArea {
    fn area(&self) -> f64;
  }

  // impl Trait for Item, rather than just impl Item.
  impl HasArea for Circle {
    fn area (&self) -> f64 {
      std::f64::consts::PI * (self.radius * self.radius)
    }
  }

  fn print_area<T: HasArea> (shape: T) {
    println!("This shape has an area of {}", shape.area());
  }

  struct Square {
    x: f64,
    y: f64,
    side: f64,
  }

  impl HasArea for Square {
    fn area(&self) -> f64 {
      self.side * self.side
    }
  }

  impl HasArea for int {
    fn area(&self) -> f64 {
      println!("this is silly");

      *self as f64
    }
  }

  let c = Circle {
    x: 0.0f64,
    y: 0.0f64,
    radius: 1.0f64,
  };

  let s = Square {
    x: 0.0f64,
    y: 0.0f64,
    side: 1.0f64,
  };

  print_area(c);
  print_area(s);
  print_area(5i);

  //traits must be used in any scope where you wish to use the trait's method.
  let c = shapes::Circle {
    x: 0.0f64,
    y: 0.0f64,
    radius: 1.0f64,
  };

  println!("{}", c.area());
}

mod shapes {
  use std::f64::consts;

  pub trait HasArea1 {
    fn area(&self) -> f64;
  }

  pub struct Circle {
    pub x: f64,
    pub y: f64,
    pub radius: f64,
  }

  impl HasArea1 for Circle {
    fn area(&self) -> f64 {
      consts::PI * (self.radius * self.radius)
    }
  }
}