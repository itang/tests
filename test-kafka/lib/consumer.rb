require 'poseidon'

consumer = Poseidon::PartitionConsumer.new("my_test_consumer", "localhost", 9092,
                                            "topic1", 0, :earliest_offset)
loop do
  puts "consumer.next_offset: #{consumer.next_offset}"
  messages = consumer.fetch
  messages.each do |m|
    puts m.value
  end
end
