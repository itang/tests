use "path:."
use "lib:sum"

use @add[I64](a: I64, b: I64)

actor Main
  new create(env: Env) =>
    env.out.print("4+8 = " + @add(4,8).string())
