defmodule TestM do
   defmacro cmd(cmds) do
     cmds |> Enum.map fn x ->
       quote do
         def unquote(x)(args \\ []) do
           System.cmd unquote(x) |> to_string, args
         end
       end
     end
  end
end

defmodule HygieneTest do
    require TestM
    TestM.cmd([:jiayou, :gops])
end
