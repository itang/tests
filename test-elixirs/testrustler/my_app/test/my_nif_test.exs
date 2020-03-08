defmodule MyNifTest do
  use ExUnit.Case

  test "adds using NIF" do
    assert MyNif.add(300, 120) == 420
  end
end
