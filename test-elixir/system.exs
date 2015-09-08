defmodule Util do 
  def system(str) do
    [head|tail] = String.split(str)
    args = tail |> Enum.filter &(&1 |> String.strip |> String.length == 0)
    System.cmd head, args
  end
end

{content, _code } = Util.system "ls -l"
IO.puts content