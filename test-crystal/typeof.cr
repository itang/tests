require "./util"
include Util

a = 1
b = typeof(a)
assert_equal b, Int32

puts %(typeof(1, "a", 'a'):)
p typeof(1, "a", 'a')
assert_equal typeof([1, "a", 'a']), (Array(String | Int32 | Char))

hash = {} of Int32 => String
another_hash = typeof(hash).new #:: Hash(Int32, String)
that_hash = Hash(Int32, String).new
assert_equal another_hash.class, that_hash.class


#####################################################################################################
## @see http://crystal-lang.org/2015/08/24/its-a-typeof-magic.html
arr = Array(typeof(1, 'a', true)).build(3) do |buffer|
  buffer[0] = 1
  buffer[1] = 'a'
  buffer[2] = true
  3
end

assert arr == [1, 'a', true]

# Note that the NoReturn type is gone: the “expected” type of the last expression would be Int32 | NoReturn, that is, the union of the possible types of the method. However, NoReturn doesn’t have a tangible value, so mixing NoReturn with any type T basically gives you T back. Because, if the not_nil method succeeds (that is, it doesn’t raise), you will get an integer back, otherwise an exception will be bubbled through the stack.
def not_nil(exp)
  if exp.is_a?(Nil)
    raise "oops, nil"
  else
    exp
  end
end

class Array
  def compact
    # We create an array whose type is the type that results of invoking not_nil on the first element of the array. Note that the compiler doesn’t know what types are in each position in an array, so using 0, 1 or 123 would be the same.
    result = Array(typeof(not_nil(self[0]))).new
    each do |element|
      result << element unless element.is_a?(Nil)
    end
    result
  end
end

ary = [1, nil, 2, nil, 3]
puts typeof(ary)       #=> Array(Int32 | Nil)

compacted = ary.compact
puts compacted         #=> [1, 2, 3]
puts typeof(compacted) #=> Array(Int32)

def flatten_type(object)
  if object.is_a?(Array)
    flatten_type(object[0])
  else
    object
  end
end

puts typeof(flatten_type(1))                          #=> Int32
puts typeof(flatten_type([1, [2]]))                   #=> Int32
puts typeof(flatten_type([1, [2, ['a', 'b']]]))       #=> Int32 | Char
puts typeof(flatten_type([1, [2, [3, 'b']]]))         #=> Int32 | Char
