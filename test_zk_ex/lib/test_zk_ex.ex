defmodule TestZkEx do
  alias Zookeeper.Client, as: ZK

  def main() do
    {:ok, pid} = ZK.start_link
    tree pid, "/"
  end

  def tree(pid, dir, level \\ 0) do
    path = repeat(" ", level) <> dir

    case ZK.get(pid, dir) do
       {:ok, {data, _}} -> IO.puts "#{path} [#{data}"
        _ -> IO.puts path
    end

    case ZK.get_children(pid, dir) do
      {:ok, children} -> children |> Enum.each &(tree(pid, (if dir == "/", do:  dir <> &1, else: dir <> "/" <> &1), level + 1))
      _ ->
    end
  end

  defp repeat(x, n) do
    # 0..n |> Enum.map(fn _ -> x end) |> Enum.join("")
    Stream.cycle([x]) |> Enum.take(n) |> Enum.join("")
  end
end

TestZkEx.main()

# {:ok, path} = Zookeeper.Client.create(pid, "/testing", "some data")
# {:ok, {data, stat}} = Zookeeper.Client.get(pid, path, self())
# {:ok, stat} = Zookeeper.Client.set(pid, path, "some new data")
# receive do
#   {Zookeeper.Client, ^path, :data} -> IO.puts("data changed in #{path}")
# end
