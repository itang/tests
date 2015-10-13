#[derive(Eq, PartialEq, Debug, Copy, Clone)]
pub struct Point {
  x: i32,
  y: i32,
}

#[derive(Eq, PartialEq, Debug, Copy, Clone)]
pub struct Line {
    p1: Point,
    p2: Point,
}

impl Line {
  pub fn length(&self) -> f64 {
    let xdiff = self.p1.x - self.p2.x;
    let ydiff = self.p1.y - self.p2.y;
    ((xdiff.pow(2) + ydiff.pow(2)) as f64).sqrt()
  }
}

#[no_mangle]
pub extern "C" fn make_point(x: i32, y: i32) -> Box<Point> {
  Box::new(Point { x: x, y: y })
}

#[no_mangle]
pub extern "C" fn get_distance(p1: &Point, p2: &Point) -> f64 {
    Line { p1: *p1, p2: *p2 }.length()
}
