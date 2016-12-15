defmodule TestFentanEx.Mixfile do
  use Mix.Project

  def project do
    [app: :test_fentan_ex,
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
    [{:credo, "~> 0.5", only: [:dev, :test]}]
  end

  def escript do
    [main_module: MyApp.CLI]
  end
end
