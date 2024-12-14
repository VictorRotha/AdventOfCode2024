package day12

import java.io.File

fun main() {

    val input = getInput()
    val visited = mutableListOf<Pair<Int, Int>>()

    var price = 0

    for (row in input.indices) {
        for (col in input[row].indices )
            if (Pair(col, row) !in visited) {

                var area = 0
                var peri = 0

                val group = mutableListOf<Pair<Int, Int>>()
                val queue = mutableListOf(Pair(col, row))

                while (queue.isNotEmpty()) {
                    val current = queue.removeLast()
                    val neighbours = getNeighbours(current, input, group, queue)

                    queue.addAll(neighbours
                        .filter {it !in group && it !in queue })

                    group.add(current)
                    area++
                    peri += 4 - neighbours.size

                }

                visited.addAll(group)
                price += area * peri

            }

    }

    println("total price $price")

}

fun getNeighbours(node: Pair<Int, Int>, input: List<String>, visited: List<Pair<Int, Int>>, queue: List<Pair<Int, Int>>): List<Pair<Int, Int>> {

    val w = input[0].length
    val h = input.size

    val group = input[node.second][node.first]

    return listOf(
        Pair(node.first, node.second + 1),
        Pair(node.first, node.second - 1),
        Pair(node.first - 1, node.second),
        Pair(node.first + 1, node.second)
    ).filter {
        it.first in 0 ..< w  && it.second in 0 ..< h
    }.filter {
        input[it.second][it.first] == group
    }

}




fun getInput(): List<String> {

    val file = File("src/main/kotlin/day12/input.txt")

    return file.bufferedReader().readLines()
}