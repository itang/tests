require 'prey'

module Consumer
  def self.main
    c = Prey::Cluster.new
    t = c.thrift

    loop do
       d = t.get("access", 1, 0, 0).first
       puts d.data if d
    end

  end
end
