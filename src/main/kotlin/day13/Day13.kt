package day13

import java.io.File
import kotlin.math.min

fun main() {

    getInput()

    val machines = getInput()
    part01(machines)
    part02(machines)
}

fun part02(input : List<List<Int>>) {

    //Px = A * ax + B * bx
    //Py = A * ay + B * by
    //cost = 3 * A + B

    //A = (Pxby - Pybx) / (axby - aybx)
    //B = (Pyax - Pxay) / (axby - aybx)

    var total = 0L
    for (machine in input) {

        val ax = machine[0]
        val ay = machine[1]
        val bx = machine[2]
        val by = machine[3]
        val px = machine[4] + 10000000000000L
        val py = machine[5] + 10000000000000L

        val d = (ax * by - ay * bx)
        val a = (px * by - py * bx)
        val b = (py * ax - px * ay)

        if (a % d == 0L && (b % d) == 0L) {
            val A = a / d
            val B = b / d
            total += 3 * A + B
        }
    }
    println("Solution part 02: $total")


}

fun part01(input : List<List<Int>>) {
    var total = 0
    for (machine in input) {
        val cost = findCheapest(machine)
        if (cost > 0)
            total += cost
    }

    println("Solution part 01: $total")

}

fun findCheapest(m: List<Int>): Int {

    val ax = m[0]
    val ay = m[1]
    val bx = m[2]
    val by = m[3]
    val px = m[4]
    val py = m[5]

    var cheapest : Int = -1
    for (i in 0 .. px / ax) {
        val rest = px - i * ax
        if (rest % bx != 0)
            continue

        val j = rest / bx
        if (py != i * ay + j * by)
            continue

        val cost = 3 * i + j

        cheapest = if (cheapest == -1) cost else min(cheapest, cost)
    }

return cheapest

}

fun getInput() : List<List<Int>> {

    val file = File("src/main/kotlin/day13/input.txt")
    val result = mutableListOf<List<Int>>()
    file.bufferedReader().readText().split("\n\n").forEach {block ->
        result.add(Regex("[0-9]+").findAll(block)
            .map { it.value.toInt() }
            .toList())
    }
    return result

}


