class A 

  #getter :names
  getter names

  def initialize(@names = [] of Int)
  end

  def [](i)
    @names[i]
  end

  def []=(i,v)
    if @names.size() -1 >= i
      @names[i] = v
    else
      @names << v
    end
  end

  #def names
   #@names
  #end
end

a = A.new()

a.names << 100
puts a[0]

a[1] = 10000

a[100] = 100000

a.names.each do |v|
  puts v 
end


#

if nil 
  puts "nil -> true"
else 
  puts "nil -> false"
end

if false
  puts "false -> true?"
else 
  puts "false -> false"
end

if ""
  puts "empty -> true"
else 
  puts "empty -> false"
end


var = 100

if var < 10
  puts "< 10"
elsif var < 50
  puts "< 50"
else 
  puts "var >= 50"
end

puts  3 >2 ? "3 > 2" : "3 <= 2"

if a = "hello,world"
  puts "'#{a}'.length: #{a.length}"
end

puts "a".is_a?(String)
puts 1111.is_a?(Number)
puts 1111.is_a?(Int)

puts "a".responds_to?(:length)
puts "a".responds_to?(:size)

puts 1.responds_to?(:abs)
puts 1.responds_to?(:length)

var = 10
while var >= 0
  puts var
  var -= 1
end

var = 0
until var == 10
  puts var
  var += 1
end

class Person
  getter age
  property name

  def initialize(@age)
    @name = "itang"
  end

  def become_older(v : Int32)
   @age += v
  end

  def become_older(s : String)
     become_older(s.to_i)
  end

  def become_older
    @age += yield @age
  end

  def to_s
    "Person(age:#{age})"
  end

  def ==(that: self)
     that.age == age
  end

  def ==(that)
    false
  end

end

p = Person.new 10
puts p.name
p.name = "tqibm"
puts p.name

p.become_older 10
puts p.to_s

p.become_older "20"
puts p.to_s

puts p.become_older {|x| x + 100 }

puts p.become_older {|x| x + 1000 }

p2 = Person.new 20
p3 = Person.new 20
puts p == p2
puts p2 == p3
puts p == ""

class A
  protected def a
    puts "a"
  end
  private def b
    puts "b"
  end
end

class B < A
 def c 
    a
    b
  end
end

a = A.new
#puts a.a
#puts a.b
b = B.new
b.c


