object pattern_and_functionalcomposition {

  def main(args: Array[String]): Unit = {
    def f(s: String) = "f(" + s + ")"

    def g(s: String) = "g(" + s + ")"

    val fComposeG = f _ compose g _ // f(g(x))
    println(fComposeG("a"))
    println(f.compose(g)("x"))

    println(f.andThen(g)("x"))

    // PartialFunction
    val casef: PartialFunction[String, String] = {
      case null => "NULL"
      case a: String if a.length < 5 => a.toUpperCase()
      case b: String if b.length < 10 => b.toLowerCase()
    }
    println(casef("itang"))
    println(casef("computer"))
    println(casef.isDefinedAt(null))
    println(casef.isDefinedAt("itang"))
    println(casef.isDefinedAt("*" * 10))
    try {
      casef("*" * 10)
    } catch {
      case e: Exception => println(e.getStackTrace.mkString("\n"))
    }

    val casef2 = casef.orElse({
      case c: String if c.length < 20 => c.toUpperCase() + "."
    })
    assert(casef2.isDefinedAt("*" * 15))
  }
}
