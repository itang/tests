require "./util"
include Util

#Assignment is done with the equal (=) character.
local = 1

class A
  property instance

  @@class_v = 3
  @instance = 2

  def self.class_v
    @@class_v
  end
end

a = A.new
puts a.instance
a.instance = 100
assert_equal a.instance, 100

puts A.class_v
assert_equal A.class_v, 3

$global = 4
assert_equal $global, 4

local += 1 # local = local + 1
assert_equal local, 2

local ||= 2 # save as : local || (local = 1)

class Person
 @names = [] of String

 def name=(name)
   @name = name
 end

 def name
   @name
 end

 def []=(index, value)
   @names[index] = value
 end

 def [](start, e)
   puts start, e
 end
end

person = Person.new
person.name=("John")
person.name = "John1"
assert_equal person.name, "John1"
