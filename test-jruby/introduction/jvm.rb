require 'java'

os = java.lang.System.get_property 'os.name'
home = java.lang.System.get_property 'java.home'
JRuntime = java.lang.Runtime
mem = JRuntime.get_runtime.free_memory
total = JRuntime.get_runtime.total_memory

puts "Running on #{os}"
puts "Java home is #{home}"
puts "#{mem} bytes available in JVM"
puts "total #{mem} bytes in JVM"
