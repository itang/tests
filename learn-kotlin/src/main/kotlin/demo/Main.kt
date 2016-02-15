package demo

import demo.classes.User
import java.util.*
import java.text.SimpleDateFormat as DFormat
import java.lang.String.format
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject
import kotlin.concurrent.thread
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaGetter
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

    test_functions_lambdas()

    test_higher_order_functions_lamdbas()

    test_destructuring_declarations()

    test_ranges()

    test_type_checks_and_casts()

    test_this()

    test_equal()

    test_operator_overloading()

    test_null_safety()

    test_exceptions()

    test_annotations()

    test_reflection()

    test_type_safe_builders()
}

fun test_type_safe_builders() {

}

fun test_reflection() {
    // The most basic reflection feature is getting the runtime reference to a Kotlin class. To obtain the reference to a statically
    // known Kotlin class, you can use the class literal syntax
    class MyClass

    val c: KClass<MyClass> = MyClass::class

    println(c)

    val j: Class<MyClass> = MyClass::class.java
    println(j)

    // Function References

    fun isOdd(x: Int) = x % 2 != 0
    // We can easily call it directly ( isOdd(5) ), but we can also pass it as a value, e.g. to another function. To do this, we use the
    //:: operator:
    println(arrayListOf(1, 2, 3, 4, 5).filter(::isOdd))

    fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
        return { x -> f(g(x)) }
    }

    fun toString(x: Int) = x.toString()
    fun getLength(x: String) = x.length
    val numAsLentoLength = compose(::getLength, ::toString)
    assertEquals(3, numAsLentoLength(100))

    /*
    The expression ::x evaluates to a property object of type KProperty<Int> , which allows us to read its value using
get() or retrieve the property name using the name property. For more information, please refer to the docs on the
KProperty class.
For a mutable property, e.g. var y = 1 , ::y returns a value of type KMutableProperty<Int>, which has a set()
method.
     */

    class D {
        var x = 1
    }

    val d = D()

    val p: KProperty<Int> = D::x
    assertEquals(p.getter.name, "<get-x>")
    assertEquals(p.getter.call(d), 1)


    val p2: KMutableProperty<Int> = D::x
    p2.setter.call(d, 100)
    assertEquals(p.getter.call(d), 100)

    class Foo

    fun function(factory: () -> Foo) {
        val x: Foo = factory()
    }
    function(::Foo)

}

fun test_annotations() {
    /*
    @Target specifies the possible kinds of elements which can be annotated with the annotation (classes, functions,
    properties, expressions etc.);

    @Retention specifies whether the annotation is stored in the compiled class files and whether it’s visible through
    reflection at runtime (by default, both are true);

    @Repeatable allows using the same annotation on a single element multiple times;

     */

    @Fancy class Foo {
        @Fancy fun baz(@Fancy foo: Int): Int {
            return (@Fancy 1)
        }
    }

    class Foo2 @Inject constructor(de: MyClass)

    class MyDependency
    class Foo3 {
        var x: MyDependency? = null
            @Inject set
    }

    @Special("example") class Foo4 {}

    // Lambdas
    val f = @Suspendable { Thread.sleep(1) }
    f.invoke()

    //
    annotation class Ann

    class Example(@field:Ann val foo: String, @get:Ann val bar: String, @param:Ann val quux: String)

    annotation class VisibleForTesting

    class Collaborator
    class Example2 {
        //If you have multiple annotations with the same target, you can avoid repeating the target by adding brackets after the target
        // and putting all the annotations inside the brackets

        @set:[Inject VisibleForTesting]
        lateinit public var collaborator: Collaborator
    }

    /*
    The full list of supported use-site targets is:
— file
— property (annotations with this target are not visible to Java)
— field
— get (property getter)
— set (property setter)
— receiver (receiver parameter of an extension function or property)
— param (constructor parameter)
— setparam (property setter parameter)
     */

    fun @receiver:Fancy String.myExtension() {
    }

    @CAnn(String::class, Int::class) class MyClass2

    println(String::class)
}

annotation class CAnn(val arg1: KClass<*>, val arg2: KClass<out Any>)

annotation class Suspendable

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Fancy

annotation class Special(val why: String)

