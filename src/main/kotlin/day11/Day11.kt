package day11

import java.io.File


fun main() {

    val input = getInput()

    var sum25 = 0L
    var sum75 = 0L
    for (n in input) {

        sum25 += blink(n, 25)
        sum75 += blink(n, 75)

    }
    println("Solution part 01: $sum25")
    println("Solution part 02: $sum75")
}


val cache: MutableMap<Pair<Long, Int>, Long> = mutableMapOf()

fun blink(n : Long, times: Int) : Long {

    if (times == 0)
        return 1

    if (Pair(n, times) in cache)
        return cache[Pair(n, times)]!!

    var sum = 0L
    for (m in process(n)) {
        sum += blink(m, times - 1)
    }
    cache[Pair(n, times)] = sum
    return sum

}

fun process(n : Long) : List<Long>{
    return when {
        n == 0L -> listOf(1L)
        n.toString().length % 2 == 0 ->  {
            val l = n.toString().length
            listOf(
                n.toString().substring(0, l/2).toLong(),
                n.toString().substring(l/2).toLong()
            )
        }
        else -> listOf(n * 2024L)
    }

}

fun getInput(): List<Long> {

    val file = File("src/main/kotlin/day11/input.txt")
    return file.bufferedReader().readLine().split(" ").map {it.toLong()}
}

