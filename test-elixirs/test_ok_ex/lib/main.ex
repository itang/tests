import OK, only: :macros

defmodule MyApp.CLI do
    def get_employee_data(file, name) do
        {:ok, file}
        ~>> File.read
        ~>> Poison.decode
        ~>> Dict.fetch(name)
    end

    def handle_user_data({:ok, data}), do: IO.puts("Contact at #{data["email"]}")
    def handle_user_data({:error, :enoent}), do: IO.puts("File not found")
    def handle_user_data({:error, {:invalid, _}}), do: IO.puts("Invalid JSON")
    def handle_user_data({:error, {:invalid, _, _}}), do: IO.puts("Invalid JSON")
    def handle_user_data({:error, :key_not_found}), do: IO.puts("Could not find employee")
    def handle_user_data(some), do: IO.inspect some

    def main(_args) do
        get_employee_data("employees.json", "tang") |> handle_user_data
        get_employee_data("employees_xxx.json", "tang") |> handle_user_data
        get_employee_data("employees_bad.json", "tang") |> handle_user_data
        get_employee_data("employees.json", "xxx") |> handle_user_data
    end
end
