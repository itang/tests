require "bunny"
require 'time'

conn = Bunny.new(:hostname => 'localhost')
conn.start

ch   = conn.create_channel
q = ch.queue("new_task") # ch.queue("new_task", :durable => true)

100.times do |x|
  msg = "#{x}: #{ARGV.empty? ? "Hello World! #{Time.new}" : ARGV.join(" ") }"
  q.publish(msg, :persistent => true)
  puts " [x] Sent #{msg}"
  sleep(0.2)
end

sleep 1.0
conn.close