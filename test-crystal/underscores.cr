a, b = [1, 2, 3]
puts a, b 

a, b , _ = [1, 2, 3]
puts a, b 

def f (a, _b) # NO f(a, _)
  puts a
end

f("hello,", "world")

["a", "b", "c"].each do
  puts "hello"
end

["a", "b", "c"].each do |_|
  puts "hello"
end

a = {100, 200}
## {a1, b1} = {100, 200} # NO 
puts a[0], a[1]