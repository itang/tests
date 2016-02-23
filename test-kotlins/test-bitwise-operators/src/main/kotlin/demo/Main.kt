package demo

import kotlin.test.*

object Options {
    val option1 = 1 shl 0
    val option2 = 1 shl 1
    val option3 = 1 shl 2
    val option4 = 1 shl 3
    val option5 = 1 shl 4

    fun hasOption(options: Int, option: Int): Boolean {
        return (options and option) == option
    }
}

fun main(args: Array<String>) {
    val options = Options.option1 or Options.option3 or Options.option5
    assertTrue { Options.hasOption(options, Options.option3) }
}
