require "./cr/*"

module Cr
  file = "../data.bat"
  slice = Slice(UInt8).new(1)

  in = File.new(file)
  in.read(slice)
  puts slice[0] # 254
  in.close()
end
