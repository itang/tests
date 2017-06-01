package demo

data class User(val name: String, val age: Int, val addr: String, val email: String)

fun userValidator(user: User): Validate<User> {
    return V.validator(user, ruleMode = RuleCheckMode.CONTINUE) {
        with(it.name, "user.name") {
            rule({ !it.isBlank() }, { " " })
            rule({ it != "itang" }, { "名字是敏感词" })
            rule({ it.length >= 6 }, { "名字长度应该大于或等于6 " })
        }

        with(it.age) {
            rule({ it in 1..199 }, { "年龄应该在 1 ~ 200 " })
        }

        rule({ it.addr.length > 10 }, { "地址长度大于10" })

        with(it.email) { rule(Functions.email, { "邮件地址不合法" }) }
    }
}

fun main(args: Array<String>) {
    val user = User("itang", -1, "ss", "bademail")
    val ret = userValidator(user).validate()

    printRet(ret)

    println("*".repeat(100))

    val user2 = User("tqibm.tang", 30, "123456789123", "tqibm@163.com")
    val ret2 = userValidator(user2).validate()

    printRet(ret2)
}

fun printRet(ret: ValidateResult) {
    when (ret) {
        Ok -> {
            println("validation ok")
        }
        is Error -> {
            print("validation error:")
            val s = ret.items.map { "${it.name ?: ""}: expected ${it.message}, but it is: ${it.target}" }
            s.forEach(::println)
        }
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
data class V<out T>(val target: T, val name: String? = null) {
    internal val list = mutableListOf<Validate<*>>()

    companion object {
        fun <T> validator(target: T, name: String? = null, ruleMode: RuleCheckMode = RuleCheckMode.BREAK, actions: V<T>.(t: T) -> Unit): Validate<T> {
            val b = V(target, name)
            b.actions(target)

            return group(target, name, b.list, ruleMode) as Validate<T>
        }
    }
}

fun <T, S> V<T>.with(target: S, name: String? = null, ruleMode: RuleCheckMode = RuleCheckMode.BREAK, actions: V<S>.(t: S) -> Unit) {
    this.list.add(V.validator(target, name, ruleMode, actions))
}

fun <T> V<T>.rule(pred: (T) -> Boolean, message: () -> String) {
    this.list.add(single(this.target, this.name, pred, message))
}

object Functions {
    fun matches(pattern: String) = matches(Regex(pattern))
    fun matches(regex: Regex): (String?) -> Boolean = { it?.let { regex.matches(it) } ?: false }

    fun doesNotMatch(pattern: String) = doesNotMatch(Regex(pattern))
    fun doesNotMatch(regex: Regex): (String?) -> Boolean = { !matches(regex)(it) }

    val notEmpty: (String?) -> Boolean = { !it.isNullOrEmpty() }
    val notBlank: (String?) -> Boolean = { !it.isNullOrBlank() }

    val alpha = matches("[a-zA-Z]+")
    val numeric = matches("[0-9]+")
    val alphanumeric = matches("[a-zA-Z0-9]+")

    val email = matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    fun length(value: String?, min: Int = 0, max: Int = Int.MAX_VALUE) = value?.let { it.length >= min && it.length <= max } ?: false
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
sealed class ValidateResult {
    abstract fun ok(): Boolean
}

object Ok : ValidateResult() {
    override fun ok() = true
}

data class FailItem<out T>(val target: T, val name: String?, val message: String)

data class Error(val items: List<FailItem<*>>) : ValidateResult() {
    override fun ok() = false
}

interface Validate<out T> {
    val target: T
    val name: String?
    fun validate(): ValidateResult
}

interface GroupValidation<out T> : Validate<T> {
    val rules: List<Validate<T>>
    val ruleMode: RuleCheckMode
}

enum class RuleCheckMode {
    BREAK,
    CONTINUE
}

data class Checker<in T>(val pred: (T) -> Boolean, val message: () -> String)

data class SingleValidate<T>(override val target: T, override val name: String?, val checker: Checker<T>) : Validate<T> {
    override fun validate(): ValidateResult {
        return if (!checker.pred(target)) {
            Error(listOf(FailItem(target, name, checker.message())))
        } else {
            Ok
        }
    }
}

abstract class BaseGroupValidation<out T>(override val target: T, override val name: String?, override val ruleMode: RuleCheckMode, override val rules: List<Validate<T>>) : GroupValidation<T>

class AnyBreakGroupValidation<out T>(override val target: T, override val name: String?, override val rules: List<Validate<T>>) : BaseGroupValidation<T>(target, name, RuleCheckMode.BREAK, rules) {
    override fun validate(): ValidateResult {
        for (rule in this.rules) {
            val ret = rule.validate()
            if (!ret.ok()) {
                return ret
            }
        }

        return Ok
    }
}

class AllThroughGroupValidation<out T>(override val target: T, override val name: String?, override val rules: List<Validate<T>>) : BaseGroupValidation<T>(target, name, RuleCheckMode.CONTINUE, rules) {
    override fun validate(): ValidateResult {
        val fails = mutableListOf<FailItem<*>>()
        for (rule in this.rules) {
            val ret = rule.validate()
            if (ret is Error) {
                fails.addAll(ret.items)
            }
        }

        if (fails.isEmpty()) {
            return Ok
        } else {
            return Error(fails)
        }
    }
}

fun <T> single(target: T, name: String?, pred: (T) -> Boolean, message: () -> String): SingleValidate <T> {
    return SingleValidate(target, name, Checker(pred, message))
}

fun <T> group(target: T, name: String?, validations: List<Validate<T>>, ruleMode: RuleCheckMode = RuleCheckMode.BREAK): GroupValidation <T> {
    return when (ruleMode) {
        RuleCheckMode.BREAK -> AnyBreakGroupValidation(target, name, validations)
        RuleCheckMode.CONTINUE -> AllThroughGroupValidation(target, name, validations)
    }
}
