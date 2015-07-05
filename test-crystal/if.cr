require "./util"
include Util

a = [nil, false]
a.each do |x|
  assert !x
end

def true_of_false?(a : Int32)
  a % 2 == 0
end

a = 1
b = false
if true_of_false?(a)
  a = "hello"
else
  a = 100.0
end
p a.class
assert_equal a.class, Float64

def some_value(a: Int32)
  if a % 2 == 0
    "string"
  else
    100.0
  end
end

ret = some_value(100)
p ret.class
assert ret.class == String

some_condition = true

a = 1
if some_condition
  a = "hello"
else
  a = true
end
p a.class

puts "hello" if true

a = 1 > 2 ? 3 : 4
assert a == 4

a = if 1 > 2; 3 else 5 end
assert a == 5
puts a

a = if 1 > 2 3 else 5 end
assert a == 5
puts a

a = true ? nil : 100
if a
  puts a.abs
end

if a = true ? nil : 100
  puts a.abs
end

if a.is_a?(String)
  puts a.length
end

s = "Hello"
if s.responds_to?(:length)
  puts s.length
end

unless false
  puts "false"
else
  puts "true"
end

puts "hello" unless false