package demo

import kotlin.test.assertEquals

fun main(args: Array<String>) {
    test1()

    test_extension()

    test_define_functions()

    defining_local_variables();

    string_templates();

    conditional_expressions();

    test_more();
}

fun test_define_functions() {
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    fun sum1(a: Int, b: Int) = a + b

    fun printSum(a: Int, b: Int): Unit {
        println(a + b)
    }
    DEBUG("${sum(1, 2)}")
    DEBUG("${sum1(1, 2)}")
    printSum(1, 2)
}

fun defining_local_variables() {
    val a: Int = 1
    val b = 1
    val c: Int
    //println(c) // Variable 'c' must be initialized
    c = 1
    DEBUG(b.javaClass)

    //mutable variable
    var x = 5
    x += 1
    DEBUG(x)
}

fun string_templates() {
    val args = arrayOf("hello", "world")
    DEBUG("First argument: ${args[0]}")

    val second = args[1]
    DEBUG("Second argument: $second")
}

fun conditional_expressions() {
    fun max(a: Int, b: Int): Int {
        if (a > b)
            return a
        else
            return b
    }

    fun max2(a: Int, b: Int) = if (a > b) a else b

    DEBUG(max(1, 2))
    DEBUG(max(100, 200))

    //Using nullable values and checking for null
    fun parseInt(str: String): Int? {
        return tryIt(str) { Integer.parseInt(it) }
    }

    val x = parseInt("100")
    val y = parseInt("200")
    //println(x * y)
    if ( x != null && y != null) {
        DEBUG(x * y)
    }

    // using type checks and automatic casts
    // the is operator checks if an expression is an instance of a type.
    fun getStringLength(obj: Any): Int? {
        if (obj is String) {
            return obj.length
        }

        return null
    }
    DEBUG("getStringLength(\"hello\"): ${getStringLength("hello")}")


    //for loop
    val args = arrayOf("a", "b", "c")
    for (arg in args) {
        println(arg)
    }
    for (i in args.indices) {
        println(args[i])
    }

    //while loop
    var i = 0
    while (i < 3) {
        println(i++)
    }

    //when expression
    fun cases(obj: Any) {
        when (obj) {
            1 -> println("One")
            "Hello" -> println("Greeting")
            is Long -> println("Long")
            !is String -> println("Not a string")
            else -> println("Unknown")
        }
    }
    cases("Hello")

    // using ranges
    val m = 101
    if (x in 1..m - 1)
        println("ok")
    val array = arrayOf(1, 2, 3)
    if (x !in 0..array.lastIndex) {
        DEBUG("x is $x")
    }

    for (x in 1..5)
        println(x)

    // using collections
    val names = listOf("itang", "tqibm")
    for (name in names)
        println(name)

    if ("itang" in names) {
        println("Yes")
    }

    names.filter { it.startsWith("t") }
            .sortedBy { it }
            .map { it.toUpperCase() }
            .forEach { println(it) }
}

fun test_more() {
    test_basic_types()
}

fun test_basic_types() {
    fun numbers() {
        val d: Double = 1000.0
        val f: Float = 500.0f
        val l: Long = 100L
        val i: Int = 50
        val s: Short = 20
        val b: Byte = 10

        val list = listOf(d, f, l, i, s, b)
        for (it in list) {
            DEBUG(it)
        }

        val hex = 0x0F
        println(hex)

        val bs = 0b00010001
        println(bs)

        //boxing
        val a: Int = 1000
        println(a == a)

        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        print(boxedA == anotherBoxedA)

        val bb: Byte = 127 // Ok, literals are checked statically
        //val i: Int = b // ERROR
        val ii: Int = b.toInt()
        DEBUG(ii)

        val ll = 1L + 3 // Long + Int => Long
        println("$ll class: ${ll.javaClass}")
    }
    numbers()

    fun operations() {
        val x = (1 shl 2) and 0x000FF000
        println(x)
    }
    operations()

    fun charactors() {
        fun check(c: Char) {
            //if (c == 1){ //ERROR: incompatible types
            if (c == 'A') {
                println("OK")
            }
        }
        check('A')

        fun decimalDigitValue(c: Char): Int {
            if (c !in '0'..'9') {
                throw IllegalArgumentException("Out of range")
            }
            return c.toInt() - '0'.toInt()
        }
        for (c in "12349".toCharArray()) {
            println(decimalDigitValue(c))
        }
    }
    charactors()

    fun booleans() {
        val a: Boolean? = true
        if (a != null && a) {
            println("true")
        }
        val b = false
        println(b.toString())

        fun printit(): Boolean {
            println("ss")
            return false
        }

        val c = a as Boolean
        if ( c || printit()) {
            DEBUG("OK")
        }
        if (c && printit()) {
            DEBUG("SHIT")
        }

        val d = !c
        if (!d) {
            println("OK")
        }
    }
    booleans()

    fun arrays() {
        /*
        class MyArray<T> private constructor(){
            val size: Int
            fun get(index:Int): T
            fun set(index: Int, value: T): Unit
            fun iterator():Iterator<T>
        }
        */
        val arr = arrayOf(1, 2, 3)
        DEBUG("arr.length: ${arr.size}")

        val arr2 = Array(5, { i -> (i * i).toString() })
        DEBUG(java.util.Arrays.toString(arr2))

        val intArr = intArrayOf(1, 2, 3)
        DEBUG(intArr[0] + intArr[2])

        assertEquals(intArr[0], 1)
    }
    arrays()

    fun strings() {
        // Strings are represented by the type String . Strings are immutable
        //Elements of a string are characters that can be
        //accessed by the indexing operation: s[i] . A string can be iterated over with a for-loop
        val str = "Hello, World"
        for (c in str) {
            println(c)
        }
        val s = "Hello, world!\n"
        assertEquals("Hello", s.split(",")[0])

        val text = """
        for (c in "foo")
          print(c)
"""
        assert(text.contains("for"))
    }
    strings()

    fun string_templates() {
        val i = 10
        val s = "i = $i"
        assertEquals("i = 10", s)

        val s1 = "abc"
        val str = "$s1.length is ${s.length}"
        DEBUG(str)
        val str2 = """$s1 123"""
        assertEquals(str2, "abc 123")

        val price = "${'$'}9.99"
        assertEquals("$9.99", price)
    }
    string_templates()
}

/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////


// extension method
fun <T> T.tap(consumer: (x: T) -> Unit): T {
    consumer(this)

    return this
}

fun <A, B> tryIt(a: A?, consumer: (A) -> B): B? {
    if (a == null) {
        return null
    } else {
        try {
            return consumer(a)
        } catch(e: Exception) {
            println("WARN: ${e.message}")
            return null
        }
    }
}

fun test1() {
    fun getValue(): String? = "1000d"

    fun parseInt(s: String?): Int? {
        return tryIt(s) { Integer.parseInt(it) }
    }

    fun pi(s: String): Int? = parseInt(s)

    DEBUG("Hello, Kotlin")
    DEBUG(parseInt("100"))
    DEBUG(parseInt("100x"))

    val s = getValue() as String // hacked
    DEBUG(pi(s))
}

fun test_extension() {
    val len = "hello".tap { println(it.toUpperCase()) }.length
    DEBUG("length: $len", "extension")
}

fun DEBUG(message: Any?, title: String? = null) {
    val line = listOf(message, title).filterNotNull().joinToString(" ")
    println("DEBUG: $line")
}
