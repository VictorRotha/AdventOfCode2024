package day06

import java.io.File

fun main() {

    val input = getInput()
    val start = getStartPos(input)!!

    part01(input, start)
    part02(input, start)

}


fun part02(input: List<String>, start: Pair<Int, Int>) {

    val loops = runGuard(start, input)?.filter {
        if (it == start)
            return@filter false
        runGuard(start, input, it) == null
    }?.count()

    println("Solution part 02: $loops")
}


fun part01(input: List<String>, start: Pair<Int, Int>)  {

    val result = runGuard(start, input)?.size ?: -1

    println("Solution part 01: $result" )

}


fun runGuard(start : Pair<Int, Int>, input : List<String>, obstacle: Pair<Int, Int>? = null) : Set<Pair<Int, Int>>? {

    val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))
    var dir = 0
    val visited = mutableSetOf(start)
    var pos = start

    var counter = 0

    while (counter < 100) {

        val next = Pair(pos.first + directions[dir].first, pos.second + directions[dir].second)

        if (next.first < 0 || next.first >= input[0].length || next.second < 0 || next.second >= input.size)
            return visited

        if (input[next.second][next.first] == '#' || (obstacle != null && next == obstacle)) {
            dir = (dir + 1) % 4
        } else {
            pos = next
            counter = if (next in visited) counter + 1 else 0
            visited.add(pos)
        }
    }

    return null
}


fun getStartPos(input: List<String>): Pair<Int, Int>? {
    for (row in input.indices)
        for (col in 0 ..< input[row].length)
            if (input[row][col] == '^')
                return Pair(col, row)
    return null
}

fun getInput() : List<String> {

    val file = File("src/main/kotlin/day06/input.txt")
    return file.bufferedReader().readLines()
}