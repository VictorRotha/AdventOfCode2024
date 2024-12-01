package day01

import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {

    val file = File("src/main/kotlin/day01/example_input.txt")

    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    file.bufferedReader().forEachLine { line ->
        val entries = line.split("   ")
        list1.add(entries[0].toInt())
        list2.add(entries[1].toInt())
    }

    list1.sort()
    list2.sort()

    val part01 = IntRange(0, list1.size - 1).sumOf { i ->
        abs(list1[i] - list2[i])
    }

    println("Solution part 01: $part01")

    val frequency = mutableMapOf<Int, Int>()
    val part02 = list1.sumOf { n ->
        if (!frequency.containsKey(n))
            frequency[n] = list2.count { m -> m == n }
        n * frequency[n]!!
    }

    println("Solution part 02: $part02")

}


