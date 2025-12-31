package old

import java.io.File
import java.security.MessageDigest

class Advent2015 {

    companion object {

        fun day1_1() { // 280
            val rawText =
                File("C:\\Users\\bala\\adventOfCode\\adventOfCode\\src\\main\\resources\\2015\\day1.txt").readText()
            val up = rawText.split("(").size - 1
            val down = rawText.split(")").size - 1

            print("2015 day 01.1: ${up - down}, ")
        }

        fun day1_2() { // 1797
            val rawText =
                File("C:\\Users\\bala\\adventOfCode\\adventOfCode\\src\\main\\resources\\2015\\day1.txt").readText()
            var index = 0
            var temp = 0
            var result = 0
            rawText.forEach {
                index++
                temp += if (it.toString() == "(") 1 else -1
                if (temp == -1 && result == 0) {
                    result = index
                }
            }
            print("2015 day 01.2: $result, ")
        }

        fun day2_1() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day2.txt").readLines()
            var result = 0
            rawText.forEach {
                val dimensions = it.split("x")
                val surface1 = 2 * dimensions[0].toInt() * dimensions[1].toInt()
                val surface2 = 2 * dimensions[0].toInt() * dimensions[2].toInt()
                val surface3 = 2 * dimensions[1].toInt() * dimensions[2].toInt()
                val smallest = listOf(surface1, surface2, surface3).sorted()[0] / 2
                result += surface1 + surface2 + surface3 + smallest
            }
            print("2015 day 02.1: $result, ")
        }

