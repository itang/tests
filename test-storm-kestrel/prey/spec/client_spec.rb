require "helper"

describe Prey::Client do
  describe "#[]" do
    it "returns queue instance" do
      subject["prey"].should be_instance_of(Prey::Queue)
    end
  end
end
