# encoding: utf-8

require "bunny"
require 'time'

# connnect to RabbitMQ server
conn = Bunny.new(:hostname => 'localhost')
conn.start

# create a channel
ch   = conn.create_channel

# declare a queue for us to send to
# it will only be created if it doesn't exist already
q = ch.queue("hello")
100.times do |i|
  ch.default_exchange.publish("#{i} Hello World! #{Time.new}",  :routing_key => q.name)
  puts "#{i} [x] Sent 'Hello World!'"
  sleep 1
end


# close the connnection
conn.close
