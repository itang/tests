defmodule MyNif do
  use Rustler, otp_app: :my_app, crate: "mynif"

  def add(_a, _b), do: :erlang.nif_error(:nif_not_loaded)
  def panic(), do: :erlang.nif_error(:nif_not_loaded)
  def generate(_num, _upper), do: :erlang.nif_error(:nif_not_loaded)
  def crash(), do: :erlang.nif_error(:nif_not_loaded)
end
