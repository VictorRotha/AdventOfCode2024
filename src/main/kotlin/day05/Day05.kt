package day05

import java.io.File

fun main() {

    val file = File("src/main/kotlin/day05/input.txt")

    val rules = mutableMapOf<Int, MutableList<Int>>()
    val correctNumbers = mutableListOf<List<Int>>()
    val correctedNumbers = mutableListOf<List<Int>>()
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
            val numbers = line.split(",").map {it.toInt()}.toMutableList()
            var correct = false
            var corrected = false
            while (!correct) {
                correct = true
                for (i in 0 ..< numbers.size - 1) {
                    if (rules.containsKey(numbers[i + 1]) && numbers[i] in rules[numbers[i + 1]]!!) {
                        correct = false
                        corrected = true
                        numbers[i] = numbers[i+1].also { numbers[i + 1] = numbers[i] }
                    }
                }
            }

            if (!corrected)
                correctNumbers.add(numbers)
            else
                correctedNumbers.add(numbers)


        }
    }

    println("Solution part 01: ${correctNumbers.sumOf {it[it.size / 2] }}")
    println("Solution part 02: ${correctedNumbers.sumOf {it[it.size / 2] }}")

}


