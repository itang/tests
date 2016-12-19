defmodule MyApp.CLI do
  @moduledoc """
  fentan demo
  """

  def main(_args) do
    [{1, 10.0, 3}, {2, 10.0, 1}] |> fentan1(5.1) |> IO.inspect
    [{1, 10.0, 3}, {2, 10.0, 1}] |> fentan2(5.1) |> IO.inspect
    IO.puts ""

    [{1, 10.0, 3}, {2, 10.0, 4}, {3, 10.0, 2}] |> fentan1(5.0) |> IO.inspect
    [{1, 10.0, 3}, {2, 10.0, 4}, {3, 10.0, 2}] |> fentan2(5.0) |> IO.inspect
    IO.puts ""

    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan1(1.0) |> IO.inspect
    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan2(1.0) |> IO.inspect

    IO.puts ""

    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan1(0.1) |> IO.inspect
    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan2(0.1) |> IO.inspect

    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan1(0.01) |> IO.inspect
    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan2(0.01) |> IO.inspect

    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan1(0.05) |> IO.inspect
    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan2(0.05) |> IO.inspect

    IO.puts "\n以下对比能测试fenta1的问题"
    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}, {4, 10.0, 1}, {5, 10.0, 1}, {6, 10.0, 1}, {7, 10.0, 1}] |> fentan1(0.05) |> IO.inspect
    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}, {4, 10.0, 1}, {5, 10.0, 1}, {6, 10.0, 1}, {7, 10.0, 1}] |> fentan2(0.05) |> IO.inspect
  end

  @doc """
  存在累积误差放大问题
  """
  def fentan1(items, reduce_total) do
    items = Enum.map(items, fn {id, price, amount} ->
      {id, to_cent_round(price), amount}
    end)

    total = items
            |> Enum.map(fn {_, price, amount} -> price * amount end)
            |> Enum.sum

    _fentan1(items, total, to_cent_round(reduce_total), 0, [])
  end

  defp _fentan1([{id, _, _}], _total, reduce_total, acc, ret), do: [{id, reduce_total - acc} | ret]
  defp _fentan1([{id, price, amount} | tail], total, reduce_total, acc, ret) do
    s = price * amount
    r = s / total
    v = to_round(r * reduce_total)

    _fentan1(tail, total, reduce_total, acc + v, [{id, v} | ret])
  end


  @doc """
  逐步分摊法
  """
  def fentan2(items, reduce_total) do
    items = Enum.map(items, fn {id, price, amount} ->
      {id, to_cent_round(price), amount}
    end)

    total = items
            |> Enum.map(fn {_, price, amount} -> price * amount end)
            |> Enum.sum

    _fentan2(items, total, to_cent_round(reduce_total), [])
  end

  defp _fentan2([{id, _, _}], _total, reduce_total, ret), do: [{id, reduce_total} | ret]
  defp _fentan2([{id, price, amount} | tail], total, reduce_total, ret) do
    s = price * amount
    r = s / total
    v = to_round(r * reduce_total)

    _fentan2(tail, total - s , reduce_total - v, [{id, v} | ret])
  end

  defp to_cent_round(v), do: round(v * 100.0) # v |> (fn x -> x * 100.0 end).() |> Float.round |> round

  defp to_round(v), do: round(v)

end
