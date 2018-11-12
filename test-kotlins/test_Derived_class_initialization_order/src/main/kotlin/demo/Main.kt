package demo

fun main(args: Array<String>) {
    val obj = A("a", "b")
    println(obj.c)
    println(obj.d)
}

interface IBase {
    val a: String
    val b: String
    val c: List<String>
    val d: List<String>
}

abstract class Base(override val a: String, override val b: String, e: String ) : IBase {
    override val c = forInit()

    init {
        println("Base init $a, $b $e")
    }

    fun forInit(): List<String> {
        println("Base for init list $a $b ")
        return listOf(a, b)
    }
}

class A(override val a: String, override val b: String) : Base(a, b, "e") {
    override val d = listOf(a, b)

    init {
        println("A init $a, $b")
    }
}