fun test_exceptions() {
    //throw MyException("Hi There!")
    try {
        throw RuntimeException("ERROR")
    } catch(e: Exception) {
        assertEquals(e.message, "ERROR")
    } finally {
        println("finally")
    }

    val a: Int = try {
        Integer.parseInt("100s")
    } catch(e: Exception) {
        println("WARN: ${e.message}")
        -1
    }
}

fun test_null_safety() {
    var a: String = "ss"
    //a = null //compilation error
    var b: String? = "abc"
    b = null // ok
    val l = a.length

    // val l2 = b.length // nly safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type kotlin.String?
    if (b != null) {
        println(b.length)
    }

    // Safe Calls
    assertNull(b?.length)
    val len: Int? = b?.length
    // ob?.department?.head?.name

    val l3: Int = if (b != null) b.length else -1
    val l4: Int = b?.length ?: -1
    assertEquals(l3, l4)

    assertFails {
        val l5: Int = b?.length ?: throw IllegalStateException("error")
    }
    // The !! Operator
    // this will return a non-null value of b or throw an NPE if b is null
    assertFails { b!!.length }
}

fun test_operator_overloading() {
    //Unary operations
    /*
    +a a.unaryPlus()
    -a a.unaryMinus()
    !a a.not()
     */
    /*
    a++ a.inc() + see below
    a-- a.dec() + see below
    */

    /*
   // Binary operations
    a + b a.plus(b)
    a - b a.minus(b)
    a * b a.times(b)
    a / b a.div(b)
    a % b a.mod(b)
    a..b a.rangeTo(b)
     */

    /*
    a in b b.contains(a)
    a !in b !b.contains(a)
     */

    /*
    a[i] a.get(i)
a[i, j] a.get(i, j)
a[i_1, ..., i_n] a.get(i_1, ..., i_n)
a[i] = b a.set(i, b)
a[i, j] = b a.set(i, j, b)
a[i_1, ..., i_n] =
b a.set(i_1, ..., i_n, b)
     */

    /*
a(i) a.invoke(i)
a(i, j) a.invoke(i, j)
a(i_1, ...,
i_n) a.invoke(i_1, ...,
i_n)
     */
    /*
    a += b a.plusAssign(b)
a -= b a.minusAssign(b)
a *= b a.timesAssign(b)
a /= b a.divAssign(b)
     */

    var a = 1
    a += 2

    /*
    a == b a?.equals(b) ?: b === null
a != b !(a?.equals(b) ?: b ===
null)
     */

    /*
    a > b a.compareTo(b) > 0
a < b a.compareTo(b) < 0
a >=
b a.compareTo(b) >=
0
a <=
b a.compareTo(b) <= 0
     */
}

fun test_equal() {
    //Referential equality is checked by the === operation (and its negated counterpart !== ). a === b evaluates to true if
    //and only if a and b point to the same object

    //Structural equality is checked by the == operation (and its negated counterpart != )
    val a = "hello"
    val b = "hello"
    assertTrue { a == b }
    assertTrue { a === b }
    val c = String("hello".toByteArray())
    assertTrue { a == c }
    assertFalse { a === c }
}

fun test_this() {
    val b = A()
}

class A {
    //implicit label @A
    inner class B {
        fun Int.foo() {
            val a: A = this@A
            val b: B = this@B

            val c: Int = this // foo()'s receiver, an Int
            val c1: Int = this@foo // foo()'s receiver, an Int

            /*
            val funLit = @lambda {  String.() ->
                val d = this // funLit's receiver
                val d1 = this@lambda // funLit's receiver
            }
            */
            val funLit2 = { s: String ->
                val d1: Int = this
            }
        }
    }
}

fun test_type_checks_and_casts() {
    val obj = "hello"

    //smart cast
    if (obj is String) {
        println(""""$obj".length: $obj.length""")
    }

    // unsafe cast operator

    val s: String = obj as String

    assertFails { s as Number }

    val x: String? = obj as String?
    assertEquals(x, "hello")

    //"safe"(nullable) cast operator
    assertNull(s as? Number)
}

fun test_ranges() {
    /*
    Range expressions are formed with rangeTo functions that have the operator form .. which is complemented by in and
!in. Range is defined for any comparable type, but for integral primitive types it has an optimized implementation. Here are
some examples of using ranges
     */
    val i = 5
    if (i in 1..10) {
        println("$i in 1..10")
    }

    for (i in 1..4) println(i)
    for (i in (1..4).reversed()) println(i)
    for (i in 4 downTo 1) println(i)

    for (i in 1..4 step 2) println(i)
    for (i in 4 downTo 1 step 2) println(2)
}

