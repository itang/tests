require "./util"

include Util

def wrap_name(name)
  unless name
    return ""
  end

  ret = [] of String
  curr = ""
  name.each_char do |x|
    if x >= 'A' && x <= 'Z'
      ret << curr if curr != ""
      curr = x.to_s.downcase
    else
      curr = curr + x
    end
  end
  ret << curr if curr != ""

  ret.join("_")
end

abstract class A
  macro inherited
    def table_name
      wrap_name("{{@type.name.id}}")
    end
  end
end

puts wrap_name("Hello")
puts wrap_name("ABC")
puts wrap_name("")
puts wrap_name(nil)
puts wrap_name("HelloWorldNiHaoA")

class B < A
end

class ProjectUser < A
end

b = B.new
puts b.table_name
puts ProjectUser.new.table_name
