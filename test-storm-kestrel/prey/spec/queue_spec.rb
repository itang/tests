require "helper"

describe Prey::Queue do
  let(:cluster) { Prey::Cluster.new }
  let(:thrift) { cluster.thrift }
  let(:queue_name) { "prey" }
  let(:expiration_msec) { 0 }
  let(:max_items) { 1 }
  let(:timeout) { 0 }
  let(:abort_timeout) { 0 }

  subject { described_class.new(cluster, queue_name)}

  before { thrift.flush_queue(queue_name) }

  describe "#get" do
    it "returns empty array if no items" do
      subject.get.should eq([])
    end

    it "returns item if found" do
      item = "1"
      items = [item]
      thrift.put(queue_name, items, expiration_msec)
      received_item = subject.get.first
      received_item.should_not be_nil
      received_item.data.should eq(item)
    end
  end

  describe "#put" do
    context "with single item" do
      it "puts item onto queue" do
        item = "1"
        subject.put(item)
        received_item = thrift.get(queue_name, max_items, timeout, abort_timeout).first
        received_item.should_not be_nil
        received_item.data.should eq(item)
      end
    end

    context "with many items" do
      it "puts item onto queue" do
        items = ["1", "2", "3"]
        subject.put(items)
        received_items = thrift.get(queue_name, items.size, timeout, abort_timeout)
        received_items.map(&:data).sort.should eq(items.sort)
      end
    end

    context "with expiration" do
      it "works" do
        item = "1"
        subject.put(item, 1)
        sleep 0.001 # ensure item expires
        received_item = thrift.get(queue_name, max_items, timeout, abort_timeout).first
        received_item.should be_nil
      end
    end
  end

  describe "#confirm" do
    it "confirms items" do
      abort_timeout = 5
      items = ["1", "2", "3"]
      thrift.put(queue_name, items, expiration_msec)
      received_items = thrift.get(queue_name, items.size, timeout, abort_timeout)
      subject.confirm(received_items[0, 2])
      sleep 0.5
      received_items = thrift.get(queue_name, items.size, timeout, abort_timeout)
      received_items.map(&:data).sort.should eq(items[2, 3])
    end
  end

  describe "#abort" do
    it "aborts items" do
      abort_timeout = 10_000
      items = ["1", "2", "3"]
      thrift.put(queue_name, items, expiration_msec)
      received_items = thrift.get(queue_name, items.size, timeout, abort_timeout)
      subject.abort(received_items[0, 2])
      received_items = thrift.get(queue_name, items.size, timeout, abort_timeout)
      received_items.map(&:data).sort.should eq(items[0, 2])
    end
  end

  describe "#flush" do
    it "flushes queue" do
      items = ["1", "2", "3"]
      thrift.put(queue_name, items, expiration_msec)
      subject.flush
      received_items = thrift.get(queue_name, items.size, timeout, abort_timeout)
      received_items.should eq([])
    end
  end

  describe "#size" do
    it "returns size of queue" do
      items = ["1", "2", "3"]
      thrift.put(queue_name, items, expiration_msec)
      subject.size.should be(3)
    end
  end
end
