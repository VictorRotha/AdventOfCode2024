package day13

import java.io.File
import kotlin.math.min

fun main() {

    val machines = getInput()
    var total = 0
    for (m in machines) {
        val cost = findCheapest(m)
        if (cost > 0)
            total += cost
    }
    println("Solution part 01: $total")


}

fun findCheapest(m: Machine): Int {

    var cheapest : Int = -1
    for (i in 0 .. m.px / m.ax) {
        val rest = m.px - i * m.ax
        if (rest % m.bx != 0)
            continue

        val j = rest / m.bx
        if (m.py != i * m.ay + j * m.by)
            continue

        val cost = 3 * i + j

        cheapest = if (cheapest == -1) cost else min(cheapest, cost)
    }

return cheapest

}

fun getInput(): List<Machine> {

    val file = File("src/main/kotlin/day13/input.txt")

    val result = mutableListOf<Machine>()

    var ax : Int = -1
    var ay : Int = -1
    var bx : Int = -1
    var by : Int = -1
    var px : Int = -1
    var py : Int = -1

    file.bufferedReader().forEachLine {line ->

        when {
            line.startsWith("Button A") -> {
                val xy = line.split(": ")[1].split(", ")
                ax = xy[0].split("+")[1].toInt()
                ay = xy[1].split("+")[1].toInt()
            }
            line.startsWith("Button B") -> {
                val xy = line.split(": ")[1].split(", ")
                bx = xy[0].split("+")[1].toInt()
                by = xy[1].split("+")[1].toInt()
            }
            line.startsWith("Prize") -> {
                val xy = line.split(": ")[1].split(", ")
                px = xy[0].split("=")[1].toInt()
                py = xy[1].split("=")[1].toInt()
            }
            line.isEmpty() -> result.add(Machine(ax, ay, bx, by, px, py))
        }


    }
    result.add(Machine(ax, ay, bx, by, px, py))

    return result
}


data class Machine(
    val ax : Int,
    val ay : Int,
    val bx : Int,
    val by : Int,
    val px : Int,
    val py : Int

)

