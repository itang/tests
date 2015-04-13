module Util
  extend self

  def assert(b)
    raise "Assert failure!"
  end

  def assert(b, msg = "")
    raise "Assert failure! #{msg}" unless b
  end
end
