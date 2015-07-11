require "./util"
include Util

struct Point
  property x, y

  def initialize(@x, @y)
  end
end

p = Point.new(1, 2)
assert_equal p.x, 1
assert_equal p.y, 2

puts p.class
assert p.is_a?(Point)
assert p.is_a?(Value)
##assert p.instance_of? Point

# A struct is mostly used for performance reasons to avoid lots of small memory allocations when passing small copies might be more efficient.
