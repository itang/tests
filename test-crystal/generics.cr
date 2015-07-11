require "./util"
include Util

class MyBox(T)
    def initialize(@value: T)
  end

  def value
    @value
  end
end

#The above now works, because MyBox is now not a single type, but a family of types identified with a T type: MyBox(Int32) is a different type than MyBox(String)
MyBox(Int32).new(1)

box = MyBox(String).new("hello")
box.value.length #=> 5

assert_equal box.value.length, 5
