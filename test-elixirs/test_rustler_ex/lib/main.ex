defmodule MyApp.CLI do
  require Test.Fib

  def main(_args) do
    IO.puts("by rust: 1 + 1 = #{Test.Fib.add(1, 1)}")
  end
end
