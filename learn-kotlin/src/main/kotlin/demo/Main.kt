package demo


import demo.classes.User
import java.util.*
import java.text.SimpleDateFormat as DFormat
import java.lang.String.format
import javax.inject.Inject
import kotlin.concurrent.thread
import kotlin.properties.Delegates
import kotlin.properties.getValue
import kotlin.properties.setValue
import kotlin.reflect.KProperty
import kotlin.test.*

fun main(args: Array<String>) {
    test1()

    test_extension()

    test_define_functions()

    defining_local_variables()

    string_templates()

    conditional_expressions()

    test_more()

    test_extensions()

    test_data_classes()

    test_generics()

    test_nested_classes()

    test_enum_classes()

    test_object_expressions_and_declarations()

    test_delegation()
}

fun test_delegation() {
    val b = DBaseImpl(10)
    DDerived(b).print()

    //Delegated Properties
    // - lazy properties: the value gets computed only upon first access
    // - observable properties: listeners get notified about changes to this property,
    // - storing properties in a map, not in separate field each.
    class Delegate {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, thak yhou for delegating '${property.name}' to me!"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$value has been assgind to '${property.name} in $thisRef.'")
        }
    }

    class Example {
        var p: String by Delegate()
    }

    val e = Example()
    e.p = 100.toString()
    println(e.p)

    // Standard Delegates
    // Lazy
    //lazy() is a function that takes a lambda and returns an instance of Lazy<T> which can serve as a delegate for
    //implementing a lazy property: the first call to get() executes the lambda passed to lazy() and remembers the result,
    //subsequent calls to get() simply return the remembered result
    class LazyTest {
        val lazyValue: String by lazy {
            println("computed!")
            "Hello"
        }
    }

    val l = LazyTest()
    println(l.lazyValue.javaClass)
    assertEquals(l.lazyValue, "Hello")
    assertEquals(l.lazyValue, "Hello")

    class User {
        var name: String by Delegates.observable("<no name>") {
            p, old, new ->
            println("$old -> $new")
        }
    }

    val user = User()
    println(user.name)
    assertEquals(user.name, "<no name>")
    user.name = "itang"

    assertEquals(user.name, "itang")
    user.name = "tqibm"

    //storing properties in a Map
    class User2(val map: Map<String, Any?>) {
        val name: String by map
        val age: Int by map
        val addr: String by map
    }

    val user2 = User2(mapOf("name" to "Itang", "age" to 25))
    assertEquals(user2.name, "Itang")
    assertEquals(user2.age, 25)

    assertFails { user2.addr }

    //
    class MutableUser(val map: MutableMap<String, Any?>) {
        var name: String by map
        var age: Int by map
    }

    val m = HashMap<String, Any?>()
    m.put("name", "itang")
    m.put("age", 100)

    val user3 = MutableUser(m)
    assertEquals(user3.name, "itang")
    user3.name = "tqibm"
    assertEquals(m.get("name"), "tqibm")
}

interface DBase {
    fun print()
}

class DBaseImpl(val x: Int) : DBase {
    override fun print() {
        println(x)
    }
}

class DDerived(b: DBase) : DBase by b

fun test_object_expressions_and_declarations() {
    open class A(x: Int) {
        open val y: Int = x
    }

    val ab = object : A(1), Aoed {
        override val y = 15
    }
    assertEquals(15, ab.y)

    val adHoc = object {
        var x: Int = 5
        var y: Int = 10
    }
    println(adHoc.x + adHoc.y)


    DataProviderManager.registerDataProvider(DataProvider())
    println(DataProviderManager.allDataProviders)

    //Companion Objects
    val myclassIntance = MyClass2.create()
    println(myclassIntance)

    println(MyClass3.create())
    println(MyClass4.create())

    // — object declarations are initialized lazily, when accessed for the first time
    // — object expressions are executed (and initialized) immediately, where they are used
}

interface Aoed {}
class DataProvider {}

object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
        // ...
    }

    val allDataProviders: Collection<DataProvider>
        get () = listOf()
}

class MyClass2 {
    companion object Factory {
        fun create(): MyClass2 = MyClass2()
    }
}

class MyClass3 {
    companion object {
        fun create(): MyClass3 = MyClass3()
    }
}

interface Factory<T> {
    fun create(): T
}

class MyClass4 {
    companion object : Factory<MyClass4> {
        override fun create(): MyClass4 = MyClass4()
    }
}