fun test_destructuring_declarations() {
    val (name, age) = Person("itang", 100)
    //as
    val person = Person("itang", 100)
    val name2 = person.component1()
    val age2 = person.component2()
    assertEquals(name, "itang")
    assertEquals(name2, name)
    assertEquals(age, 100)

    // Note that the componentN() functions need to be marked with the operator keyword to allow using them in a
    // destructuring declaration.

    //
    val persons = arrayListOf(Person("itang", 100), Person("tqibm", 50))
    for ((name, age) in persons) {
        println("$name:$age")
    }

    data class Result(val result: Int, val status: Status)

    fun f(): Result {
        return Result(1, Status.OK)
    }
    assertEquals(f(), Result(1, Status.OK))

    //
    val m = mapOf("itang" to 100, "tqimb" to 20)
    for ((k, v) in m) {
        println("$k: $v")
    }

    /*
    operator fun <K, V> Map<K, V>.iterator(): Iterator<Map.Entry<K, V>> =
       entrySet().iterator()
    operator fun <K, V> Map.Entry<K, V>.component1() = getKey()
    operator fun <K, V> Map.Entry<K, V>.component2() = getValue()
     */
}

enum class Status {
    OK, ERROR
}

data class Person(val name: String, val age: Int)

fun test_higher_order_functions_lamdbas() {
    // A higher-order function is a function that takes functions as parameters, or returns a function
    fun <T> lock(lock: Lock, body: () -> T): T {
        lock.lock()
        try {
            return body()
        } finally {
            lock.unlock()
        }
    }

    var sharedResource = 1
    fun Int.operation() {

    }

    fun toBeSynchronized() = sharedResource.operation()
    val result = lock(ReentrantLock(), ::toBeSynchronized)

    val result2 = lock(ReentrantLock(), { sharedResource.operation() })

    // In Kotlin, there is a convention that if the last parameter to a function is a function, then we can omit the parentheses
    val result3 = lock(ReentrantLock()) {
        sharedResource.operation()
    }
    //Lambda expression
    // A lambda expression is always surrounded by curly braces.
    // Its parameters (if any) are declared before -> (parameter types may be omitted),
    // The body goes after -> (when present).

    fun <T, R> List<T>.map2(transform: (T) -> R): List<R> {
        val result = arrayListOf<R>()
        for (item in this) {
            result.add(transform(item))
        }
        return result
    }

    // A lambda expression or an anonymous function is a “function literal”,
    val doubled = listOf(1, 2, 3).map2 { it -> it * 2 }
    val doubled2 = listOf(1, 2, 3).map2 { it * 2 }
    assertEquals(doubled, doubled2)

    //Function types
    fun <T> max(collection: Collection<T>, less: (T, T) -> Boolean): T? {
        var max: T? = null
        for (it in collection)
            if (max == null || less(max, it))
                max = it
        return max
    }

    assertEquals(max(listOf(1, 2, 3), { x, y -> x < y }), 3)

    val less: (x: Int, y: Int) -> Boolean = { x, y -> x < y }

    assertEquals(max(listOf(1, 2, 3), less), 3)

    val sum = { x: Int, y: Int -> x + y }
    sum(1, 2)
    println(sum.javaClass.superclass) // class kotlin.jvm.internal.Lambda

    val ret = intArrayOf(1, 2, 3).filter { it > 2 }
    println(ret.javaClass)
    println(ret)

    // Anonymous Functions
    val f = fun(x: Int, y: Int): Int = x + y
    println(f.javaClass.superclass)

    val r2 = intArrayOf(1, 2, 3).filter (fun(item) = item > 0)
    println(r2)

    /*
    One other difference between lambda expressions and anonymous functions is the behavior of non-local returns. A return
statement without a label always returns from the function declared with the fun keyword. This means that a return inside
a lambda expression will return from the enclosing function, whereas a return inside an anonymous function will return
from the anonymous function itself.
     */

    //Closures
    // A lambda expression or anonymous function (as well as a local function and an object expression) can access its closure,

    var sum2 = 0
    intArrayOf(1, 2, 3, 4, 5).filter { it > 0 }.forEach {
        sum2 += it
    }
    println("sum: $sum2")

    // Function Literals with Receiver
    // Kotlin provides the ability to call a function literal with a specified receiver object.
    // Inside the body of the function literal, you can call methods on that receiver object
    // without any additional qualifiers. This is similar to extension functions, which allow
    // you to access members of the receiver object inside the body of the function. One of the most important examples of their usage is Type
    // safe Groovy-style builders.

    // sum : Int.(other: Int) -> Int
    val sum3 = fun Int.(other: Int): Int = this + other
    assertEquals(100.sum3(200), 300)

    class HTML {
        fun body() {
        }
    }

    fun html(init: HTML.() -> Unit): HTML {
        val html = HTML()
        html.init()
        return html
    }
    html {
        body()
    }

    // Inline Functions
    lock(ReentrantLock(), fun() = println("xx"))

    // The inline modifier affects both the function itself and the lambdas passed to it: all of those will be inlined into the call
    // site.

    /*
    fun foo() {
        ordinaryFunction {
        return // ERROR: can not make `foo` return here
        }
        }
     */
    /*

    fun foo() {
        inlineFunction {
            return // OK: the lambda is inlined
        }
    }
    */
    // But if the function the lambda is passed to is inlined, the return can be inlined as well, so it is allowed:
    // Such returns (located in a lambda, but exiting the enclosing function) are called non-local returns
    /*
    Note that some inline functions may call the lambdas passed to them as parameters not directly from the function body, but
from another execution context, such as a local object or a nested function. In such cases, non-local control flow is also not
allowed in the lambdas. To indicate that, the lambda parameter needs to be marked with the crossinline modifier
     */
    /*
        inline fun f(crossinline body: () -> Unit) {
            val f = object: Runnable {
                override fun run() = body()
            }
            // ...
        }
    */

    // Reified type parameters
    open class TreeNode {
        var parent: TreeNode? = null
    }

    class MyTreeNode() : TreeNode()

    fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
        var p = parent
        while (p != null && !clazz.isInstance(p)) {
            p = p?.parent
        }
        @Suppress("UNCHECKED_CAST")
        return p as T
    }

    val myTree = TreeNode()
    myTree.findParentOfType(MyTreeNode::class.java)

    // myTree.findParentOfType<MyTreeNodeType>()


    val myTree2 = TreeNode2()
    myTree2.findParentOfType2<MyTreeNode>()

    println(membersOf<TreeNode2>().joinToString("\n"))
}

