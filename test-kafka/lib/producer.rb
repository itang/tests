require 'poseidon'

producer = Poseidon::Producer.new(["localhost:9092"], "my_test_producer")

messages = []
messages << Poseidon::MessageToSend.new("topic1", "value1")
messages << Poseidon::MessageToSend.new("topic2", "value2")
producer.send_messages(messages)


(0..10000).each do |i|
  producer.send_messages [Poseidon::MessageToSend.new("topic1", "message-#{i}-#{Time.now}")]
  sleep(0.5)
end
