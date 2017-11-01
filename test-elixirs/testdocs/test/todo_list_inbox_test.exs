defmodule TodoList.InboxTest do
  use ExUnit.Case
  doctest TodoList.Inbox

  test "returns the header" do
    assert TodoList.Inbox.header() == "Inbox"
  end
end