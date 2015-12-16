package demo

import demo.classes.User
import java.util.*
import java.text.SimpleDateFormat as DFormat
import java.lang.String.format
import javax.inject.Inject

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

    test_packages()

    test_control_flow()

    test_classes_objects()
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

fun test_packages() {
    /*
    All the contents (such as classes and functions) of the source file are contained by the package declared. So, in the example
above, the full name of baz() is foo.bar.baz , and the full name of Goo is foo.bar.Goo .
If the package is not specified, the contents of such a file belong to “default” package that has no name
     */
    // package foo.bar
    // import foo.Bar
    // import foo.*
    // import foo.Bar
    // import bar.Bar as bBar
    println(DFormat("yyyy-MM-dd").format(Date()))
    DEBUG(format("hello, %s", "world"))

    // the import keyword is not restricted to importing classes; you can also use it to import other declarations:
    // top-level functions and properties
    // functions and properties declared in object declarations;
    // enum constants

    //Visibility of Top-level Declarations
    // if a top-level declaration is marked private, it is private to the file it's declared in.

}

fun test_control_flow() {
    fun test_if_expression() {
        // if is an expression
        val a = 100
        var max = a
        val b = 200
        if (a < b) {
            max = b
        }
        assertEquals(max, 200)

        fun max(a: Int, b: Int): Int {
            val max = if (a > b ) a else b
            return max
        }
        assertEquals(300, max(100, 300))

        fun max2(a: Int, b: Int): Int {
            val max = if ( a > b) {
                println("Choose a")
                a
            } else {
                println("Choose b")
                b
            }
            return max
        }
        assertEquals(max2(1, 2), 2)

        if (a > 10) {
            println("a > 10")
        } else if (a > 100) {
            println("a > 100")
        } else {
            println("a<= 10")
        }
    }
    test_if_expression()

    fun test_when() {
        val x = 100
        when (x) {
            1 -> print("x == 1")
            2 -> print("x == 2")
            else -> {
                print("x is neither 1 nor 2")
            }
        }
        val b = 1
        when (b) {
            0, 1 -> println("x == 0 or x == 1")
            else -> println("otherwise")
        }
        when (x) {
            tryIt("100") { Integer.parseInt(it) } -> println("100")
            else -> println("s does not encode x")
        }

        val validNumbers = arrayOf(100, 200, 300)
        when (x) {
            in 1..10 -> println("x is in the range")
            in validNumbers -> println("x is valid")
            !in 10..20 -> println("x is outside the range")
            else -> println("none of the above")
        }

        val s = "hello,world"
        val hasPrefix = when (s) {
            is String -> s.startsWith("hello")
            else -> false
        }
        assert(hasPrefix)

        fun Int.isOdd(): Boolean {
            return this % 2 == 1
        }

        fun Int.isEven(): Boolean {
            return this % 2 == 0
        }
        //as if else if chain
        println("x: $x")
        when {
            x.isOdd() -> println("x is odd")
            x.isEven() -> println("x is even")
            else -> println("x is funny")
        }
    }
    test_when()

    fun test_for_loop() {
        /*
        for iterates through anything that provides an iterator, i.e.
        — has a member- or extension-function iterator() , whose return type
        — has a member- or extension-function next() , and
        — has a member- or extension-function hasNext() that returns Boolean .
        All of these three functions need to be marked as operator .
         */
        val list = listOf(1, 2, 3)
        for (it in list) {
            println(it)
        }

        val arr = arrayOf(1, 2, 3, 4, 5)
        for (i in arr.indices) {
            println("$i: ${arr[i]}")
        }
        val arr2 = arrayOf(200, 300, 400, 500)
        arr2.forEachIndexed { a, b -> println("$a, $b") }

        for ((index, value) in arr2.withIndex()) {
            println("$index: $value")
        }
    }
    test_for_loop()

    fun test_while_loop() {
        var x = 10
        while (x > 0) {
            println(x)
            x--;
        }

        val random = java.util.Random()
        fun retrieveData(): Int {
            return random.nextInt(10)
        }

        do {
            val y = retrieveData()
            println("y: $y")
        } while (y < 6)
    }

    test_while_loop()

    fun test_return_jump() {
        /*
        — return. By default returns from the nearest enclosing function or anonymous function.
        — break. Terminates the nearest enclosing loop.
        — continue. Proceeds to the next step of the nearest enclosing loop.
         */
        //Break and Continue Labels
        var sum = 0
        loop@ for (i in 1..100) {
            for (j in 1..100) {
                sum++
                if (i + j > 10)
                    break@loop
            }
        }
        DEBUG("sum: $sum")

        // Return at Labels
        val ints = arrayOf(1, 2, 3, 5, 6, 100)
        fun foo(): Int {
            var i = 0
            ints.forEach {
                if (it > 4) return it
                i++
            }
            return i
        }
        assertEquals(5, foo())

        fun foo2(): Int {
            var i = 0
            ints.forEach lit@ {
                if (it > 4) return@lit
                i += it
            }
            return i
        }

        fun foo3(): Int {
            var i = 0
            ints.forEach {
                if (it > 5) return@forEach
                i += it
            }
            return i
        }
        assertEquals(foo3(), 1 + 2 + 3 + 5)

        //Alternatively, we can replace the lambda expression with an anonymous function. A return statement in an anomymous
        //function will return from the anonymous function itself.

        fun foo4(): Int {
            var i = 0
            ints.forEach(fun(it: Int) {
                if (it > 5) return
                i += it
            })
            return i
        }
        assertEquals(foo4(), 1 + 2 + 3 + 5)
    }
    test_return_jump()
}