fun test_enum_classes() {
    val d: Direction = Direction.NORTH
    assertEquals(d, Direction.NORTH)

    val c: Color = Color.BLUE
    assertEquals(c, Color.BLUE)
    assertEquals(c.rgb, 0x0000FF)

    println(Arrays.toString(ProtocolState.values()))

    val s = ProtocolState.valueOf("WAITING")
    assertEquals(s, ProtocolState.WAITING)

    assertEquals(s.name, "WAITING")
    assertEquals(s.ordinal, 0)
}

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
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

    test_interfaces()

    visibility_modifiers()
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

    //class Members
    // constructors and initializer blocks
    // Functions
    // Properties
    // Nested and Inner Classes
    // Object Declarations

    fun test_inheriatance() {
        class Example // Implicitly inheriats from any
        // Any is not java.lang.Object ; in particular, it does not have any members other than equals() , hashCode() and
        // toString()

        // To declare an explicit supertype, we place the type after a colon in the class header:
        open class Base(p: Int)

        class Derived(val p: Int) : Base(p)

        val d = Derived(100)
        assertEquals(d.p, 100)

        class Base2(p: Int)
        //class Derived2(val p: Int) : Base2(p) // This type is final, so it cannot be inherited from

        class Context
        class AttributeSet
        open class View(context: Context) {
            constructor(context: Context, attr: AttributeSet) : this(context)
        }

        class MyView : View {
            constructor(ctx: Context) : super(ctx)

            constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr)
        }

        //Overriding Members
        open class B {
            open fun v() {
                println("B")
            }

            fun nv() {
            }
        }

        class BB() : B() {
            override fun v() {
                println("BB")
            }

            //override fun nv(){}
        }

        val b: B = B()
        b.v()
        val b2: BB = BB()
        b2.v()

        val b3: B = BB()
        b3.v()

        open class BBB() : B() {
            //A member marked override is itself open, i.e. it may be overridden in subclasses. If you want to prohibit re-overriding, use
            //final
            final override fun v() {
                println("BBB")
            }
        }

        val b4: B = BBB()
        b4.v()

        class B2() : BBB() {
            //override fun v(){}
        }

        val b5: B = B2()
        b5.v()

        //Overriding Rules
        fun overridingRules() {
            open class A {
                open fun f() {
                    println("A")
                }

                fun a() {
                    println("a")
                }
            }

            class C() : A(), IB {
                final override fun f() {
                    super<A>.f()
                    super<IB>.f()
                }

                override fun b() {
                    super.b()
                }

                override fun c() {
                    println("C")
                }
            }

            val c = C()
            c.f()
        }
        overridingRules()

        fun abstract_classes() {
            abstract class A {
                abstract fun f()
            }

            class C() : A(), IB {
                override fun f() {
                    super.f()
                }

                override fun c() {
                    println("C")
                }
            }

            val c = C()
            c.f()

            // We can override a non-abstract open member with an abstract one
            open class Base {
                open fun f() {
                }
            }

            abstract class Derived : Base() {
                override abstract fun f()
            }
        }
        abstract_classes()
    }

    test_inheriatance()

    fun test_companion() {
        //If you need to write a function that can be called without having a class instance but needs access to the internals of a class
        //(for example, a factory method), you can write it as a member of an object declaration inside that class.

        // Even more specifically, if you declare a companion object inside your class, you’ll be able to call its members with the same
        // syntax as calling static methods in Java/C#, using only the class name as a qualifier
        ObjectA.hello()

        val oa = ObjectCampanionA.create()
        oa.hello()
    }
    test_companion()

    fun sealed_classes() {
        //Sealed classes are used for representing restricted class hierarchies, when a value can have one of the types from a limited
        //set, but cannot have any other type
        fun eval(expr: Expr): Double = when (expr) {
            is Expr.Const -> expr.number
            is Expr.Sum -> eval(expr.e1) + eval(expr.e2)
            Expr.NotANumber -> Double.NaN
        }
        assertEquals(eval(Expr.Sum(Expr.Const(100.0), Expr.Const(200.0))), 300.0)
    }
    sealed_classes()

    //
    fun properties_fields() {
        // Classes in Kotlin can have properties. These can be declared as mutable, using the var keyword or read-only using the val
        //keyword
        class Address {
            public var name: String = ""
        }

        val addr = Address()
        println(addr.name)
        addr.name = "sh"
        assertEquals(addr.name, "sh")

        // Getters and Setters
        /*
        var <propertyName>: <PropertyType> [= <property_initializer>]
          <getter>
          <setter>
         */
        class A {
            var name: String = "a"

            var fullName: String
                get() = name.toUpperCase()
                set(value) {
                    this.name = value
                }

            val isEmpty: Boolean
                get() = fullName.isEmpty()

            var setterVisibility: String = "abc"
                private set

            var setterWithAnnotation: Any? = null
                @Inject set

        }

        val a = A()
        assertEquals("a", a.name)
        assertEquals("A", a.fullName)
        assertFalse { a.isEmpty }

        // Backing fields
        // classes in kotlin cannot have fields, sometimes it is necessary to have a backing field when using custom
        // accessors. for these purposes, Kotlin provides an automatic backing field which can be accesse using the field
        // identifier

        class B {
            var counter = 0 // the initializer value is written directly to the backing field
                set(value) {
                    if (value > 0)
                        field = value
                }
        }

        val b = B()
        assertEquals(b.counter, 0)
        b.counter = -1
        assertEquals(b.counter, 0)
        b.counter = 100
        assertEquals(b.counter, 100)

        fun compile_time_constants() {
            // top-level or member of an object
            // initialized with a value of type String or a primitive type
            // No custom getter
            // such properties can be used in annotations
            @Deprecated(Constants.SUBSYSTEM_DEPRECATED) fun foo() {
                println("deprecated")
            }
            foo()
        }
        compile_time_constants()

        fun late_initialized_properties() {
            class TestSubject {
                fun method() {
                    println("sss")
                }
            }

            class MyTest {
                // tip for compiler
                lateinit var subject: TestSubject

                /*@SetUp*/ fun setup() {
                    subject = TestSubject()
                }

                /*@Test*/fun test() {
                    subject.method()
                }
            }
        }
    }
    properties_fields()
}

