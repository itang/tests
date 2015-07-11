require "./util"
include Util

class Person
  property name

  def self.new(name)
    puts "new"
    instance = Person.allocate
    instance.initialize(name)
    instance
  end

  def initialize(@name)
  end
end

a = Person.new("itang")
b = Person.new(1)

p "a #{a.name.class}"

p "b #{b.name.class}"

puts a.name
ret = a.name
if ret.is_a?(String)
  puts ret.length
end