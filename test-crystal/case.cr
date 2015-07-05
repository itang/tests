a = "1"
case a
when "1", "2"
  puts "true: 1"
when "3"
  puts "bad"
else 
  puts "else"
end

case a
  when String
    puts "String"
  when Int32
    puts "Int32"
  else 
    puts "else"
end

num = 100
case num
  when .even?
    puts "even"
  when .odd?
    puts "odd"
end
