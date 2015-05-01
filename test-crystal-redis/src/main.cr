require "redis"
require "crtang"

include Crtang

Redis.open("127.0.0.1", 6379) do |client|
  Crtang.time do
    10.times do
      client.set("now", Time.new)
      p client.get("now")
      sleep(0.2)
    end
  end

  time do
    (1..100000).each do |x|
      key = "hello.#{x}"
      client.set key, x
      #p client.get key
    end
  end
end
