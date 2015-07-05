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

  def initialize(@name : String)
  end
end

a = Person.new("itang")
puts a.name
