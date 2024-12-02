package day01

import java.io.File
import kotlin.math.abs

fun main() {

    val file = File("src/main/kotlin/day01/input.txt")

    val (left, right) = file.bufferedReader()
        .readLines()
        .map {line ->
            val entries = line.split("   ")
            Pair(entries[0].toInt(), entries[1].toInt())
            }
        .unzip()

    val part01 = left.sorted()
        .zip(right.sorted())
        .sumOf { abs(it.first - it.second) }

    println("Solution part 01: $part01")

    val counts = right
        .groupBy { it }
        .mapValues { l -> l.value.count() }


    val part02 = left.sumOf { it * counts.getOrDefault(it, 0) }

    println("Solution part 02: $part02")

}


