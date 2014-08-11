println "hello"

// list
def emptyList = []
assert emptyList.size() == 0

def list = [1, 2, 3, "hello", new java.util.Date()]
assert list.size() == 5

assert list.get(2) == 3

assert list.get(3) == "hello"

for (i in list) {
    println i
}

//map
def emptyMap = [:]
assert emptyList.isEmpty()

def map = ['name': 'James', 'location': 'London']
assert map.size() == 2
assert map.get('name') == 'James'
assert map['name'] == 'James'

for (entry in map) {
    println(entry.getKey() + ":" + entry.value)
}

//working with closures
def closure = { param -> println("Hello ${param}") }
closure.call("world")

//If no parameter(s) is(are) specified before -> symbol then a default named parameter, called 'it' can be used. e.g.
def closure1 = { println "hello, " + it }
closure1.call(100)

[1, 2, 3].each({ item -> print "${item}-" })
println()
["k1": "v1", 'k2': 'v2'].each { key, value -> println key + "=" + value }

def fun(int i, Closure c) {
    c.call(i)
}

[1, 2, 3].each() { item -> print "${item}-" }
println()

[1, 2, 3].each({ item -> print "${item}-" })
println()

[1, 2, 3].each { item -> print "${item}-" }
println()

[1, 2, 3].each {
    fun(it) {
        item -> println("${item}-")
    }
}
println()

def closure2 = { i -> print i }
["a", "b", "c"].each closure2
println()

//each
[1, 2, 3].each { item ->
    print "${item}-"
}
println()

//collect
def value = [1, 2, 3].collect { it * 2 }
assert value == [2, 4, 6]

// find
assert value.find { it > 1 } == 2

// findAll
assert value.findAll { it > 1 } == [2, 4, 6]

// inject
assert value.inject { item1, item2 -> item1 + item2 } == 2 + 4 + 6

assert value.inject(0) { count, item -> count + item.toString().length() } == 3

//every
assert value.every { it < 7 } == true

//any
assert value.any { it > 5 } == true

//max | min

assert value.max() == 6
assert value.min() == 2

//join
assert value.join() == "246"
assert value.join(",") == "2,4,6"

//conditional execution
a = Calendar.getInstance().get(Calendar.AM_PM)
println(a)

// for
10.times { print "${it}-" }
println()

for (i in 0..10) {
    print("$i-")
}
println()

for (i in 0..<10) {
    print("$i-")
}
println()

//Closure
def square = { it * it }
assert square(9) == 81

assert square.call(9) == 81

assert square.curry(9).call() == 81

def str = { s1, s2 -> s1 + s2 }

assert str.curry("hello")(",world") == "hello,world"

println([1, 2, 3, 10].collect(square))

// Regular Expressions
println "potatoe" ==~ /potatoe/

locationData = "Liverpool, England: 53d 25m 0s N 3d 0m 0s"
myRegularExpression = /([a-zA-Z]+), ([a-zA-Z]+): ([0-9]+). ([0-9]+). ([0-9]+). ([A-Z]) ([0-9]+). ([0-9]+). ([0-9]+)./

matcher = (locationData =~ myRegularExpression)

println matcher[0]

if (matcher.matches()) {
    println(matcher.getCount() + " occurrence of the regular expression was found in the string.");
    println(matcher[0][1] + " is in the " + matcher[0][6] + " hemisphere. (According to: " + matcher[0][0] + ")")
}

//
println "*" * 100

println(1 in [1, 2, 3])
println(10 in [1, 2, 3])

def name = "itang"
def text = """\
hello there ${name}
how are you today?
"""
println(text)

//
class Expandable {
    def storage = [:]

    def getProperty(String name) { storage[name] }

    void setProperty(String name, value) { storage[name] = value }
}

def e = new Expandable()
e.foo = "bar"
println e.foo
