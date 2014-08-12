require 'bunny'

conn = Bunny.new
conn.start

ch = conn.create_channel
q = ch.queue("new_task") # ch.queue("new_task", :durable => true)

# Fair dispatch
# don't dispatch a new message to a worker until it has processed and acknowledged the previous one
ch.prefetch(1)

begin
  q.subscribe(:ack => true, :block => true) do |delivery_info, properties, body|
    puts " [x] Received #{body}"
    # imitate some work
    sleep body.count(".").to_i
    puts " [x] Done"

    ch.ack(delivery_info.delivery_tag)
  end

rescue Interrupt => _
  conn.close
end