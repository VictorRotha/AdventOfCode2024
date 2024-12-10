package day10

import java.io.File

fun main() {

    val input = getInput()
    var score = 0
    var rating = 0

    for (row in input.indices) {
        for (col in input[row].indices) {
            if (input[row][col] == '0') {
                score += getScore(input, Pair(col, row))
                rating += getRating(input, Pair(col, row))
            }
        }
    }

    println("Part01 score: $score")
    println("Part02 rating: $rating")
}

fun getRating(input: List<String>, head: Pair<Int, Int>) : Int {

    var level = 0
    var nodes = mutableListOf(head)

    while (level < 9 && nodes.isNotEmpty()) {
        val newNodes = mutableListOf<Pair<Int, Int>>()
        for (node in nodes) {
            newNodes.addAll(getNeighbours(input, level + 1, node))
        }
        nodes = newNodes
        level++
    }

    return nodes.size
}

fun getScore(input: List<String>, head: Pair<Int, Int>) : Int {

    var level = 0
    var nodes = mutableSetOf(head)

    while (level < 9 && nodes.isNotEmpty()) {
        val newNodes = mutableSetOf<Pair<Int, Int>>()
        for (node in nodes) {
            newNodes.addAll(getNeighbours(input, level + 1, node))
        }
        nodes = newNodes
        level++
    }

    return nodes.size
}

fun getNeighbours(input : List<String>, level: Int, pos: Pair<Int, Int>) : List<Pair<Int, Int>> {

    return listOf(
        Pair(pos.first + 1, pos.second),
        Pair(pos.first - 1, pos.second),
        Pair(pos.first, pos.second + 1),
        Pair(pos.first, pos.second - 1),
    ).filter {
        val x = it.first
        val y = it.second
        x >= 0 && y >= 0 && x < input[0].length && y < input.size
    }.filter {
        input[it.second][it.first].toString().toInt() == level
    }



}

fun getInput(): List<String> {

    val file = File("src/main/kotlin/day10/input.txt")

    return file.bufferedReader().readLines()
}