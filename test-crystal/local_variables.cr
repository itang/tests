require "./util"
include Util

# Local variables start with lowercase letters. They are declared when you first assign them a value.
name = "Crystal"
age = 1

puts name, age

assert name.class == String

#their type is inferred from their usage, not only from their initializer
name = ["Crystal"]
assert name.class == Array(String)
