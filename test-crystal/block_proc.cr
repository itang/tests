require "./util"
include Util

def twice
  yield
  yield
end

twice do
  puts "Hello!"
end

#To define a method that receives a block, simply use yield inside it and the compiler will know. You can make this more evident by declaring a dummy block argument, indicated as a last argument prefixed with ampersand (&)
def three(&block)
  yield
  yield
  yield
end

three do
  puts "World"
end

three { puts Time.now }

def yield_now
  yield Time.now
end

yield_now do |x|
  puts x.to_s "%Y-%m-%d %H:%M:%S"
end

yield_now { |x| puts x.to_s "%Y-%m-%d" }

def many
  yield 1, 2, 3
end

many do |x, y, z|
  puts x + y + z
end

many do |x, y|
  puts x + y
end

def less
  yield
  yield
end

less do |i|
  puts i.inspect # nil
end
