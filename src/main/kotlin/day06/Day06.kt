package day06

import java.io.File

fun main() {

    val input = getInput()

    var pos = Pair(-1, -1)
    for (row in input.indices) {
        for (col in 0 ..< input[row].length) {
            if (input[row][col] == '^') {
                pos = Pair(col, row)
                break
            }

        }
    }

    val directions = listOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(-1, 0))
    var dir = 0
    val visited = mutableSetOf(pos)

    while (true) {
        val next = Pair(pos.first + directions[dir].first, pos.second + directions[dir].second)

        if (next.first < 0 || next.first >= input[0].length || next.second < 0 || next.second >= input.size)
            break

        if (input[next.second][next.first] != '#') {
            pos = next
            visited.add(pos)
        } else {
            dir = (dir + 1) % 4
        }


    }

    println("Solution part 01: ${visited.size}" )

}

fun getInput() : List<String> {

    val file = File("src/main/kotlin/day06/input.txt")

    return file.bufferedReader().readLines()
}