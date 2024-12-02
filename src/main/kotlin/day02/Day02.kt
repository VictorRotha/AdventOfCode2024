package day02

import java.io.File
import kotlin.math.abs

fun main() {

    val file = File("src/main/kotlin/day02/input.txt")
    part01(file)
    part02(file)

}

fun part01(file: File) {
    var count = 0
    file.bufferedReader().forEachLine { line ->
        val report = line.split(" ").map { it.toInt() }
        if (isSafeReport(report))
            count++
    }

    println("Solution part 01: $count")
}

fun part02(file: File) {
    var count = 0
    file.bufferedReader().forEachLine { line ->
        val report = line.split(" ").map { it.toInt() }

        if (isSafeReport(report))
            count++
        else {
            for (i in report.indices) {
                val altered = report.filterIndexed { index, j ->
                    index != i
                }
                if (isSafeReport(altered)) {
                    count++
                    break
                }
            }
        }
    }

    println("Solution part 02: $count")

}

fun isSafeReport(report: List<Int>) : Boolean {

    val firstDelta = ((report[1] - report[0]))
    val dir = when {
        firstDelta > 0 -> 1
        firstDelta < 0 -> -1
        else -> 0
    }

    for (i in 1..<report.size) {

        val delta = report[i] - report[i - 1]
        if (!isSafeStep(delta, dir)) {
                return false
        }

    }

    return true
}

fun isSafeStep(delta : Int, dir: Int) : Boolean {

    if (delta == 0 || abs(delta) > 3) {
        return false
    } else if (delta > 0 && dir < 0) {
            return false
    } else if (delta < 0 && dir > 0) {
            return false
    }
    return true

}
