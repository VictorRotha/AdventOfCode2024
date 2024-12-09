package day09

import java.io.File

fun main() {

    val input = getInput()

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

fun moveLeft(list : List<Int>, pos: Int) : Int {

    var posEnd = pos
    while (posEnd >= 0) {
        posEnd--
        if (list[posEnd] > -1)
            break
    }

    return posEnd


}


fun getInput(): String {

    val file = File("src/main/kotlin/day09/input.txt")

    return file.bufferedReader().readText().trim()
}