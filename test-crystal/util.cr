module Util
  extend self

  def assert(b, msg = "")
    raise "Assert failure! #{msg}" unless b
  end

  def assert_equal(a, b)
    if a != b
      assert(false, "#{a} != #{b}")
    end
  end

  def assert_not_equal(a, b)
    if a == b
      assert(false, "#{a} == #{b}")
    end
  end

  def assert_nil(a)
    if !a.nil?
      assert(false, "a is't nil")
    end
  end
end