fun test_interfaces() {
    class Child : MyInterface {
        override val property: Int = 29
        override fun bar() {
            println("bar")
        }
    }

    val child = Child()
    child.bar()
    child.foo()
}

fun visibility_modifiers() {
    // Classes, objects, interfaces, constructors, functions, properties and their setters can have visibility modifiers.
    // (Getters always
    // have the same visibility as the property.)

    // Packages.
    //
    // If you do not specify any visibility modifier, public is used by default, which means that your declarations will be visible
    // everywhere;
    // If you mark a declaration private , it will only be visible inside the file containing the declaration;
    // If you mark it internal , it is visible everywhere in the same module;
    // protected is not available for top-level declarations.

    //classes and Interfaces
    // — private means visible inside this class only (including all its members);
    // — protected — same as private + visible in subclasses too;
    // — internal — any client inside this module who sees the declaring class sees its internal members;
    // — public — any client who sees the declaring class sees its public members.
    open class Outer {
        private val a = 1
        protected val b = 2
        internal val c = 3
        val d = 4 // public by default

        inner protected class Nested {
            public val e: Int = 5
        }
    }

    class Subclass : Outer() {
        fun test() {
            //this.a // a is not visible
            println(b)
            println(c)
            println(d)
        }
    }

    //constructors
    // By default, all constructors are public

    //val c = PrivateConsClass()
    val c = PrivateConsClass.create()
    assertEquals(c.a, 100)

    // Modules
    // a module is a set of Kotlin files compiled together
    // an IntelliJ IDEA module;
    // a Maven or Gradle project;
    // a set of files compiled with one invocation of the Ant task.
}

fun test_extensions() {
    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        val tmp = this[index1] // 'this'  corrensponds to the list
        this[index1] = this[index2]
        this[index2] = tmp
    }

    //val l = mutableListOf(1,2,3)
    //l.swap(0, 2)
    //assertEquals(l, mutableListOf(3,2,1))

    fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val tmp = this[index1]
        this[index1] = this[index2]
        this[index2] = tmp
    }

    // Extensions are resolved statically
    // . If
    //there’s a member and extension of the same type both applicable to given arguments, a member always wins
    class C {
        fun foo(): String {
            return ("member")
        }
    }

    fun C.foo(): String {
        return ("extension")
    }

    val c = C()
    assertEquals("member", c.foo())

    // extension properties
    //val <T> List<T>.lastIndex: Int
    //get() = size - 1

    //Companion Object Extensions
    //If a class has a companion object defined, you can also define
    // extension functions and properties for the companion
    fun MyClass.Companion.foo(): MyClass {
        return MyClass()
    }

    val m = MyClass.foo()
    assertNotNull(m)

    //Scope of Extendions
    //most of the time we define extensions on the top level.

}

fun test_data_classes() {
    data class User(val name: String, val age: Int)
    // - equals() hashCode()
    // - toString()
    // - componentN()
    // - copy()

    // Th primary constructor needs to have at least one parameter
    // All primary constructor parameters need to be marked as val or var.
    // Data classes can't be abstract, open, sealed or inner.
    // Data classes may not extend other classes (but may implements interfaces).
    data class User2(val name: String = "", val age: Int = 1)

    val user = User("itang", 30)
    //copy
    val jack = user.copy(name = "Jack", age = 100)
    assertEquals("Jack", jack.name)
    assertEquals(100, jack.age)

    val newjack = jack.copy(age = 10)
    assertEquals("Jack", newjack.name)
    assertEquals(10, newjack.age)

    // Data classes and Destructuring Declarations
    val jane = User("Jane", 35)
    val (name, age) = jane
    println("$name, $age years of age")

    //standard library provides Pair and Triple
    val kv = Pair("Itang", 100)
    assertEquals(kv.first, "Itang")
    assertEquals(kv.second, 100)
    val (key, value) = kv
    assertEquals("$key-$value", "Itang-100")
}

