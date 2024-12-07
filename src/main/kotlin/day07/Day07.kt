package day07

import java.io.File
import kotlin.math.pow


fun main() {
    part01()
    part02()
}

fun combinations(n : Int) : List<String> {
    var data : MutableList<String> = mutableListOf("0", "1", "2")

    for (i in 0 ..< n-1) {
        val result = mutableListOf<String>()
        for (s in data) {
            result.add("${s}0")
            result.add("${s}1")
            result.add("${s}2")
        }
        data = result
    }

    return data

}

fun getInput() : Map<Long, List<Int>>{

    val file = File("src/main/kotlin/day07/input.txt")
    val input = mutableMapOf<Long, List<Int>>()
    file.bufferedReader().lines().forEach {line ->
        val key = line.split(":")[0].toLong()
        val value = line.split(":")[1].trim().split(" ").map {it.toInt()}
        input[key] = value
    }

    return input

}

fun part02() {


    val input = getInput()

    var total = 0L
    for (product in input.keys) {
        val numbers = input[product]
        val n = numbers!!.size - 1
        val combinations = combinations(n)

        for (combi in combinations) {
            var result = numbers[0].toLong()
            for (b in 1 ..< numbers.size) {
                if (combi[b-1] == '0')
                    result *= numbers[b]
                else if (combi[b-1] == '1')
                    result += numbers[b]
                else
                    result = (result.toString() + numbers[b].toString()).toLong()
            }

            if (result == product) {
                total += result
                break
            }
        }
    }

    println("Solution part 02: $total")

}

fun part01() {

    val input = getInput()

    var total = 0L
    for (product in input.keys) {
        val numbers = input[product]
        val n = numbers!!.size - 1

        for (i in 0 ..< 2.0.pow(n).toInt()) {
            val combi = Integer.toBinaryString(i).padStart(n, '0')
            var result = numbers[0].toLong()
            for (b in 1 ..< numbers.size) {
                if (combi[b-1] == '0')
                    result *= numbers[b]
                else
                    result += numbers[b]
            }

            if (result == product) {
                total += result
                break
            }

        }
    }

    println("Solution part 01: $total")
}
