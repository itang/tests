require 'redis'
require 'csv'

redis = Redis.new(host: "c1",port: 6379)

redis.keys("*")
     .sort
     .map { |x| [x, redis.type(x)] }
     .each do |x|
       k, t = x
       printf("%-30s: %s\n", k, t)
       redis.hkeys(k).each {|k| printf("%+40s\n", k) } if (t == 'hash' && k =~ /rtb\.rt/)
     end



CSV.open("tredis.csv", "wb") do |csv|
  redis.keys("*")
      .sort
      .each { |x| csv << [x, redis.type(x)] }
end