fun test_classes_objects() {
    class Invoice {

    }

    class Empty

    // constructors
    // A class in Kotlin can have a primary constructor and one or more secondary constructors. The primary constructor is
    // part of the class header: it goes after the class name (and optional type parameters).
    // If the primary constructor does not have any annotations or visibility modifiers, the constructor keyword can be omitted:
    class Person constructor(firstname: String) {

    }

    class Person2(firstname: String) {

    }

    //The primary constructor cannot contain any code. Initialization code can be placed in initializer blocks, which are prefixed
    //with the init keyword:
    class Customer(name: String) {
        init {
            DEBUG("Customer initialized with value ${name}")
        }
    }

    val c = Customer("itang")
    println(c)
    // They can also be used in property
    // initializers declared in the class body:
    class Customer2(name: String) {
        val customerKey = name.toUpperCase()
    }

    val c2 = Customer2("itang")
    DEBUG("c2.customerKey: ${c2.customerKey}")

    // for declaring properties and initializing them from the primary constructor, Kotlin has a concise syntax:
    class Person3(val firstName: String, val lastName: String, var age: Int) {
    }

    val p = Person3("tang", "da", 100)
    DEBUG("p.firstName: ${p.firstName}, p.lastName:${p.lastName}, p.age: ${p.age}")
    p.age = 120
    DEBUG("p.firstName: ${p.firstName}, p.lastName:${p.lastName}, p.age: ${p.age}")

    class Person4 {

    }

    // If the constructor has annotations or visibility modifiers, the constructor keyword is required, and the modifiers go before
    class Customer3 @Inject constructor(name: String) {

    }

    val user = User("itang")
    println(user.name)

    // secondary constructors, which are prefixed with constructor:
    class A {
        var theName: String

        constructor(name: String) {
            theName = name
        }
    }

    val a = A("itang")
    println("a.theName: ${a.theName}")

    //If the class has a primary constructor, each secondary constructor needs to delegate to the primary constructor, either directly
    //or indirectly through another secondary constructor(s).
    class B(val name: String) {
        var a: A = A(name)

        constructor(name: String, a: A) : this(name) {
            this.a = a
        }
    }

    val b = B("itang")
    assertEquals(b.name, "itang")
    assertEquals(b.a.theName, "itang")

    val b2 = B("itang", A("tqibm"))
    assertEquals(b2.name, "itang")
    assertEquals(b2.a.theName, "tqibm")

    //
    class C(val name: String = "C")

    val co = C()
    val co2 = C("CC")
    assertEquals(co.name, "C")
    assertEquals(co2.name, "CC")

    //Creating instances of classes
    // To create an instance of a class, we call the constructor as if it were a regular function:
    // Note that Kotlin does not have a new keyword.
    val invoice = Invoice()
    val customer = Customer("ss")
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
