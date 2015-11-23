f = File.new("access.log",  "w+")
users = ["itang", "tqibm", "live.tang", "tangqiong"]

loop do
  msg = "#{users[rand(users.size)]} #{Time.now}\n"
  f.write(msg)
  f.flush
  sleep(1)
end

f.close()
