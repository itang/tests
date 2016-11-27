defmodule MyApp.CLI do
  require Test.Fib

  def main(_args) do
    IO.puts("by rust: fab(42) = #{Test.Fib.fab(42)}")
  end
end
