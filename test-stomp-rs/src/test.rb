##  gem install stomp

require 'rubygems'
require 'stomp'

client = Stomp::Client.new("guest", "guest", "localhost", 61613)

topic = "/topic/messages"

10000.times do |i|
  client.publish topic, "Test Message number #{i} (from ruby)"
end

client.publish topic, "All Done! (from ruby)"
