require "bundler"
Bundler.require :test
require "prey"

root_path = Pathname(__FILE__).dirname.join('..').expand_path
Dir[root_path.join("spec/support/**/*.rb")].each { |f| require f }

host = ENV["KESTREL_HOST"] || "127.0.0.1"
port = ENV["KESTREL_MEMCACHE_PORT"] || 22134
server = Prey::Server.new({
  host: host,
  memcache_port: port,
})
server.stats

RSpec.configure do |config|
end

log_path = root_path.join("log")
log_path.mkpath
log_file = log_path.join("prey.log")
log_to = ENV.fetch("LOG_STDOUT", false) ? STDOUT : log_file
LOGGER = Logger.new(log_to)
