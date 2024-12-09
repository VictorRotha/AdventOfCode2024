package day09

import java.io.File

fun main() {

    val input = getInput()
    part01(input)
    part02(input)




}

fun moveLeft(list : List<Int>, pos: Int) : Int {

    var posEnd = pos
    while (posEnd >= 0) {
        posEnd--
        if (list[posEnd] > -1)
            break
    }

    return posEnd


}

fun part01(input : String) {
    val list = input.toList().map { it.toString().toInt() }
        .mapIndexed() { idx, count ->
            val id = if (idx % 2 == 0) idx / 2 else -1
            val result = mutableListOf<Int>()
            for (i in 0..<count)
                result.add(id)
            result
        }
        .flatten()

    val newList = mutableListOf<Int>()
    var pos = 0
    var posEnd = list.size - 1

    while (pos <= posEnd) {
        if (list[pos] == -1) {
            newList.add(list[posEnd])
            posEnd = moveLeft(list, posEnd)

        } else {
            newList.add(list[pos])
        }
        pos++
    }

    var sum = 0L
    for (i in 0 ..<newList.size) {
        sum += i * newList[i]
    }

    println("Solution part 01: $sum")
}

fun part02(input : String) {

    val list = input.toList()
        .map { it.toString().toInt() }
        .mapIndexed() { idx, count ->
            val id = if (idx % 2 == 0) (idx / 2) else -1
            Pair(id, count)
            }
        .filter { it.second != 0 }

    val newList = list.toMutableList()
    var pos = newList.size-1
    while(pos > 0) {
        if (newList[pos].first == -1)
            pos--
        val l = newList[pos].second
        val space = findSpace(newList, l, pos)
        if (space != null) {
            newList[space.first] = newList[pos]
            newList[pos] = Pair(-1, l)
            if (l < space.second) {
                newList.add(space.first + 1, Pair(-1, space.second - l))
                pos++
            }
        }
        pos--
    }

    var idx = 0
    var sum = 0L
    for (block in newList) {
        if (block.first != -1)
            for (i in 0..<block.second)
                sum += block.first * (idx + i)
        idx += block.second
    }

    println("Solution part 02: $sum")
}

fun findSpace(list: List<Pair<Int, Int>>, l : Int, maxIdx : Int) : Pair<Int,Int>? {

    for (i in 0 .. maxIdx) {
        if (list[i].first == -1 && list[i].second >= l)
            return Pair(i, list[i].second)
    }
    return null
}

fun getInput(): String {

    val file = File("src/main/kotlin/day09/input.txt")
    return file.bufferedReader().readText().trim()
}