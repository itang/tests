require "./test_b/*"

require "benchmark"

module TestB
  # TODO Put your code here
end

arr = Array.new(1000, 1)

Benchmark.ips do |x|
  x.report("Array#[]") { arr[500] }
  x.report("Array#[]?") { arr[500]? }
end
