defmodule Test.Fib do
    require Rustler

    @on_load :load_nif
    def load_nif do
        Rustler.load_nif(:test_rustler_ex, "test_fib")
    end

    # When your NIF is loaded, it will override this function.
    def add(_a, _b), do: throw :nif_not_loaded
end
