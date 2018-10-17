defmodule TestforwordWeb.TestController do
  use TestforwordWeb, :controller

  def test(conn, _params) do
    conn |> text("Hello, World")
  end
end
