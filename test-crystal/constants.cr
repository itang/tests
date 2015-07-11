require "./util"
include Util

PI = 3.14

assert PI.class == Float64

module Earch
  RADIUS = 6_371_000
end

assert_equal Earch::RADIUS.class, Int32

## Compile error
#Earch.RADIUS

C1 = begin
  a = 100
  puts a
  a
end

assert_equal C1, 100

#If a constant is not used, its initializer is never included in the final executable
C2 = begin
  b = 100
  assert false
end
