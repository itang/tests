defmodule A do 
  def f(a, options) do
    IO.puts "#{inspect options}"
  end

  defmacro unless(cond, options) do
    quote do
      if(!unquote(cond), unquote(options))
    end
  end
end
