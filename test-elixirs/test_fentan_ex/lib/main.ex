defmodule MyApp.CLI do

  def main(_args) do
       [{1, 10.0, 3}, {2, 10.0, 1}] |> fentan(5.1) |> IO.inspect
       [{1, 10.0, 1}, {2, 10.0, 1}, {3, 10.0, 1}] |> fentan(1.0) |> IO.inspect
  end

  def fentan(items, reduce_total) do
      items = items |>Enum.map(fn {id, price, amount} -> {id, fixed(price), amount } end)
      total = items |> Enum.map(fn {_, price, amount} -> price * amount end) |> Enum.sum

       _test(items, [], fixed(total), fixed(reduce_total), 0)
  end

  defp _test([{id,_,_}], ret,  total, reduce_total, acc), do: [{id, reduce_total - acc} | ret]
  defp _test([{id, price, amount} | more], ret, total, reduce_total, acc) do
      s = price * amount
      r = s / total
      v = fixed(r * reduce_total)
      _test(more, [{id, v} | ret], total, reduce_total, acc + v)
  end

  defp fixed(v), do: (v * 100.0) |> Float.round |> round

end
