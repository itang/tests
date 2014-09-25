fn main() {
  struct Circle {
    x: f64,
    y: f64,
    radius: f64,
  }

  impl Circle {
    // There are three variants: self, &self, and &mut self
    fn area(&self) -> f64 {
      std::f64::consts::PI * (self.radius * self.radius)
    }

    //  define methods that do not take a self parameter
    fn new(x: f64, y: f64, radius: f64) -> Circle {
      Circle { x: x, y: y, radius: radius, }
    }
  }

  let c = Circle { x :0.0, y: 0.0, radius: 2.0 };
  println!("{}", c.area());

  let d = Circle::new(0.0, 0.0, 2.0);
  println!("{}", d.area());
}
