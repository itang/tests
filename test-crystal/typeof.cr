require "./util"
include Util

a = 1
b = typeof(a)
assert_equal b, Int32

p typeof(1, "a", 'a')
assert_equal typeof([1, "a", 'a']), (Array(String | Int32 | Char))

hash = {} of Int32 => String
another_hash = typeof(hash).new #:: Hash(Int32, String)
that_hash = Hash(Int32, String).new
assert_equal another_hash.class, that_hash.class
