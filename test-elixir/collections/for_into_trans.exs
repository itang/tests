bs = 3
ps = Enum.to_list(0..9)

# to map
ret = List.foldl(ps, %{}, fn(p, acc) ->
        b = rem(p, bs)
        Map.put acc, b, [p | (acc[b] || [])]
      end)

ret |> inspect |> IO.puts

# reverse values
#ret = Enum.into(ret, %{}, fn {k, v} -> {k, Enum.reverse(v)} end)
ret = for {k, v} <- ret, into: %{}, do: {k, Enum.reverse(v)}
ret |> inspect |> IO.puts

## output
# %{0 => [9, 6, 3, 0], 1 => [7, 4, 1], 2 => [8, 5, 2]}
# %{0 => [0, 3, 6, 9], 1 => [1, 4, 7], 2 => [2, 5, 8]}
