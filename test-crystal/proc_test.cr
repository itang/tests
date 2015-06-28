require "./util"
include Util

p = ->{ 1 }
assert p.class == Proc(Int32)

p = ->{}
puts p.class
assert p.class == Proc(Nil)

p = ->(x : Int32) { x.to_s }
assert p.class == Proc(Int32, String)
assert p.call(100) == "100"

p = ->(x : Int32, y : Int32) { x + y }
assert p.class == Proc(Int32, Int32, Int32)

p = Proc(Int32, String).new { |x| x.to_s }
assert p.class == Proc(Int32, String)

def f(i)
  i + 1
end

p = ->f(Int32)

puts p.class
