package demo

fun main(args: Array<String>) {
    val td = listOf("2017-09-02", "2017-09-03", "2017-09-09", "2017-09-10", "2017-09-16", "2017-09-17", "2017-09-23", "2017-09-24")
    println("日期周末列表: $td\n")

    val dsList = listOf(
            listOf(
                    "2017-09-01",
                    "2017-09-04",
                    "2017-09-05"
            ),
            listOf(
                    "2017-09-24",

                    "2017-09-25",
                    "2017-09-26",
                    "2017-09-27",
                    "2017-09-28",
                    "2017-09-29",
                    "2017-09-30"
            ),
            listOf(
                    "2017-09-24",
                    "2017-09-25",
                    "2017-09-26",
                    "2017-09-27",

                    "2017-09-29",
                    "2017-09-30"
            ),
            listOf(
                    "2017-09-10",

                    "2017-09-11",
                    "2017-09-12",
                    "2017-09-13",
                    "2017-09-14",
                    "2017-09-15",
                    "2017-09-16", //exclude
                    "2017-09-18",
                    "2017-09-19"
            ),
            listOf(
                    "2017-09-11",
                    "2017-09-12",
                    "2017-09-14",
                    "2017-09-15",
                    "2017-09-16",
                    "2017-09-18",
                    "2017-09-19"
            ))

    dsList.forEachIndexed { index, ds ->
        println("$index: $ds")
        val range = getMaxNumberOfConsecutiveDateRange(ds, td)
        println("\t=>最大连续天数: ${range.size} with $range\n")
    }
}

/**
 * 获取最大连续日期范围.
 *
 * @param ds 日期样本,  日期格式: yyyy-MM-dd
 * @param td 自动连续的日期但不计入连续天数的数量中(如周末)
 */
fun getMaxNumberOfConsecutiveDateRange(ds: List<String>, td: List<String>): List<String> {
    val dsi = ds.sorted().distinct()
    val tdi = td.sorted().distinct()
    val all = (dsi + tdi).distinct().sorted()

    if (dsi.size <= 1) {
        return dsi
    }

    val rangeList = mutableListOf<List<String>>()
    var currRange = mutableListOf<String>()
    var i = 0
    while (i < all.size - 1) {
        val c = all[i]
        val n = all[i + 1]

        if (c !in tdi) {
            currRange.add(c)
        }

        val ni = n.replace("-", "").toInt()
        val ci = c.replace("-", "").toInt()
        when (ni) {
            ci + 1 -> { //相邻
                if (i == all.size - 2) { // 最后一次迭代
                    if (n !in tdi) {
                        currRange.add(n)
                    }
                }
            }
            else -> { //不相邻
                val range = mutableListOf<String>().apply { addAll(currRange) }
                rangeList.add(range)
                currRange = mutableListOf()
            }
        }
        i += 1
    }

    if (currRange.isNotEmpty()) {
        rangeList.add(currRange)
    }

    println("\t>> 连续范围列表: $rangeList")
    return rangeList.maxBy { it.size } ?: emptyList()
}
