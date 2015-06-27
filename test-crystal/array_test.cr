require "./util"

include Util

a = [1,2,3]

puts a.class
assert a.is_a? Array
assert a.class == Array(Int32)

b = [1, "Hello", 'x']

p b.class
assert b.class == Array((Int32 | String | Char))

c = [] of Int32
d = Array(Int32).new

assert c.class == d.class
assert c == d

f = [] of Int32
puts f.empty?
assert f.empty?

f << 10
f << 100
assert f == [10, 100]

class A
  property items

  # an argless new method
  def self.new
    instance = A.allocate
    instance.initialize()
    instance
  end

  def initialize(@items = [] of Int32)
  end

  a << method
  def << (i)
    @items << i
  end
end

a = A {1,2,3}
puts a.items == [1,2,3]
