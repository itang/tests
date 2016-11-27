defmodule RustlerBench do
  use Benchfella

  bench "native add" do
    Test.Fib.fib(42)
  end

  bench "local add" do
    fib(42)
  end

  defp fib(n) do
    if n < 2 do
      n
    else
      fib(n-2) + fib(n-1)
    end
  end
end
