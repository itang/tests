defmodule TestforwordWeb.PageController do
  use TestforwordWeb, :controller

  def index(conn, _params) do
    render(conn, "index.html")
  end
end
