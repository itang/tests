# TestRustlerEx

测试 Rust + Elixir with Rustler

## Installation

If [available in Hex](https://hex.pm/docs/publish), the package can be installed as:

  1. Add `test_rustler_ex` to your list of dependencies in `mix.exs`:

    ```elixir
    def deps do
      [{:test_rustler_ex, "~> 0.1.0"}]
    end
    ```

  2. Ensure `test_rustler_ex` is started before your application:

    ```elixir
    def application do
      [applications: [:test_rustler_ex]]
    end
    ```

## Build

```
$ rustup override set stable
$ mix compile
```

## Ref

* rustler https://hexdocs.pm/rustler/basics.html#crate-configuration
