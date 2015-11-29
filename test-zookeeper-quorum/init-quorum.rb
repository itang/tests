#################################################################
##
class Server
  @@servers = []
  attr_reader :id, :host, :port0, :port1, :port2

  private def initialize(id, host, port0, port1, port2)
    @id = id
    @host = host
    @port0 = port0
    @port1 = port1
    @port2 = port2

    @@servers << self
  end

  def self.newServer(host, start_port0, start_port1, start_port2)
    index = @@servers.size + 1
    new(index, host, start_port0 + index - 1, start_port1 + index, start_port2 + index)
  end

  def get_config
    sl = @@servers.map {|x| "server.#{x.id}=#{x.host}:#{x.port1}:#{x.port2}" }.join("\n")

    conf_content = <<-eos
tickTime=2000
dataDir=./data/#{id}
clientPort=#{port0}
initLimit=5
syncLimit=2
#{sl}
    eos
    conf_content
  end

  def self.servers
    @@servers
  end

  def write_myid
    dd = "data/#{id}"
    system "mkdir -p #{dd}"
    File.write dd + "/myid", id
  end

  def config_path
    "config/#{id}"
  end

  def config_file
    "#{config_path}/zoo.cfg"
  end

  def write_config
    system "mkdir -p #{config_path}"
    File.write config_file, get_config
  end

  def write_run_sh
    content = <<-eos
#!/bin/bash

# 前台启动
zkServer.sh --config #{config_path} start-foreground
    eos
    File.write "run_#{id}.sh", content
  end

  def write_log4j
    system "cp log4j.properties #{config_path}"
  end
end

###########################################################################################
if $0 == __FILE__

  # init servers.
  3.times {|x| Server.newServer("localhost", 2181, 2888, 3888)}

  # generate demo quorum servers.
  Server.servers.each do |x|
    x.write_myid
    x.write_config
    x.write_run_sh
    x.write_log4j

    system 'chmod +x *.sh'
    system 'tree'
  end

end
