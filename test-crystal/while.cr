require "./util"
include Util

stop? = false
index = 1
while !stop?
  puts index
  index += 1
  if index > 10
    stop? = true
  end
end

a = 1
index = 0
while index < 10
  index += 1
  a = "String"
  a.length
end

p a.class

a = 2
while (a += 1) < 20
  if a == 10
    # goes to 'puts a'
    break
  end
end
puts a #=> 10

assert_equal a, 10

a = 1
while a < 5
  a += 1
  if a == 3
    next
  end
  puts a
end

index = 0
until index > 10
  puts index
  index += 1
end
assert_equal index, 11