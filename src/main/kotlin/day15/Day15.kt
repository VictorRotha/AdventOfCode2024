package day15

import java.io.File


fun Pair<Int, Int>.add(other: Pair<Int, Int>) :Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}

fun List<CharArray>.getCharAt(pos : Pair<Int, Int>) : Char {
    return this[pos.second][pos.first]
}
fun List<CharArray>.setCharAt(pos : Pair<Int, Int>, c: Char) {
    this[pos.second][pos.first] = c
}

val directions = mapOf(
    '<' to Pair( -1, 0),
    '>' to Pair( 1, 0),
    '^' to Pair( 0, -1),
    'v' to Pair( 0, 1)
)


fun main() {


    val input = getInput()

    val mapInput = input.split("\n\n")[0].split("\n")
    val commands = input.split("\n\n")[1].filter { it != '\n' }

    part01(mapInput, commands)
    part02(mapInput, commands)

}

fun convertMap(original : List<String>) :  Pair<Pair<Int, Int>, List<CharArray>> {


    var start = Pair(-1,-1)
    val map = original
        .map { s -> s.toCharArray()}
        .mapIndexed { j, s ->
            s.mapIndexed() { i, c ->
                when (c) {
                    '.' -> ".."
                    '#' -> "##"
                    'O' -> "[]"
                    '@' -> {
                        start = Pair(i * 2, j)
                        ".."
                    }
                    else -> ""
                }
            }
                .joinToString("")
        }
        .map { s -> s.toCharArray() }

    return Pair(start, map)

}

fun moveHorizontal(map: List<CharArray>, next: Pair<Int, Int>, delta: Pair<Int, Int>) : Boolean {

    var box = next.copy()
    var c = map.getCharAt(box)
    while (c == '[' || c == ']') {
        box = box.add(delta)
        c = map.getCharAt(box)

        if  (c == '.') {

            val start = if (delta.first > 0) next.first + 1 else box.first
            val end  = if (delta.first > 0) box.first else next.first - 1

            var sign = '['
            for (i in start .. end) {
                map[box.second][i] = sign
                sign = if (sign == '[') ']' else '['
            }

            map.setCharAt(next, '.')
            return true
        }
    }

    return false
}

fun moveVertical(map: List<CharArray>, next: Pair<Int, Int>, delta: Pair<Int, Int>) : Boolean {


    val c = map.getCharAt(next)
    var boxes = listOf(next, next.add(if (c == '[') Pair(1, 0) else Pair(-1,0 )) )
    val nbBoxes = boxes.toMutableList()
    while (boxes.isNotEmpty()) {
        val nbs = mutableListOf<Pair<Int, Int>>()

        for (b in boxes) {
            val nb = b.add(delta)
            when (map.getCharAt(nb)) {
                '#' -> return false
                '[', ']' -> {
                    nbs.add(nb)
                }
            }
        }

        if (nbs.isEmpty()) {
            moveBoxes(nbBoxes, map, delta)
            return true
       }

        boxes = nbs.toMutableList()
        for (b in nbs) {
            val part = b.add(if (map.getCharAt(b) == '[') Pair(1, 0) else Pair(-1, 0))
            if (part !in boxes)
                boxes.add(part)
        }
        nbBoxes.addAll(boxes)
    }

    return false
}

fun moveBoxes(boxes: List<Pair<Int, Int>>, map: List<CharArray>, delta: Pair<Int, Int>) {

    val result = mutableMapOf<Pair<Int, Int>, Char>()

    for (b in boxes)
        result[b] = '.'

    for (b in boxes) {
        result[b.add(delta)] = map.getCharAt(b)
    }

    for (pos in result.keys) {
        map.setCharAt(pos, result[pos]!!)
    }

}

fun part02(original : List<String>, commands: String) {

    val res = convertMap(original)
    var pos = res.first
    val map = res.second

    for (command in commands) {

        val delta = directions[command]

        val next = pos.add(delta!!)
        val c = map.getCharAt(next)

        when (c) {
            '#' -> {}
            '.' -> {pos = next}
            '[', ']' -> {

                val move = if (delta.second == 0) {
                    moveHorizontal(map, next, delta)
                } else {
                    moveVertical(map, next, delta)
                }

                if (move)
                    pos = next

            }
        }
    }

    println("Solution part 02: ${calculateGPS(map, '[')}")
}


fun part01(mapInput: List<String>, commands: String) {

    val map = mapInput.map { s -> s.toCharArray()}

    var pos = Pair(-1,-1)
    for (y in map.indices){
        for (x in map[y].indices) {
            if (map[y][x] == '@') {
                pos = Pair(x, y)
                map[y][x] = '.'
            }
        }
    }

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
                    c = map.getCharAt(box)
                    if  (c == '.') {
                        map.setCharAt(box, 'O')
                        map.setCharAt(next, '.')
                        move = true
                    }
                }

                if (move) {
                    pos = next
                }
            }
        }
    }

    println("Solution part 01: ${calculateGPS(map, 'O')}")
}


fun calculateGPS(map : List<CharArray>, c : Char) : Int {

    var sum = 0
    for (y in map.indices){
        for (x in map[y].indices) {
            if (map[y][x] == c) {
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