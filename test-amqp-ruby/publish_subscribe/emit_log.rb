#!/usr/bin/env ruby

# encoding: utf-8
require 'bunny'
require 'time'

conn = Bunny.new
conn.start

ch = conn.create_channel
x  = ch.fanout("logs")

100.times do |i|
  msg = i.to_s + " " + (ARGV.empty? ? "Hello World #{Time.now.strftime "%Y-%m-%d %H:%M:%S %L %z" }" : ARGV.join(" "))
  x.publish msg
  puts " [x] Sent #{msg}"
  sleep(0.2)
end

conn.close