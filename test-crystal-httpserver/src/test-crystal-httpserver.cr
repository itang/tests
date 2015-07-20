require "http/server"
require "./test-crystal-httpserver/*"

module Test::Crystal::Httpserver
  # TODO Put your code here
end

server = HTTP::Server.new(8080) do |request|
  HTTP::Response.ok "text/plain", "Hello world!"
end

puts "Listening on http://0.0.0.0:8080"
server.listen
