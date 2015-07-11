require "./util"
include Util

macro define_method(name, content)
  def {{name}}
    {{content}}
  end
end

define_method hello, 1
puts hello

macro define_method2(name, content)
  def {{name.id}}
    {{content}}
  end
end

define_method2 :world, 2
puts world
assert_equal world, 2

define_method2 :name, "itang"

assert name == "itang"

macro define_method3(name, content)
  def {{name.id}}
    {% if content == 1 %}
      "one"
    {%else %}
      {{content}}
    {% end %}
  end
end

define_method3 foo, 1
define_method3 bar, 2

assert_equal foo, "one"
assert_equal bar, 2

macro define_dummy_methods(names)
  {% for name, index in names %}
    def {{name.id}}
      {{index}}
    end
  {% end %}
end

puts "*" * 100
define_dummy_methods [m1, m2, m3]
puts m1, m2, m3

macro define_dummy_methods_hash(hash)
  {% for key, value in hash %}
    def {{key.id}}
      {{value}}
    end
  {% end %}
end
define_dummy_methods_hash({b1: 10, b2: 20})
puts b1, b2

macro println(*values)
   print {{*values}}, '\n'
end

println 1,2,3
