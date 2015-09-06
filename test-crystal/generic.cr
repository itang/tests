class Container(T, D)
  def id_value(t: T) : D?
    if t.responds_to? :id
        t.id as D?
    end
  end
end

class Entity(D)
  getter id
  def initialize(@id: D?)
  end
end

a = Container(Entity(Int32), Int32).new
puts a.id_value(Entity(Int32).new(100))
##output
# 100
