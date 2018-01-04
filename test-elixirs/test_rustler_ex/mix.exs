defmodule TestRustlerEx.Mixfile do
  use Mix.Project

  def project do
    [
      app: :test_rustler_ex,
      version: "0.1.0",
      elixir: "~> 1.3",
      build_embedded: Mix.env() == :prod,
      start_permanent: Mix.env() == :prod,
      deps: deps(),
      # REF LINK: https://hexdocs.pm/rustler/basics.html#crate-configuration
      compilers: [:rustler] ++ Mix.compilers(),
      rustler_crates: [
        test_fib: [
          path: "/native/test_fib",
          mode: if(Mix.env() == :prod, do: :release, else: :debug)
        ]
      ],
      escript: escript
    ]
  end

  def application do
    [applications: [:logger]]
  end

  defp deps do
    # {:rustler, "~> 0.5.0"},
    [
      {:rustler, github: "hansihe/rustler", sparse: "rustler_mix"},
      {:benchfella, "~> 0.3.0"}
    ]
  end

  def escript do
    [main_module: MyApp.CLI]
  end
end