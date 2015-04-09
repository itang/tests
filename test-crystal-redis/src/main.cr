require "redis"

Redis.open("127.0.0.1", 6379) do |client|
 10.times do 
    client.set("now", Time.new)
    p client.get("now")
    sleep(1)
  end
end
