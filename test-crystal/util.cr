module Util
  extend self

  def assert(b, msg = "")
    raise "Assert failure! #{msg}" unless b
  end

  def assert_equal(a, b, message="")
    if a != b
      assert(false, "#{a} != #{b}")
    end
  end
end
