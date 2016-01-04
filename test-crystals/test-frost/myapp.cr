require "http/server"
require "frost/server/handlers/log_handler"
require "frost/server/handlers/public_file_handler"
require "./config/bootstrap"

# FIXME: ?
class Zlib::Deflate
  def close; end
end

begin
  port = ENV.fetch("PORT", "9292").to_i

  handlers = [
    Frost::Server::LogHandler.new,
    HTTP::DeflateHandler.new,
    Frost::Server::PublicFileHandler.new(File.join(Frost.root, "public"))
  ]
  dispatcher = Myapp::Dispatcher.new

  server = HTTP::Server.new("0.0.0.0", port, handlers) do |request|
    dispatcher.call(request)
  end

  puts "Listening on http://0.0.0.0:#{ port }"
  server.listen
end
