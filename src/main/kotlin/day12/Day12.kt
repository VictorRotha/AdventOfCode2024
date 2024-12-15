package day12

import java.io.File

fun main() {

    val input = getInput()
    val visited = mutableListOf<Pair<Int, Int>>()

    var price = 0
    var discount = 0

    for (row in input.indices) {
        for (col in input[row].indices )
            if (Pair(col, row) !in visited) {

                var area = 0
                var peri = 0

                val group = mutableListOf<Pair<Int, Int>>()
                val queue = mutableListOf(Pair(col, row))

                while (queue.isNotEmpty()) {
                    val current = queue.removeLast()
                    val neighbours = getNeighbours(current, input)

                    queue.addAll(neighbours
                        .filter {it !in group && it !in queue })

                    group.add(current)
                    area++
                    peri += 4 - neighbours.size

                }

                visited.addAll(group)

                price += area * peri
                discount += area * getSize(group, input)
            }
    }

    println("Solution part 01: $price")
    println("Solution part 02: $discount")
}

fun getSize(group: MutableList<Pair<Int, Int>>, input: List<String>): Int {

    val n = mutableListOf<Pair<Int, Int>>()
    val s = mutableListOf<Pair<Int, Int>>()

    for (node in group) {
        listOf(
            Pair(node.first, node.second + 1),
            Pair(node.first, node.second - 1),
        ).filter {
            it !in group
        }.forEach {
            when (it.second) {
                node.second + 1 -> s.add(node)
                node.second - 1 -> n.add(node)
            }
        }
    }

    var total = 0

    total += getHorSides(n, input)
    total += getHorSides(s, input)

    return total * 2
}

fun getHorSides(borders : List<Pair<Int, Int>>, input: List<String>) : Int {

    var total = 0

    borders.groupBy() {
        it.second
    }.forEach { entry ->
        var sides = 1
        val l = entry.value.sortedBy { it.first }

        l.forEachIndexed { idx, pos ->
            if (idx != 0)
                if (pos.first > l[idx - 1].first + 1 ||
                    input[pos.second][pos.first] != input[l[idx - 1].second][l[idx - 1].first]
                )
                    sides++
        }
        total += sides
    }
    return total
}

fun getNeighbours(node: Pair<Int, Int>, input: List<String>): List<Pair<Int, Int>> {

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