open class TreeNode2 {
    var parent: TreeNode2? = null
}

class MyTreeNode2() : TreeNode2()

inline fun <reified T> membersOf() = T::class.members

inline fun <reified T> TreeNode2.findParentOfType2(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p?.parent
    }
    return p as? T
}

inline fun <T> lock(lock: Lock, body: () -> T): T {
    return body()
}

inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {
    // ...
}

fun test_functions_lambdas() {
    //functions in kotlin are declared using the fun keyword
    fun double(x: Int): Int {
        return x + x
    }

    assertEquals(double(100), 200)

    //Infix notation
    // Functions can also be called using infix notations when
    // they are memmber function or extension functions
    // they have a single parameter
    // they are marked with the infix keyword
    infix fun Int.d(x: Int): Int {
        return x * this
    }

    // call extension function using infix notation
    assertEquals(1 d 2, 2)
    assertEquals(2 d 3, 6)

    //
    fun powerOf(number: Int, exponent: Int) {

    }

    // Default Arguments
    read(arrayOf(1, 2, 3))

    // Named Arguments
    fun reformat(str: String, normalizeCase: Boolean = true, upperCaseFirstLetter: Boolean = true,
                 devideByCamelHumps: Boolean = false,
                 wordSeparator: Char = ' ') {

    }
    reformat("hello")

    reformat("hello", true, true, false, '_')

    reformat("hello",
            normalizeCase = true)

    // Unit-returning functions
    // if a function does not return any useful value, its return type is Unit. Unit is a type with only one value - Unit.
    // This value does not have to be returned explicityly.
    fun printHello(name: String?): Unit {
        if (name != null)
            println("Hello $name")
        else
            println("Hi there!")
        // `return Unit or return is optional
    }

    printHello("hello")

    // the Unit return type declaration is also ooptional. The above code is equivalent to
    fun printHello(name: String?) {

    }

    //Single-Expression functions
    // when a function returns a single expression, the curly braces can be omitted and the body is specified after a = symbol
    //
    fun double(x: Int): Int = x * 2

    //return type is optional when this can be inferred by the compiler
    fun double2(x: Int) = x * 2

    //Variable number of arguments(Varargs)
    fun <T> asList(vararg ts: T): List<T> {
        val result = ArrayList<T>()
        for (t in ts) {
            result.add(t)
        }
        return result
    }

    assertEquals(asList(1, 2, 3, 4), listOf(1, 2, 3, 4))


    val a = arrayOf(1, 2, 3)
    //spread operator
    val list = asList(-1, 0, *a, 4)
    assertEquals(list, listOf(-1, 0, 1, 2, 3, 4))

    //Function Scope
    //Local Functions
    // Kotlin supports local functions, i.e. a function inside another function


    fun dfs(graph: Graph) {
        fun dfs(current: Vertex, visited: Set<Vertex>) {
            //if(!visited.add(current)) return
        }
        dfs(Graph().vertices[0], HashSet())
    }

    //Local function can access local variables of outer functions(i.e. The Closure), so in the case above, the visibted can be a local
    // variable
    fun dfs2(graph: Graph) {
        val visited: Set<Vertex> = HashSet()
        fun dfs(current: Vertex) {
            //if(!visited.add(current)) return
        }
        dfs(Graph().vertices[0])
    }

    //Local functions can even return from outer functions using qualified return expressions
    fun reachable(from: Vertex, to: Vertex): Boolean {
        val visited = HashSet<Vertex>()
        fun dfs(current: Vertex) {
            // here we return from the outer function:
            //@NOTICE: why syntax error!
            // if (current == to) return@reachable true
            // And here -- from local function:
            if (!visited.add(current)) return
            //for (v in current.neighbors)
            //  dfs(v)
        }
        dfs(from)
        return false // if dfs() did not return true already
    }
    reachable(Graph().vertices[0], Graph().vertices[0])

    // Member Functions
    class Sample {
        fun foo() {
            println("Foo")
        }
    }
    Sample().foo()

    //Generic Functions
    fun <T> singletonList(item: T): List<T> {
        return listOf(item)
    }

    //inline Functions
    //Extension Functions
    //Higher-Order Functions and Lambdas

    //Tail recursive functions
    tailrec fun findFixPoint(x: Double = 1.0): Double
            = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))

    fun findFixPoint2(): Double {
        var x = 1.0
        while (true) {
            val y = Math.cos(x)
            if (x == y) return y
            x = y
        }
    }
}

