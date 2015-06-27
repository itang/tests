require "./util"

include Util

m1 = {1 => 2, 3 => 4}
assert m1.class == Hash(Int32, Int32)

m2 = { "name" => 2, 1 => "hello" }
assert typeof(m2) == Hash((String | Int32), Int32 | String)

m1 = {} of String => Int32
m2 = Hash(String, Int32).new
assert m1.class == m2.class == Hash(String, Int32)
assert m1.empty? && m2.empty?

m1 = {name: "itang", age: 19}
assert m1.class == Hash(Symbol, String | Int32)

m2 = {"name": "itang", "age": 18}
assert m2.class == Hash(String, String | Int32)

assert m2["name"] == "itang"
assert m2["age"] == 18

begin
  assert m2["bad"] == nil
rescue ex
  puts ex.message
end
assert m2["bad"]? == nil

m2["bad"] = "bad"
assert m2["bad"] == "bad"

## Hash-like types
class MyType
  property hash

  def self.new
    instance = MyType.allocate
    instance.initialize()
    instance
  end
  def initialize(@hash = Hash(String, String ).new)
  end

  def []=(key, value)
    @hash[key] = value
  end

  def to_s
    puts "to_s"
    @hash.to_s
  end
end

#Segmentation fault (core dumped)
#Program terminated abnormally with error code: 35584
puts MyType{"foo": "bar"}.to_s
puts "here"
puts MyType{"foo": "bar"}.class

a = MyType{"foo": "bar"}
assert a.hash == {"foo": "bar"}

b = {age: 18, name: "itang"}
b.each do |k, v|
  puts "#{k}: #{v}"
end

p b.map do |k, v|
  [k,v]
end
