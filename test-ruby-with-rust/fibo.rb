require 'ffi'

module Fibo
  extend FFI::Library

  ffi_lib 'fibonacci/target/release/libfibonacci.so'
  attach_function :fibonacci, [:int], :int
end

puts Fibo.fibonacci(42)
