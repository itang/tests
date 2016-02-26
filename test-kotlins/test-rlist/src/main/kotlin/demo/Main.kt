package demo

class Node<T>(val value: T, var next: Node<T>?) {
    fun add_next(next: Node<T>): Node<T>? {
        this.next = next

        return this.next
    }
}

object Nodes {
    fun <T> each(node: Node<T>?, f: (T) -> Unit) {
        if (node != null) {
            f(node.value)
            each(node.next, f)
        }
    }

    fun <T> reverse_each(node: Node<T>?, f: (T) -> Unit) {
        if (node != null) {
            reverse_each(node.next, f)
            f(node.value)
        }
    }

    fun <T> reverse(node: Node<T>?): Node<T>? {
        var prev: Node<T>? = null
        var curr = node
        while (curr != null) {
            var next = curr.next // 记住当前节点的下一个节点
            curr.next = prev // 当前节点的next指向上一个值
            prev = curr // 新的root移到当前节点
            curr = next  // 当前节点替换为其下一个节点，构成迭代语义
        }//最后一个节点之后，跳出循环（next为空）

        return prev
    }
}

fun main(args: Array<String>) {
    val first: Node<Int> = Node(1, null)
    val second: Node<Int> = Node(2, null)
    val three: Node<Int> = Node(3, null)
    first?.add_next(second)?.add_next(three)

    Nodes.each(first) { println(it) }

    println("++++++++++++")
    Nodes.reverse_each(first) { println(it) }

    println("++++++++++++")
    Nodes.each(Nodes.reverse(first)) { println(it) }
}
