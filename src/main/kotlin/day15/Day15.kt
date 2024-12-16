package day15

import java.io.File


fun Pair<Int, Int>.add(other: Pair<Int, Int>) :Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}


fun main() {


    val input = getInput()
    val map = input.split("\n\n")[0].split("\n").map { s -> s.toCharArray()}

    var pos = Pair(-1,-1)
    for (y in map.indices){
        for (x in map[y].indices) {
            if (map[y][x] == '@') {
                pos = Pair(x, y)
                map[y][x] = '.'
            }
        }
    }

    val commands = input.split("\n\n")[1].filter { it != '\n' }

    val directions = mapOf(
        '<' to Pair( -1, 0),
        '>' to Pair( 1, 0),
        '^' to Pair( 0, -1),
        'v' to Pair( 0, 1)
    )

    for (command in commands) {

        val delta = directions[command]

        val next = pos.add(delta!!)

        when (map[next.second][next.first]) {
            '#' -> {}
            '.' -> {pos = next}
            'O' -> {

                var move = false
                var box = next.copy()

                var c = 'O'
                while (c == 'O') {
                    box = box.add(delta)
                    c = map[box.second][box.first]
                    if  (c == '.') {
                        map[box.second][box.first] = 'O'
                        map[next.second][next.first] = '.'
                        move = true
                    }
                }

                if (move) {
                    pos = next
                }
            }

        }

    }

    println("Solution part 01: ${calculateGPS(map)}")

}

fun calculateGPS(map : List<CharArray>) : Int {

    var sum = 0
    for (y in map.indices){
        for (x in map[y].indices) {
            if (map[y][x] == 'O') {
                sum += 100 * y + x
            }
        }
    }
    return sum
}

fun printMap( map: List<CharArray>, pos: Pair<Int, Int>) {

    for (y in map.indices){
        for (x in map[y].indices) {
            if (pos == Pair(x,y))
                print('@')
            else
                print(map[y][x])
        }
        println()
    }

}

fun getInput(): String {

    val file = File("src/main/kotlin/day15/input.txt")

    return file.bufferedReader().readText()
}