class Vertex

class Graph {
    val vertices: Array<Vertex> = arrayOf(Vertex(), Vertex())
}


//@NOTICE: can't nested ?????????
fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size) {

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

inline fun <A> Boolean?.ifelse(te: () -> A, ee: () -> A): A? {
    return this?.let {
        if (it) {
            te()
        } else {
            ee()
        }
    }
}

inline fun <A> Boolean?.iftrue(te: () -> A): A? {
    return this.ifelse(te, { -> null })
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

        a.iftrue { assertTrue(true) }
        false.ifelse ({ throw RuntimeException("") }, { true })

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
        val ints = arrayOf(1, 2, 3, 5, 6, 3)
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
                if (it > 5) return@forEach // like break current iterate
                i += it
            }
            return i
        }
        assertEquals(foo3(), 1 + 2 + 3 + 5 + 3)

        //Alternatively, we can replace the lambda expression with an anonymous function. A return statement in an anomymous
        //function will return from the anonymous function itself.

        fun foo4(): Int {
            var i = 0
            ints.forEach(fun(it: Int) {
                if (it > 5) return  // just return current function
                i += it
            })
            return i
        }
        assertEquals(foo4(), 1 + 2 + 3 + 5 + 3)
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

fun <A, B> A.letTry(consumer: (A) -> B): B? {
    return this?.let {
        try {
            consumer(it)
        } catch(e: Exception) {
            println("WARN: ${e.message}")
            return null
        }
    }
}

fun test1() {
    assertNull(null.letTry { Integer.parseInt(it) })

    assertEquals(100, "100".letTry { Integer.parseInt(it) })

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
