require "./util"

include Util

s = "Hello
     World!"

p s

puts "\"\t\\ \101 \u{41}"

s = "Hello " \
    "world, " \
    "no newlines"

assert (s == "Hello world, no newlines")

assert (s == "Hello \
  world, \
  no newlines")

s = %(hello ("world"))

assert (s == "hello (\"world\")")

## Interpolation

a = 1
b = 2
assert "sum = #{a + b}" == "sum = 3"

s = "1"

assert s.to_i == 1
assert 1.to_s == s