        fun day2_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day2.txt").readLines()
            var result = 0
            rawText.forEach {
                val dimensions = it.split("x")
                val x = dimensions[0].toInt()
                val y = dimensions[1].toInt()
                val z = dimensions[2].toInt()
                val sorted = listOf(x, y, z).sorted()
                result += sorted[0] + sorted[0] + sorted[1] + sorted[1]
                result += x * y * z
            }
            print("2015 day 02.2: $result, ")
        }

        fun day3_1() { // 3_1: 2081, 3_2 2341
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day3.txt").readText()

            val allVerticesSanta = mutableListOf<Pair<Int, Int>>()
            val allVerticesRobo = mutableListOf<Pair<Int, Int>>()

            allVerticesSanta.add(Pair(0, 0))
            allVerticesRobo.add(Pair(0, 0))
            fun addVertice(list: MutableList<Pair<Int, Int>>, direction: Char) {

                when (direction) {
                    '^' -> list.add(Pair(list.last().first, list.last().second + 1))
                    'v' -> list.add(Pair(list.last().first, list.last().second - 1))
                    '>' -> list.add(Pair(list.last().first + 1, list.last().second))
                    '<' -> list.add(Pair(list.last().first - 1, list.last().second))
                }

            }

            var toggle = true
            rawText.forEach { direction ->
                if (toggle) {
                    addVertice(allVerticesSanta, direction)
                } else {
                    addVertice(allVerticesRobo, direction)
                }
                toggle = !toggle
            }

            allVerticesSanta.addAll(allVerticesRobo)
            println("2015 day3.2: ${allVerticesSanta.toSet().size}")
        }

        fun day4_1() {
//            val rawText =
//                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day4.txt").readText()

            var result = 0
            val input = "yzbqklnj"
//            val leadingZeros = 0
            val md = MessageDigest.getInstance("MD5")
            for (i in 1..10000000) {
                val tmp = "$input$i"
                val digest = md.digest(tmp.toByteArray())
                val hash = digest.toHexString()
                if (hash.startsWith("000000")) {
                    result = i
                    break
                }
            }

            println("2015 day4.1: $result")
        }

        fun day5_1() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day5.txt").readLines()

            var result = 0
            val forbidden = listOf("ab", "cd", "pq", "xy")
            val regex = "([a-zA-Z])\\1+".toRegex()
            rawText.forEach { line ->
                if (forbidden.any { line.contains(it) }) {
                    println("line_1")
                    return@forEach
                }
                if (!regex.containsMatchIn(line)) {
                    println("line_2")
                    return@forEach
                }
                val tmp = line.replace("a", "").replace("e", "").replace("i", "").replace("o", "").replace("u", "")
                if (tmp.length + 3 > line.length) {
                    println("line_3: ${tmp.length},${line.length}")
                    return@forEach
                }
                result++
            }


            println("2015 day5.1: $result")
        }

        fun day5_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day5.txt").readLines()

            var result = 0
            val regex1 = "(.{2}).*\\1".toRegex()
            val regex2 = "(.)?(.)\\1".toRegex()
            rawText.forEach { line ->
                if (!regex1.containsMatchIn(line)) {
                    return@forEach
                }
                if (!regex2.containsMatchIn(line)) {
                    return@forEach
                }
                result++
            }


            println("2015 day5.2: $result")
        }

        fun day6_1() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day6.txt").readLines()
            var result: Int

            val matrix = Array(1000) { arrayOfNulls<Int>(1000) }
            fun getNumber(line: String, remove: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
                val startX = line.replace(remove, "").split("through")[0].trim().split(",")[0].toInt()
                val startY = line.replace(remove, "").split("through")[0].trim().split(",")[1].toInt()
                val endX = line.replace(remove, "").split("through")[1].trim().split(",")[0].toInt()
                val endY = line.replace(remove, "").split("through")[1].trim().split(",")[1].toInt()

                return Pair(startX, startY) to Pair(endX, endY)
            }

            fun turnOff(coordinates: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
                for (x in coordinates.first.first..coordinates.second.first) {
                    for (y in coordinates.first.second..coordinates.second.second) {
                        matrix[x][y] = null
                    }
                }
            }

            fun turnOn(coordinates: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
                for (x in coordinates.first.first..coordinates.second.first) {
                    for (y in coordinates.first.second..coordinates.second.second) {
                        matrix[x][y] = 1
                    }
                }
            }

            fun toggle(coordinates: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
                for (x in coordinates.first.first..coordinates.second.first) {
                    for (y in coordinates.first.second..coordinates.second.second) {
                        matrix[x][y] = if (matrix[x][y] == null) 1 else null
                    }
                }
            }

            val turnOff = "turn off"
            val turnOn = "turn on"
            val toggle = "toggle"
            rawText.forEach { line ->
                if (line.startsWith(turnOff)) {
                    turnOff(getNumber(line, turnOff))
                } else if (line.startsWith(turnOn)) {
                    turnOn(getNumber(line, turnOn))
                } else if (line.startsWith(toggle)) {
                    toggle(getNumber(line, toggle))
                }
            }

            result = matrix.flatMap { it.toList() }.count { it != null }
            println("2015 day6.1: $result")
        }

        fun day6_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day6.txt").readLines()

            val turnOff = "turn off"
            val turnOn = "turn on"
            val toggle = "toggle"
            var result: Long

            val matrix = Array(1000) { Array(1000) { 0 } }

            fun parseCoordinateOfCorner(line: String, remove: String, corner: Pair<Int, Int>): Int {
                return line.replace(remove, "").split("through")[corner.first].trim().split(",")[corner.second].toInt()
            }

            fun getCoordinates(line: String, remove: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
                val startX = parseCoordinateOfCorner(line, remove, Pair(0, 0))
                val startY = parseCoordinateOfCorner(line, remove, Pair(0, 1))
                val endX = parseCoordinateOfCorner(line, remove, Pair(1, 0))
                val endY = parseCoordinateOfCorner(line, remove, Pair(1, 1))

                return Pair(startX, startY) to Pair(endX, endY)
            }

            fun calcBulbLit(coordinates: Pair<Pair<Int, Int>, Pair<Int, Int>>, operation: String) {
                (coordinates.first.first..coordinates.second.first).forEach { x ->
                    (coordinates.first.second..coordinates.second.second).forEach { y ->
                        when (operation) {
                            turnOff -> matrix[x][y] = 0.coerceAtLeast(matrix[x][y] - 1)
                            turnOn -> matrix[x][y] += 1
                            toggle -> matrix[x][y] += 2
                        }
                    }
                }
            }

            rawText.forEach { line ->
                when {
                    line.startsWith(turnOff) -> calcBulbLit(getCoordinates(line, turnOff), turnOff)
                    line.startsWith(turnOn) -> calcBulbLit(getCoordinates(line, turnOn), turnOn)
                    line.startsWith(toggle) -> calcBulbLit(getCoordinates(line, toggle), toggle)
                }
            }

            result = matrix.flatMap { it.toList() }.sum().toLong()
            println("2015 day6.2: $result")
        }

        fun day7_1() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day7.txt").readLines()
                    .toMutableList()
            var result: Int
            val AND = "AND"
            val OR = "OR"
            val RSHIFT = "RSHIFT"
            val LSHIFT = "LSHIFT"
            val NOT = "NOT"

            val allWires = mutableMapOf<String, UShort>()
            val regexReadyWires = Regex("^\\d+ ->")
            var iterator = rawText.iterator()
            while (iterator.hasNext()) {
                val tmp = iterator.next()
                val matches = regexReadyWires.find(tmp)
                if (matches != null) {
                    println("put: ${matches.value}")
                    allWires[tmp.split(" -> ")[1]] = tmp.split(" -> ")[0].toUShort()
                    iterator.remove()
                }
            }
            fun calcAND(line: String, iterator: MutableIterator<String>) {
                println("$AND: $line")
                val operandA = line.split(" -> ")[0].split(" $AND ")[0]
                val operandB = line.split(" -> ")[0].split(" $AND ")[1]
                val assignTo = line.split(" -> ")[1]
                if (operandA.all { it.isDigit() } && operandB.all { it.isDigit() }) {
                    allWires[assignTo] = (operandA.toInt() and operandB.toInt()).toUShort()
                    iterator.remove()
                    return
                }
                if (operandA.all { it.isDigit() } && allWires[operandB] != null) {
                    allWires[assignTo] = (operandA.toInt() and allWires[operandB]!!.toInt()).toUShort()
                    iterator.remove()
                    return
                }
                if (allWires[operandA] != null && operandB.all { it.isDigit() }) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() and operandB.toInt()).toUShort()
                    iterator.remove()
                    return
                }
                if (allWires[operandA] != null && allWires[operandB] != null) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() and allWires[operandB]!!.toInt()).toUShort()
                    iterator.remove()
                }
            }

            fun calcRSHIFT(line: String, iterator: MutableIterator<String>) {
                println("$RSHIFT: $line")
                val operandA = line.split(" -> ")[0].split(" $RSHIFT ")[0]
                val operandB = line.split(" -> ")[0].split(" $RSHIFT ")[1].toInt()
                val assignTo = line.split(" -> ")[1]
                if (allWires[operandA] != null) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() shr operandB).toUShort()
                    iterator.remove()
                }
            }

            fun calcLSHIFT(line: String, iterator: MutableIterator<String>) {
                println("$LSHIFT: $line")
                val operandA = line.split(" -> ")[0].split(" $LSHIFT ")[0]
                val operandB = line.split(" -> ")[0].split(" $LSHIFT ")[1].toInt()
                val assignTo = line.split(" -> ")[1]
                if (allWires[operandA] != null) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() shl operandB).toUShort()
                    iterator.remove()
                }
            }

            fun calcOR(line: String, iterator: MutableIterator<String>) {
                println("$OR: $line")
                val operandA = line.split(" -> ")[0].split(" $OR ")[0]
                val operandB = line.split(" -> ")[0].split(" $OR ")[1]
                val assignTo = line.split(" -> ")[1]
                if (allWires[operandA] != null && allWires[operandB] != null) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() or allWires[operandB]!!.toInt()).toUShort()
                    iterator.remove()
                }
            }

            fun calcNOT(line: String, iterator: MutableIterator<String>) {
                println("$NOT: $line")
                val operandA = line.split(" -> ")[0].split(" ")[1]
                val assignTo = line.split(" -> ")[1]
                if (allWires[operandA] != null) {
                    allWires[assignTo] = allWires[operandA]!!.toInt().inv().toUShort()
                    iterator.remove()
                }
            }
            while (rawText.size > 1) {
                iterator = rawText.iterator()
                while (iterator.hasNext()) {
                    val tmp = iterator.next()
                    when {
                        tmp.contains(AND) -> calcAND(tmp, iterator)
                        tmp.contains(RSHIFT) -> calcRSHIFT(tmp, iterator)
                        tmp.contains(OR) -> calcOR(tmp, iterator)
                        tmp.contains(NOT) -> calcNOT(tmp, iterator)
                        tmp.contains(LSHIFT) -> calcLSHIFT(tmp, iterator)
                    }
                }
            }

            println(allWires)
            result = allWires["lx"]?.toInt() ?: -1
            println("2015 day7.1: $result")
        }


        fun day7_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day7.txt").readLines()
                    .toMutableList()
            var result: Int
            val AND = "AND"
            val OR = "OR"
            val RSHIFT = "RSHIFT"
            val LSHIFT = "LSHIFT"
            val NOT = "NOT"

            val allWires = mutableMapOf<String, UShort>()
            val regexReadyWires = Regex("^\\d+ ->")
            var iterator = rawText.iterator()
            while (iterator.hasNext()) {
                val tmp = iterator.next()
                val matches = regexReadyWires.find(tmp)
                if (matches != null) {
                    if (tmp.split(" -> ")[1] == "b") {
                        println("put: 16076")
                        allWires[tmp.split(" -> ")[1]] = 16076u
                        iterator.remove()
                    } else {
                        println("put: ${matches.value}")
                        allWires[tmp.split(" -> ")[1]] = tmp.split(" -> ")[0].toUShort()
                        iterator.remove()
                    }
                }
            }
            fun calcAND(line: String, iterator: MutableIterator<String>) {
                println("$AND: $line")
                val operandA = line.split(" -> ")[0].split(" $AND ")[0]
                val operandB = line.split(" -> ")[0].split(" $AND ")[1]
                val assignTo = line.split(" -> ")[1]
                if (operandA.all { it.isDigit() } && operandB.all { it.isDigit() }) {
                    allWires[assignTo] = (operandA.toInt() and operandB.toInt()).toUShort()
                    iterator.remove()
                    return
                }
                if (operandA.all { it.isDigit() } && allWires[operandB] != null) {
                    allWires[assignTo] = (operandA.toInt() and allWires[operandB]!!.toInt()).toUShort()
                    iterator.remove()
                    return
                }
                if (allWires[operandA] != null && operandB.all { it.isDigit() }) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() and operandB.toInt()).toUShort()
                    iterator.remove()
                    return
                }
                if (allWires[operandA] != null && allWires[operandB] != null) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() and allWires[operandB]!!.toInt()).toUShort()
                    iterator.remove()
                }
            }

            fun calcRSHIFT(line: String, iterator: MutableIterator<String>) {
                println("$RSHIFT: $line")
                val operandA = line.split(" -> ")[0].split(" $RSHIFT ")[0]
                val operandB = line.split(" -> ")[0].split(" $RSHIFT ")[1].toInt()
                val assignTo = line.split(" -> ")[1]
                if (allWires[operandA] != null) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() shr operandB).toUShort()
                    iterator.remove()
                }
            }

            fun calcLSHIFT(line: String, iterator: MutableIterator<String>) {
                println("$LSHIFT: $line")
                val operandA = line.split(" -> ")[0].split(" $LSHIFT ")[0]
                val operandB = line.split(" -> ")[0].split(" $LSHIFT ")[1].toInt()
                val assignTo = line.split(" -> ")[1]
                if (allWires[operandA] != null) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() shl operandB).toUShort()
                    iterator.remove()
                }
            }

            fun calcOR(line: String, iterator: MutableIterator<String>) {
                println("$OR: $line")
                val operandA = line.split(" -> ")[0].split(" $OR ")[0]
                val operandB = line.split(" -> ")[0].split(" $OR ")[1]
                val assignTo = line.split(" -> ")[1]
                if (allWires[operandA] != null && allWires[operandB] != null) {
                    allWires[assignTo] = (allWires[operandA]!!.toInt() or allWires[operandB]!!.toInt()).toUShort()
                    iterator.remove()
                }
            }

            fun calcNOT(line: String, iterator: MutableIterator<String>) {
                println("$NOT: $line")
                val operandA = line.split(" -> ")[0].split(" ")[1]
                val assignTo = line.split(" -> ")[1]
                if (allWires[operandA] != null) {
                    allWires[assignTo] = allWires[operandA]!!.toInt().inv().toUShort()
                    iterator.remove()
                }
            }
            while (rawText.size > 1) {
                iterator = rawText.iterator()
                while (iterator.hasNext()) {
                    val tmp = iterator.next()
                    when {
                        tmp.contains(AND) -> calcAND(tmp, iterator)
                        tmp.contains(RSHIFT) -> calcRSHIFT(tmp, iterator)
                        tmp.contains(OR) -> calcOR(tmp, iterator)
                        tmp.contains(NOT) -> calcNOT(tmp, iterator)
                        tmp.contains(LSHIFT) -> calcLSHIFT(tmp, iterator)
                    }
                }
            }

            println(allWires)
            result = allWires["lx"]?.toInt() ?: -1
            println("2015 day7.1: $result")
        }

        fun day8_1() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day8.txt").readLines()

            var allCharacters = 0
            var charsInMemory = 0
            val BACKSLASH = 92.toByte()
            val QUOTA = 34.toByte()
            val x = 120.toByte()
            rawText.forEach { line ->
                val raw = line.toByteArray().iterator()
                while(raw.hasNext()) {
                    allCharacters++
                    val tmp = raw.next()
                    if(tmp == QUOTA) {
                        continue
                    }
                    if(tmp == BACKSLASH){
                        allCharacters++
                        val next = raw.next()
                        if(next == x){
                            allCharacters += 2
                            raw.next()
                            raw.next()
                        }
                    }
                    charsInMemory++
                }
            }

            println("$charsInMemory: $allCharacters")
            println("2015 day8.1: ${allCharacters - charsInMemory}")
        }

        fun day8_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day8.txt").readLines()
            var result = 0

            println("2015 day8.2: $result")
        }

        fun advent2015() {
//            day1_1()
//            day1_2()
//            day2_1()
//            day2_2()
//            day3_1()
//            day4_1()
//            day5_1()
//            day5_2()
//            day6_1()
//            day6_2()
//            day7_1()
//            day7_2()
            day8_1()
//            day8_2()
        }
    }
}