package day03

import java.io.File

fun main() {
    val input = getInput()

    part01(input)
    part02(input)

}

fun part02(input: String) {

    val regex = Regex("(mul[(][1-9][0-9]{0,2},[1-9][0-9]{0,2}[)]|don't)|do")
    var execute = true
    val sum = regex
        .findAll(input)
        .filter { match ->
            var predicate = false
            when (match.value) {
                "do" -> execute = true
                "don't" -> execute = false
                else -> predicate = execute
            }
            predicate
        }
        .map { match -> multiply(match.value) }
        .sum()

    println("Solution part 02: $sum")

}

fun part01(input: String) {
    val regex = Regex("mul[(][1-9][0-9]{0,2},[1-9][0-9]{0,2}[)]")

    val sum = regex
        .findAll(input)
        .map {match -> multiply(match.value) }
        .sum()

    println("Solution part 01: $sum")
}

fun multiply(match : String) : Int {

    return Regex("[0-9]+")
        .findAll(match)
        .map {it.value.toInt()}
        .reduce { a, b -> a * b }
}

fun getInput() : String {
    val file = File("src/main/kotlin/day03/input.txt")
    return file.bufferedReader().readText()
}