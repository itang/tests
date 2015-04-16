require "./util"

include Util

class Person
  getter name

  def initialize(@name)
  end

  def to_s
    "Person(name= #{name}"
  end
end

person = Person.new("itang")

p person
puts person

p "object_id: #{person.object_id}"

ptr = Pointer(Person).new(person.object_id)
p "ptr: #{ptr}"

person_new = ptr as Person

assert person.name == person_new.name
assert person.object_id == person_new.object_id
