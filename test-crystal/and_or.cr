require "./util"
include Util

a = 1
b = "aaa"

assert (a && b)
assert (a && b) == b

b = nil
assert !(a && b)
assert_nil a && b

b = "some"
a = nil
assert_nil a && b

assert_equal a || b, b

assert_equal b || a,  b