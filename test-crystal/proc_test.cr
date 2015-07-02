require "./util"
include Util

p = ->{ 1 }
assert p.class == Proc(Int32)
#assert p.class == (-> Int32) ## expecting token '.', not ')'

puts p

pa = [] of -> Int32
pa << ->{puts "x";1} << ->{puts "y";2}
pa.each do |x|
  puts x.call
end

#args type must declare
pa = [] of Int32 -> String
pa << ->(x : Int32){ x.to_s } << ->(x : Int32) { (x+ 1).to_s }
pa.each do |x|
  puts x.call(100)
end

p = ->{}
puts p.class
assert p.class == Proc(Nil)

p = ->(x : Int32) { x.to_s }
assert p.class == Proc(Int32, String)
assert p.call(100) == "100"

p = ->(x : Int32, y : Int32) { x + y }
assert p.class == Proc(Int32, Int32, Int32)
puts p.class

p = Proc(Int32, String).new { |x| x.to_s }
assert p.class == Proc(Int32, String)

def f(i)
  i + 1
end

p = ->f(Int32)

puts p.class
