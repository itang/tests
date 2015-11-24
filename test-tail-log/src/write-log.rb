log_path = File.expand_path "../../logs/access.log", __FILE__

puts log_path

users = ["itang", "tqibm", "live.tang", "tangqiong"]

f = File.new(log_path,  "w+")
loop do
  msg = "#{users[rand(users.size)]} #{Time.now}\n"
  f.write(msg)
  f.flush
  sleep(1)
end

f.close()
