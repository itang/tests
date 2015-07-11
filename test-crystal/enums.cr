require "./util"
include Util

#An enum is a set of integer values, where each value has an associated name
enum Color
  Red
  Green
  Blue
end

assert_equal Color::Red.value, 0

enum Color2
  Red         # 0
  Green       # 1
  Blue   = 5  # overwritten to 5
  Yellow      # 6 (5 + 1)
end

assert_equal Color2::Yellow.value, 6

enum Color3 : UInt8
  Red
  Green
  Blue
end

assert Color::Red.value.class, UInt8

# All enums inherit from Enum
assert Color::Red.is_a?(Enum)

# Flags enums
@[Flags]
enum IOMode
  Read #1
  Write # 2
  Async # 4
end

#Implicit constants, None and All, are automatically added to these enums, where None has the value 0 and All has the "or"ed value of all constants.
assert_equal IOMode::Read.value, 1
assert_equal IOMode::None.value, 0
assert_equal IOMode::All.value, 7

puts(Color::Red)                    # prints "Red"
puts(IOMode::Write | IOMode::Async) # prints "Write, Async"

puts (IOMode::Write | IOMode::Async).class
puts (IOMode::Write | IOMode::Async).value

puts ((IOMode::Write | IOMode::Async) & IOMode::Write ).class
puts ((IOMode::Write | IOMode::Async) & IOMode::Write ).value
assert_equal ((IOMode::Write | IOMode::Async) & IOMode::Write ).value, IOMode::Write.value

conf = (IOMode::Write | IOMode::Async)
assert_equal conf & IOMode::Write, IOMode::Write
assert_equal conf & IOMode::Read, IOMode::None

def has_conf(mode: IOMode, that: IOMode)
  (mode & that) == that
end
assert has_conf(IOMode::All, IOMode::Async)
assert has_conf(IOMode::None, IOMode::None)
assert has_conf(IOMode::All, IOMode::All)