fun test_generics() {
    // the wildcard with an extends-bound (upper bound) makes the type covariant
    // PECS stands for Producer-Extends, Consumer-Super.

    // site variance: we can annotate
    // the type parameter T of Source to make sure that it is only returned (produced) from members of Source<T> , and never
    // consumed. To do this we provide the out modifier
    abstract class Source<out T> {
        abstract fun nextT(): T
    }

    fun demo(strs: Source<String>) {
        val objects: Source<Any> = strs // This is ok, since T is an out-parameter.
    }

    // in "clever words" thay say that the class C is covariant in the parmeter T, or that T is a convariant type parameter.
    // can think of C as being a producer of T's, and NOT a consumer of T' s
    // The out modifier is called a variance annotation

    // in addition to out, Kotlin provides a complementary variance annotation: in. It makes a type parameter
    // contravariant: it can only be consumed and never producer. A good example
    // of a contravariant class is Comparable:
    abstract class Comparable1<in T> {
        abstract fun compareTo(other: T): Int
    }

    fun demo(x: Comparable1<Number>) {
        x.compareTo(1.0) // 1.0 has type double, which is a subtype of Number
        // Thus , we can assign x to a variable of type comparable<Double>
        val y: Comparable1<Double> = x //
    }
    //The Existential Transformation: Consumer in, Producer out! :-)

    //type projections
    // use-site variance: Type projections

    //Generic functions
    // functions

    fun <T> singletonList(item: T): List<T> {
        return listOf(item)
    }

    val s = singletonList(1000)
    assertEquals(s, listOf(1000))

    val l = singletonList<Long>(1)
    println(l[0].javaClass)

    //Generic constraints
    // Upper Bounds: upper bound that corresponds to Java’s extends keyword:

    fun <T : Comparable<T>> sort(list: List<T>) {
    }
    sort(listOf(1, 2, 3)) // ok. Int is a subtype of Comparable<Int>
    //sort(listOf(HashMap<Int, String>())) //

    // The default upper bound (if none specified) is Any? .
    // If the same type parameter needs more than one upper bound, we need a separate where-clause:

    fun <T> cloneWhenGreater(list: List<T>, threshold: T): List<T>
            where T : Comparable<T>,
    T : Cloneable2<T> {
        return list.filter { it > threshold }.map { it.clone() }
    }

    data class CC(val i: Int) : Comparable<CC>, Cloneable2<CC> {
        override fun compareTo(other: CC): Int {
            return i - other.i
        }

        override fun clone(): CC {
            return CC(i)
        }

    }
    println(cloneWhenGreater(listOf(CC(1), CC(2), CC(3)), CC(2)))
}

fun test_nested_classes() {
    class Outer2 {
        private val bar: Int = 1

        inner class Nested {
            fun foo() = 2
        }
    }

    val outer = Outer()
    val outer2 = Outer2()
    val d = Outer.Nested().foo()
    val dd = Outer2().Nested().foo()
    assertEquals(d, dd)
}

class Outer {
    private val bar: Int = 1

    /* java public static */class Nested {
        fun foo() = 2
    }
}

interface Cloneable2<T> {
    fun clone(): T
}

class MyClass {
    companion object {}
}

// extension properties
val <T> List<T>.lastIndex: Int
    get() = size - 1

interface MyInterface {
    val property: Int // abstract

    fun bar()
    fun foo() {
        print(property)
    }
}

interface IB {
    fun f() {
        println("IB")
    }

    fun b() {
        print("ib")
    }

    fun c(): Unit
}

object ObjectA {
    fun hello() {
        println("Hello ObjectA")
    }
}


class ObjectCampanionA {
    companion object Factory {
        fun create(): ObjectCampanionA = ObjectCampanionA()
    }

    fun hello() {
        println("ObjectCampanionA")
    }
}

sealed class Expr {
    class Const(val number: Double) : Expr()
    class Sum(val e1: Expr, val e2: Expr) : Expr()

    object NotANumber : Expr()
}

object Constants {
    const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"
}

class PrivateConsClass private constructor(val a: Int) {
    companion object Factory {
        fun create(): PrivateConsClass = PrivateConsClass(100)
    }
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
