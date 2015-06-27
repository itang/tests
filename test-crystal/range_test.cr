require "./util"
include Util

var1 = 1..10
var2 = 1...10
puts "#{var1.class}, #{var2.class}"
assert var1.class == Range(Int32, Int32)
assert var2.class == Range(Int32, Int32)
p var1
p var2
a = var1.to_a
p a
a = var2.to_a
p a

var1.each {|x| puts x }
puts var1.map {|x| x + 1}.tap {|x| puts x.class }
assert (var1.map {|x| x + 1}).class == Array(Int32)

p var1.select {|x| x % 2 == 0}.tap {|x| puts x.class}

(0...var1.to_a.length).each do |x|
  puts x
end
