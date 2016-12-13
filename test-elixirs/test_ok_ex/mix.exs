defmodule TestOkEx.Mixfile do
  use Mix.Project

  def project do
    [app: :test_ok_ex,
     version: "0.1.0",
     elixir: "~> 1.3",
     build_embedded: Mix.env == :prod,
     start_permanent: Mix.env == :prod,
     deps: deps(),
     escript: escript()]
  end

  def application do
    [applications: [:logger]]
  end

  defp deps do
    [{:ok, "~> 1.1.0"},{:poison, "~> 3.0"}]
  end

  def escript do
    [main_module: MyApp.CLI]
  end
end
