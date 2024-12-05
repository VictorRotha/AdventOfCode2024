package day05

import java.io.File

fun main() {

    val file = File("src/main/kotlin/day05/input.txt")

    val rules = mutableMapOf<Int, MutableList<Int>>()
    val correctNumbers = mutableListOf<List<Int>>()
    var readRules = true
    file.bufferedReader().forEachLine {line ->

        if (line.isEmpty()) {
            readRules = false
            return@forEachLine
        }

        if (readRules) {
            val rule = line.split("|")
            val key = rule[0].toInt()

            rules.putIfAbsent(key, mutableListOf())
            rules[key]?.add(rule[1].toInt())

        } else {
            val numbers = line.split(",").map {it.toInt()}
            var correct = true
            for (i in 0 ..< numbers.size - 1) {
                if (rules.containsKey(numbers[i + 1]) && numbers[i] in rules[numbers[i + 1]]!!) {
                    correct = false
                    break
                }
            }
            if (correct)
                correctNumbers.add(numbers)
        }
    }

    val sum = correctNumbers.sumOf {
        it[it.size / 2]
    }

    println("sum: $sum")


}