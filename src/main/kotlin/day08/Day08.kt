package day08

import java.io.File

fun Pair<Int, Int>.isIn(size : Pair<Int, Int>) : Boolean {

    val x = this.first
    val y = this.second
    return (x >= 0 && x < size.first && y >= 0 && y < size.second)

}

fun main() {

    val input = getInput()
    val positions = getPositions(input)
    val size = getSize(input)

    val part01Nodes = mutableSetOf<Pair<Int, Int>>()
    val part02Nodes = mutableSetOf<Pair<Int, Int>>()
    for (entry in positions) {
        for (i in 0..< entry.value.size - 1) {
            for (j in i + 1 ..< entry.value.size) {
                part01Nodes.addAll(getAntinodesPart01(entry.value[i], entry.value[j], size))
                part02Nodes.addAll(getAntinodesPart02(entry.value[i], entry.value[j], size))
            }
        }
    }

    println("Solution part 01: ${part01Nodes.size}")
    println("Solution part 02: ${part02Nodes.size}")

}


fun getAntinodesPart02(p1: Pair<Int, Int>, p2 : Pair<Int, Int>, size: Pair<Int, Int>) : List<Pair<Int, Int>> {

    val dx = p1.first - p2.first
    val dy = p1.second - p2.second

    val result = mutableListOf<Pair<Int, Int>>()

    var current = p1
    while (current.isIn(size)) {
        result.add(current)
        current = Pair(current.first + dx, current.second + dy)
    }

    current = p2
    while (current.isIn(size)) {
        result.add(current)
        current = Pair(current.first - dx, current.second - dy)
    }

    return result
}


fun getAntinodesPart01(p1: Pair<Int, Int>, p2 : Pair<Int, Int>, size: Pair<Int, Int>) : List<Pair<Int, Int>> {

    val dx = p1.first - p2.first
    val dy = p1.second - p2.second

    return listOf(
        Pair(p1.first + dx, p1.second + dy),
        Pair(p2.first - dx, p2.second - dy)
    ).filter {it.isIn(size) }

}

fun getSize(input : List<String>) : Pair<Int, Int> {
    return Pair(input[0].length, input.size)
}

fun getPositions(input : List<String>) : Map<Char, List<Pair<Int, Int>>> {
    val pos = mutableMapOf<Char, List<Pair<Int, Int>>>()
    input.forEachIndexed() { row, line ->

        for (i in line.indices) {
            if (line[i] != '.') {
                pos[line[i]] = pos.getOrDefault(line[i], listOf()).toMutableList().also {
                    it.add(Pair(i, row))
                }
            }
        }
    }

    return pos
}

fun getInput(): List<String> {

    val file = File("src/main/kotlin/day08/input.txt")
    return file.bufferedReader().readLines()
}