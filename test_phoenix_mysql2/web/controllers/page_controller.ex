defmodule TestPhoenixMysql2.PageController do
  use TestPhoenixMysql2.Web, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end
end
