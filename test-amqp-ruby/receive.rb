# encoding: utf-8

require 'bunny'

conn = Bunny.new
conn.start

ch = conn.create_channel
q = ch.queue("hello")

puts " [*] Waiting for messages in #{q.name}. To exit press CTRL +C"
count = 0
q.subscribe(:block => true) do |delivery_info, properties, body |
  puts " #{count} [x] Received #{body}"
  # canel the consumer to exit
  count = count + 1
  sleep 2
  delivery_info.consumer.cancel if count > 100
end 