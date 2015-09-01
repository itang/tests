defmodule TestPhoenixMysql2.PageControllerTest do
  use TestPhoenixMysql2.ConnCase

  test "GET /" do
    conn = get conn(), "/"
    assert html_response(conn, 200) =~ "Welcome to Phoenix!"
  end
end
