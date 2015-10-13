 require "./line"

p1 = RustPoint::make_point(10, 10)
p2 = RustPoint::make_point(20, 20)

puts RustPoint::get_distance(p1, p2)