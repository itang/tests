package demo

import com.github.austinc.jnrepl.Jnrepl

object A {
    val name = "itang"
}

fun main(args: Array<String>) {
    val a = A
    println(a.name)
    Jnrepl.startRepl() // (bean (demo.A/INSTANCE)) => {:class demo.A, :name "itang"}
}
