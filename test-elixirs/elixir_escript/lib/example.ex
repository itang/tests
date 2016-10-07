defmodule Example do
  def main(args) do
    args |> parse_args |> process
  end

  defp parse_args(args) do
    {options,_,_} = OptionParser.parse(args, switches: [foo: :string])
    options
  end

  def process(options) do
    IO.puts "Hello #{options[:name]}"
  end

  def process([]) do
    IO.puts "No arguments given"
  end
end
