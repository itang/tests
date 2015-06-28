require "./util"
include Util

t = {1, "2", true}
assert t.class == Tuple(Int32, String, Bool)

assert t[0] == 1
assert t[1] == "2"
assert t[2] == true
assert t.length == 3
assert t[3]? == nil
assert t.first? == 1
assert t.last? == true

## Error compile: index out of bounds for tuple {Int32, String, Bool}
#assert t[3] == nil
t.each do |x|
  puts x
end

ret = t.map do |x|
  x.to_s
end

p ret
assert ret.class == Tuple(String, String, String)

t = Tuple.new
assert t.empty?
