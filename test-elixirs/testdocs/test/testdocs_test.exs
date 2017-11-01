defmodule TestdocsTest do
  use ExUnit.Case
  doctest Testdocs

  test "greets the world" do
    assert Testdocs.hello() == :world
  end
end
