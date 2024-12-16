package day14

import java.io.File

fun main() {

    val input = getInput()
    val n = 100
    val w = 101
    val h = 103

    val quads = mutableListOf(0, 0, 0, 0)

    input.map { start -> step(start, n, w, h) }
        .forEach {robot ->
            when {
                robot[0] < w / 2 && robot[1] < h/2 -> quads[0]++
                robot[0] < w / 2 && robot[1] > h/2 -> quads[1]++
                robot[0] > w / 2 && robot[1] < h/2 -> quads[2]++
                robot[0] > w / 2 && robot[1] > h/2 -> quads[3]++
            }
        }

    println("Solution part 01: ${quads.reduce { acc, i -> acc * i }}")

}

fun step(robot: List<Int>, n : Int, w: Int, h: Int) : List<Int> {

    var x = (robot[0] + n * robot[2]) % w
    var y = (robot[1] + n * robot[3]) % h

    x = if (x >= 0) x else w + x
    y = if (y >= 0) y else h + y

    return listOf(x % w, y % h, robot[2], robot[3])

}

fun getInput(): List<List<Int>> {

    val file = File("src/main/kotlin/day14/input.txt")

    return file.bufferedReader().readLines().map{line ->
        Regex("[-0-9]+").findAll(line).map { it.value.toInt() }.toList()
    }


}