require "./util"
include Util

puts "Hello, World"

puts ":hello.to_s -> #{:hello.to_s}, typeof(:hello): #{typeof(:hello)}"

assert :hello.to_s == "hello"

puts typeof(typeof(:hello))
assert :hello.class == typeof(:hello)
assert :hello.class == Symbol

class String
  def hello
    "hello, " + self
  end
end

puts "world".hello.tap {|x| puts x}.class
