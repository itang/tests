require "helper"
require "prey/server"

describe Prey::Server do
  let(:thrift) { Prey::Cluster.new.thrift }
  let(:queue_name) { "prey" }

  before(:each) { thrift.flush_queue(queue_name) }

  describe "#stats" do
    it "returns hash" do
      stats = subject.stats
      stats.should be_instance_of(Hash)
      stats["time"].should be_instance_of(Fixnum)
      stats["cmd_get"].should be_instance_of(Fixnum)
      stats["queue_creates"].should be_instance_of(Fixnum)
      stats["version"].should be_instance_of(String)
      stats["reserved_memory_ratio"].should be_instance_of(Float)
    end
  end

  describe "#queue_size" do
    it "returns size" do
      thrift.put(queue_name, [""], 0)
      thrift.put(queue_name, [""], 0)
      subject.queue_size(queue_name).should be(2)
    end

    it "returns 0 for down server" do
      server = described_class.new(memcache_port: 9999)
      server.queue_size(queue_name).should be(0)
    end
  end
end
