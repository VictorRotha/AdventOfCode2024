package day04


import java.io.File

fun main() {
    part01()
    part02()
}

fun part02() {

    val input = getInput()

    var sum = 0
    for (y in input.indices) {

        for (x in input[y].indices) {
            val c = input[y][x]
            if (c == 'A' && checkX(x, y, input)) {
                sum++
            }
        }
    }

    println("Solution part 02: $sum")

}

fun checkX(x: Int, y: Int, input: List<String>) : Boolean {

    if (y < 1 || y > input.size - 2)
       return false

    if (x < 1 || x > input[y].length - 2)
        return false

    val neighbours = listOf(
        input[y-1][x-1],
        input[y-1][x+1],
        input[y+1][x-1],
        input[y+1][x+1],
    ).joinToString("")

    val validNeighbours = listOf("MSMS", "MMSS", "SSMM", "SMSM")

    return validNeighbours.contains(neighbours)
}


fun part01() {

    val input = getInput()

    var sum = 0
    sum += findXMAS(input)

    val vertical = rotate90(input)
    sum += findXMAS(vertical)

    val diagonal = rotate45(input)
    sum += findXMAS(diagonal)

    val diag2 = rotate45(vertical)
    sum += findXMAS(diag2)

    println("Solution part 01: $sum")


}


fun rotate45(input: List<String>) : List<String> {

    val result = mutableListOf<MutableList<String>>()
    val width = input[0].length

    for (i in 0 ..< width ) {

        result.add(mutableListOf())
        var col = i
        var row = 0
        while (col < width && row < input.size) {

            result[i].add(input[row][col].toString())
            col++
            row++

        }
    }


    for (i in 1 ..< input.size ) {

        val temp = mutableListOf<String>()

        var col = 0
        var row = i
        while (col < width && row < input.size) {
            temp.add(input[row][col].toString())
            col++
            row++
        }
        result.add(temp)
    }

        return result.map { list -> list.joinToString("") }

}

fun rotate90(input: List<String>) : List<String> {

    val result = mutableListOf<MutableList<String>>()

    for (i in 0 ..< input[0].length ) {

        result.add(mutableListOf())

        for (j in input.indices.reversed()) {

            result[i].add(input[j][i].toString())
        }
    }

    return result.map { list -> list.joinToString("") }


}

fun findXMAS(input: List<String>) : Int {

    var result = 0
    for (s in input) {
        result +=  Regex("XMAS").findAll(s).count()
        result +=  Regex("SAMX").findAll(s).count()
    }
    return result


}

fun getInput() : List<String> {
    val file = File("src/main/kotlin/day04/input.txt")

    val input = mutableListOf<String>()
    file.bufferedReader().forEachLine {
        input.add(it)
    }
    return input
}
