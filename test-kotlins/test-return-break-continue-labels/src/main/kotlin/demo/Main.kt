package demo


// return. By default returns from the nearest enclosing function or anonymous function.
// break. Terminates the nearest enclosing loop
// continue. Proceeds to the next step of the nearest enclosing loop.

fun main(args: Array<String>) {
    testBreakFor()
    testContinue()
    testReturn()
}

fun testReturn() {
    fun foo(): Int {
        return 100;
    }

    assert(foo() == 100)

    fun foo2(): Int {
        (1..10).forEach {
            if (it > 5)
                return it
        }
        return 0
    }
    assert(foo2() == 6)

    var i = 0
    (0..10).forEach {
        if ( it == 0) return@forEach
        i = it
    }
    assert(i == 10)

    i = 0
    (0..10).forEach here@{
        if (it == 0) return@here
        i = it
    }
    assert(i == 10)

    i = 0
    (0..10).forEach(fun(it: Int) {
        if (it == 0) return
        i = it
    })
    assert(i == 10)
}

fun testContinue() {
    var ret = 0
    for (i in 0..10) {
        if (i > 5) continue
        ret = i
    }
    assert(ret == 5)

    ret = 0
    here@ for (i in 0..10) {
        for (j in 0..10) {
            if (i > 5) continue@here
            ret = i
        }
    }
    assert(ret == 5)
}

fun testBreakFor() {
    var ret = 0
    for (i in 0..10) {
        if (i > 5) break
        ret = i
    }

    assert(ret == 5)

    ret = 0
    here@ for (i in 0..10) {
        if (i > 5) break@here
        ret = i
    }
    assert(ret == 5)

    ret = 0
    here@ for (i in 0..10) {
        for (j in 0..10) {
            if (i > 5 && j > 2) break@here
            ret = i + j
        }
    }
    assert(ret == (6 + 2))
}


fun assert(cond: Boolean) {
    if (!cond) {
        throw RuntimeException("Assert failed!")
    }
}

