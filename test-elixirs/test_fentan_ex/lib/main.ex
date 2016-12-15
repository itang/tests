defmodule MyApp.CLI do
  @moduledoc """
  fentan demo
  """

  def main(_args) do
    [{1, 10.0, 3}, {2, 10.0, 1}] |> fentan(5.1) |> IO.inspect
    [{1, 10.0, 3}, {2, 10.0, 4}, {3, 10.0, 2}] |> fentan(5.0) |> IO.inspect
    [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan(1.0) |> IO.inspect
  end

  def fentan(items, reduce_total) do
    items = Enum.map(items, fn {id, price, amount} ->
      {id, to_cent_round(price), amount}
    end)

    total = items
            |> Enum.map(fn {_, price, amount} -> price * amount end)
            |> Enum.sum

    _fentan(items, to_cent_round(total), to_cent_round(reduce_total), 0, [])
  end

  defp _fentan([{id, _, _}], _total, reduce_total, acc, ret), do: [{id, reduce_total - acc} | ret]
  defp _fentan([{id, price, amount} | tail], total, reduce_total, acc, ret) do
    s = price * amount
    r = s / total
    v = to_cent_round(r * reduce_total)

    _fentan(tail, total, reduce_total, acc + v, [{id, v} | ret])
  end

  defp to_cent_round(v), do: v |> (fn x -> x * 100.0 end).() |> Float.round |> round

end
