require "mysql"

module Logger
  extend self

  def info(msg)
    puts "INFO-#{Time.now}: #{msg}"
  end

  def error(msg)
    STDERR.puts "ERROR-#{Time.now}: #{msg}"
  end
end
include Logger

module Resources
  extend self
  def with_resource(res)
    begin
      yield(res)
    rescue e: Exception
      raise e
    ensure
      begin
        res.close()
        info("Close Resource")
      rescue e1: Exception
         error e1
      end unless res.nil?
    end
  end
end
include Resources


begin
  # MySQL.connect(host, user, password, database, port, socket, flags = 0)
  with_resource(MySQL.connect("127.0.0.1", "root", "root", "mydb", 3306_u16, nil)) do |conn|

    info(conn.query(%{SELECT 1}))

    rows = conn.query(%{select id, name from account})

    info(rows)
  end
rescue e: Exception
  error(e)
end
