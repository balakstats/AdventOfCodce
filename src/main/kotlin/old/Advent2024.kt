package old

import Utils.Companion.calculateGCD
import Utils.Companion.isReportSafe
import Utils.Companion.readIntCsv
import Utils.Companion.readStringCsv
import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import java.io.File
import java.lang.Math.floorMod
import java.util.stream.IntStream
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow


class Advent2024 {
    companion object {
        fun day1_1() { // 2264607
            val list1 =
                readIntCsv("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day1_list1.csv").sorted()
            val list2 =
                readIntCsv("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day1_list2.csv").sorted()
                    .toSet()
            var result = 0
            list1.zip(list2).forEach { pair ->
                result += abs(pair.component1() - pair.component2())
            }
            println("2024 day 01.1: $result")
        }

        fun day1_2() { // 19457120
            val list1 =
                readIntCsv("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day1_list1.csv")
            val list2 =
                readIntCsv("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day1_list2.csv")
            val commonList = list1.intersect(list2)
            val frequencies = list1.groupingBy { it }.eachCount()
            val filteredFrequencies = frequencies.filterKeys { commonList.contains(it) }
            var result = 0
            filteredFrequencies.forEach { (key, value) ->
                result += (key * value)
            }
            println("2024 day 01.2: $result")
        }

        fun day2_1() { // 269
            val rawList =
                readStringCsv("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day2_list1.csv")
            val reducedList =
                rawList.map { line -> line.split(" ").map { it.toInt() } }.filter { it.size == it.toSet().size }
            val steadyIncreasingList = reducedList.filter { it.sorted() == it }
            val steadyDecreasingList = reducedList.filter { it.sortedDescending() == it }

            var result = 0
            fun inner(list: List<List<Int>>): Int {
                var temp = 0
                list.forEach {
                    temp += if (isReportSafe(it)) 1 else 0
                }
                return temp
            }

            result += inner(steadyIncreasingList)
            result += inner(steadyDecreasingList)

            println("2024 day 02.1: $result")
        }

        fun day2_2() { // 337
            val numberOfSafeReports = 269 // safe reports
            val rawSet =
                readStringCsv("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day2_list1.csv").toSet()
            val reducedSet =
                rawSet.map { line -> line.split(" ").map { it.toInt() } }
                    .filter { abs(it.size - it.toSet().size) < 2 }.toSet()

            val reducedSetByDoubles =
                rawSet.map { line -> line.split(" ").map { it.toInt() } }.filter { it.size == it.toSet().size }.toSet()
            val steadyIncreasingList = reducedSetByDoubles.filter { it.sorted() == it }
            val steadyDecreasingList = reducedSetByDoubles.filter { it.sortedDescending() == it }
            fun inner(list: List<List<Int>>): Set<List<Int>> {
                val temp = mutableSetOf<List<Int>>()
                list.forEach {
                    if (isReportSafe(it)) {
                        temp.add(it)
                    }
                }
                return temp
            }

            val safeReports = mutableSetOf<List<Int>>()
            safeReports.addAll(inner(steadyIncreasingList))
            safeReports.addAll(inner(steadyDecreasingList))
            val reducedSetBySafeReports = reducedSet.filter { !safeReports.contains(it) }

            var result = numberOfSafeReports
            reducedSetBySafeReports.forEach {
                val length = it.size
                for (i in 0 until length) {
                    val mutableList = it.toMutableList()
                    mutableList.removeAt(i)
                    if (mutableList.size > mutableList.toSet().size) {
                        continue
                    }
                    if (mutableList.sorted() != mutableList && mutableList.sortedDescending() != mutableList) {
                        continue
                    }
                    if (isReportSafe(mutableList)) {
                        result++
                        break
                    }
                }
            }

            println("2024 day 02.2: $result")
        }

        fun day3_1() { // 170807108
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day3.txt").readText()
            val regex = """mul\([0-9]{1,3},[0-9]{1,3}\)""".toRegex()
            val matchResults = regex.findAll(rawText, 0)
            var result = 0
            matchResults.forEach {
                val numbers = it.value.replace("mul(", "").replace(")", "").split(",")
                result += numbers[0].toInt() * numbers[1].toInt()
            }
            println("2024 day 03.1: $result")
        }

        fun day3_2() { // 74838033
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day3.txt").readText()
            val dontSections = rawText.split("don't()")
            val regex = """mul\([0-9]{1,3},[0-9]{1,3}\)""".toRegex()
            var result = 0
            var loop = 0
            dontSections.forEach { outerIt ->
                if (loop == 0) {
                    loop++
                    val matches = regex.findAll(outerIt, 0)
                    matches.forEach {
                        val numbers = it.value.replace("mul(", "").replace(")", "").split(",")
                        result += numbers[0].toInt() * numbers[1].toInt()
                    }
                }
                val doSection = outerIt.indexOf("do()")
                if (doSection >= 0) {
                    val process = outerIt.substring(doSection)
                    val matches = regex.findAll(process, 0)
                    matches.forEach {
                        val numbers = it.value.replace("mul(", "").replace(")", "").split(",")
                        result += numbers[0].toInt() * numbers[1].toInt()
                    }
                }
            }
            println("2024 day 03.2: $result")
        }

        fun day4_1() { // 2514
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day4.txt").readLines()
            val lineLength = rawText[0].length
            val xmas = "XMAS"
            val samx = "SAMX"
            var result = 0

            val horizontalForward = rawText.sumOf { it.split(xmas).size - 1 }
            val horizontalBackward = rawText.sumOf { it.split(samx).size - 1 }
            result += horizontalForward + horizontalBackward

            val verticalLines: MutableList<String> = MutableList(lineLength) { "" }
            rawText.forEach {
                it.forEachIndexed { index, element ->
                    verticalLines[index] = verticalLines[index] + element
                }
            }
            val verticalForward = verticalLines.sumOf { it.split(xmas).size - 1 }
            val verticalBackward = verticalLines.sumOf { it.split(samx).size - 1 }
            result += verticalForward + verticalBackward

            fun getDiagonalLinesFromText(rawText: List<String>): MutableList<String> {
                val leftToRightDIagonalLines: MutableList<String> = MutableList((lineLength * 2) - 1) { "" }
                rawText.forEachIndexed { indexOuter, s ->
                    s.forEachIndexed { indexInner, c ->
                        if (indexOuter == 0) {
                            leftToRightDIagonalLines[indexInner] = c.toString()
                        } else {
                            if (indexInner == 0) {
                                leftToRightDIagonalLines[lineLength - 1 + indexOuter] = c.toString()
                            } else {
                                if (indexOuter == indexInner) {
                                    leftToRightDIagonalLines[0] = leftToRightDIagonalLines[0] + c.toString()
                                } else if (indexOuter < indexInner) {
                                    leftToRightDIagonalLines[indexInner - indexOuter] =
                                        leftToRightDIagonalLines[indexInner - indexOuter] + c.toString()
                                } else {
                                    leftToRightDIagonalLines[indexOuter - indexInner + lineLength - 1] =
                                        leftToRightDIagonalLines[indexOuter - indexInner + lineLength - 1] + c.toString()
                                }
                            }
                        }
                    }
                }
                return leftToRightDIagonalLines
            }

            val diagonalLeftToRight = getDiagonalLinesFromText(rawText)
            val diagonalLeftToRightForward = diagonalLeftToRight.sumOf { it.split(xmas).size - 1 }
            val diagonalLeftToRightBackward = diagonalLeftToRight.sumOf { it.reversed().split(xmas).size - 1 }
            result += (diagonalLeftToRightForward + diagonalLeftToRightBackward)

            val diagonalRightToLeft = getDiagonalLinesFromText(rawText.map { it.reversed() }.toList())
            val diagonalRightToLeftForward = diagonalRightToLeft.sumOf { it.split(xmas).size - 1 }
            val diagonalRightToLeftBackward = diagonalRightToLeft.sumOf { it.split(samx).size - 1 }
            result += (diagonalRightToLeftForward + diagonalRightToLeftBackward)
            println("2024 day 04.1: $result")
        }

        fun day4_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day4.txt").readLines()
            val lineLength = rawText[0].length
            var result = 0

            rawText.forEachIndexed { indexOuter, _ ->
                if (indexOuter > lineLength - 3) {
                    return@forEachIndexed
                }
                for (i in 0..lineLength - 3) {
                    if (rawText[indexOuter + 1].substring(i + 1).startsWith("A")) {
                        if (rawText[indexOuter].substring(i).startsWith("M") &&
                            rawText[indexOuter].substring(i + 2).startsWith("M") &&
                            rawText[indexOuter + 2].substring(i).startsWith("S") &&
                            rawText[indexOuter + 2].substring(i + 2).startsWith("S")
                        ) {
                            result += 1
                            continue
                        }

                        if (rawText[indexOuter].substring(i).startsWith("S") &&
                            rawText[indexOuter].substring(i + 2).startsWith("S") &&
                            rawText[indexOuter + 2].substring(i).startsWith("M") &&
                            rawText[indexOuter + 2].substring(i + 2).startsWith("M")
                        ) {
                            result += 1
                            continue
                        }

                        if (rawText[indexOuter].substring(i).startsWith("M") &&
                            rawText[indexOuter].substring(i + 2).startsWith("S") &&
                            rawText[indexOuter + 2].substring(i).startsWith("M") &&
                            rawText[indexOuter + 2].substring(i + 2).startsWith("S")
                        ) {
                            result += 1
                            continue
                        }

                        if (rawText[indexOuter].substring(i).startsWith("S") &&
                            rawText[indexOuter].substring(i + 2).startsWith("M") &&
                            rawText[indexOuter + 2].substring(i).startsWith("S") &&
                            rawText[indexOuter + 2].substring(i + 2).startsWith("M")
                        ) {
                            result += 1
                            continue
                        }
                    }
                }
            }
            println("2024 day 04.2: $result")
        }

        fun day5_1() { // 5964
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day5.txt").readLines()
            val pages = rawText.subList(0, 1176)
            val updates = rawText.subList(1177, rawText.size)
            var result = 0
            val mapWithDuplicateKeys = mutableListOf<Pair<String, String>>()
            pages.forEach {
                mapWithDuplicateKeys.add(it.split("|")[0] to it.split("|")[1])
            }
            updates.forEach line@{ line ->
                val updateList = line.split(",")
                val size = updateList.size
                var i = 0
                updateList.reversed().forEach { s ->
                    if (size - i++ == 0) {
                        return@line
                    }
                    val toBeMatched = updateList.subList(0, size - i)
                    val matchingPages = mapWithDuplicateKeys.filter { it.first == s }
                    if (matchingPages.any { toBeMatched.contains(it.second) }) {
                        return@line
                    }
                }
                result += updateList[(size / 2)].toInt()
            }
            println("2024 day 05.1: $result")
        }

        fun day5_2() { // 4719
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day5.txt").readLines()
            val pages = rawText.subList(0, 1176)
            val updates = rawText.subList(1177, rawText.size)
            var result = 0
            val mapWithDuplicateKeys = mutableListOf<Pair<String, String>>()
            val incorrectUpdates = mutableListOf<List<String>>()
            pages.forEach {
                mapWithDuplicateKeys.add(it.split("|")[0] to it.split("|")[1])
            }
            updates.forEach line@{ line ->
                val updateList = line.split(",")
                val size = updateList.size
                var i = 0
                updateList.reversed().forEach { s ->
                    if (size - i++ == 0) {
                        return@line
                    }
                    val toBeMatched = updateList.subList(0, size - i)
                    val matchingPages = mapWithDuplicateKeys.filter { it.first == s }
                    if (matchingPages.any { toBeMatched.contains(it.second) }) {
                        incorrectUpdates.add(updateList)
                        return@line
                    }
                }
            }

            incorrectUpdates.forEachIndexed incorrectList@{ _, list ->
                var toBeFIxed = list.toMutableList()
                while (true) {
                    var swapFirstIndex = -1
                    var swapSecondIndex = -1
                    run loop@{
                        toBeFIxed.forEachIndexed { indexInner, element ->
                            if (indexInner == toBeFIxed.size - 1) {
                                result += toBeFIxed[(toBeFIxed.size / 2)].toInt()
                                return@incorrectList
                            }
                            if (swapFirstIndex > -1) {
                                return@loop
                            }
                            val subList = toBeFIxed.subList(indexInner + 1, toBeFIxed.size)
                            subList.forEach { first ->
                                val matchingPairs =
                                    mapWithDuplicateKeys.filter { it.first == first }.filter { it.second == element }
                                if (matchingPairs.size > 0) {
                                    swapFirstIndex = indexInner
                                    swapSecondIndex = toBeFIxed.indexOf(matchingPairs.get(0).first)
                                }
                            }
                        }
                    }
                    if (swapFirstIndex > -1) {
                        val tmp = toBeFIxed[swapFirstIndex]
                        toBeFIxed[swapFirstIndex] = toBeFIxed[swapSecondIndex]
                        toBeFIxed[swapSecondIndex] = tmp
                    }
                }
            }
            println("2024 day 05.2: $result")
        }

        fun day6_1(): MutableSet<Pair<Int, Int>> { // 4454
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day6.txt").readLines()
            var direction = 0 // 0:up, 1:right, 2:down, 3:left
            var x = 0
            var y = 0
            rawText.forEachIndexed { index, line ->
                if (line.contains("^")) {
                    x = line.indexOf("^")
                    y = index
                }
            }
            val visited = mutableSetOf(Pair(x, y))
            while (true) {
                if ((((direction % 4) == 0) && y == 0) || (((direction % 4) == 3) && x == 0)) {
                    break
                }
                if ((((direction % 4) == 2) && y == rawText.size - 1) || (((direction % 4) == 1) && x == rawText[0].length - 1)) {
                    break
                }
                when (direction % 4) {
                    0 -> if (rawText[y - 1][x] != '#') y-- else direction++
                    1 -> if (rawText[y][x + 1] != '#') x++ else direction++
                    2 -> if (rawText[y + 1][x] != '#') y++ else direction++
                    3 -> if (rawText[y][x - 1] != '#') x-- else direction++
                }
                visited.add(Pair(x, y))
            }
            println("2024 day 06.1: ${visited.size}")
            return visited
        }

        fun day6_2() { // 1503
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day6.txt").readLines()
            val nrOfLines = rawText.size
            val lengthOfLine = rawText[0].length
            var startX = 0
            var startY = 0
            rawText.forEachIndexed { index, line ->
                if (line.contains("^")) {
                    startX = line.indexOf("^")
                    startY = index
                }
            }
            val loopDetected = mutableListOf<Pair<Int, Int>>()
            val visited = day6_1()
            visited.forEachIndexed loop@{ indexOuter, pair ->
                if (indexOuter == 0) {
                    return@loop
                }

                val newRawText = rawText.toMutableList()
                val newLineWithObstacle = newRawText[pair.second].toCharArray()
                newLineWithObstacle[pair.first] = '#'
                newRawText[pair.second] = newLineWithObstacle.concatToString()
                var direction = 0 // 0:up, 1:right, 2:down, 3:left
                var x = startX
                var y = startY
                val visitedPositions = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
                while (true) {
                    if ((((direction % 4) == 0) && y == 0) || (((direction % 4) == 3) && x == 0)) {
                        break
                    }
                    if ((((direction % 4) == 2) && y == nrOfLines - 1) || (((direction % 4) == 1) && x == lengthOfLine - 1)) {
                        break
                    }
                    when (direction % 4) {
                        0 -> if (newRawText[y - 1][x] != '#') y-- else direction++
                        1 -> if (newRawText[y][x + 1] != '#') x++ else direction++
                        2 -> if (newRawText[y + 1][x] != '#') y++ else direction++
                        3 -> if (newRawText[y][x - 1] != '#') x-- else direction++
                    }
                    val sizeBefore = visitedPositions.size
                    visitedPositions.add(Pair(Pair(x, y), (direction % 4)))
                    val sizeAfter = visitedPositions.size
                    if (sizeBefore == sizeAfter) {
                        loopDetected.add(Pair(x, y))
                        break
                    }
                }
            }
            println("2024 day 06.2: ${loopDetected.size}")
        }

        fun day7_1() { // 12839601725877
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day7.txt").readLines()
            var solvedEquations = 0L
            rawText.forEach loop@{ line ->
                val splitted = line.split(": ")
                val result = splitted[0].toLong()
                val numbers = splitted[1].split(" ").map { it.toLong() }.toList()
                if (numbers.sum() == result) {
                    solvedEquations += result
                    return@loop
                }
                if (numbers.reduce { acc, i -> acc * i }.toLong() == result) {
                    solvedEquations += result
                    return@loop
                }

                val operators = mutableListOf<String>()
                for (i in 1 until 2.0.pow(numbers.size.toDouble() - 1).toInt() - 1) {
                    operators.add(Integer.toBinaryString(i).padStart(numbers.size - 1, '0'))
                }
                operators.forEach inner@{
                    var tmp = numbers[0]
                    it.forEachIndexed { index, c ->
                        if (c == '0') {
                            tmp += numbers[index + 1]
                        } else {
                            tmp *= numbers[index + 1]
                        }
                        if (tmp > result) {
                            return@inner
                        }
                    }
                    if (tmp == result) {
                        solvedEquations += result
                        return@loop
                    }
                }
            }
            println("2024 day 07.1: $solvedEquations")
        }

        fun day7_2() { // 149956401519484
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day7.txt").readLines()
            var solvedEquations = 0L
            rawText.forEach loop@{ line ->
                val splitted = line.split(": ")
                val result = splitted[0].toLong()
                val numbers = splitted[1].split(" ").map { it.toLong() }.toList()
                if (numbers.sum() == result) {
                    solvedEquations += result
                    return@loop
                }
                if (numbers.reduce { acc, i -> acc * i }.toLong() == result) {
                    solvedEquations += result
                    return@loop
                }

                val operators = mutableListOf<String>()
                for (i in 1 until 2.0.pow(numbers.size.toDouble() - 1).toInt() - 1) {
                    operators.add(Integer.toBinaryString(i).padStart(numbers.size - 1, '0'))
                }
                operators.forEach inner1@{
                    var tmp = numbers[0]
                    it.forEachIndexed { index, c ->
                        if (c == '0') {
                            tmp += numbers[index + 1]
                        } else {
                            tmp *= numbers[index + 1]
                        }
                        if (tmp > result) {
                            return@inner1
                        }
                    }
                    if (tmp == result) {
                        solvedEquations += result
                        return@loop
                    }
                }

                for (i in 0 until numbers.size) {
                    val operatorsNew = mutableListOf<String>()
                    for (j in 0 until 3.0.pow(numbers.size.toDouble() - 1).toInt()) {
                        operatorsNew.add(Integer.toUnsignedString(j, 3).padStart(numbers.size - 1, '0'))
                    }

                    operatorsNew.forEach inner2@{
                        var tmp = numbers[0]
                        it.forEachIndexed { index, c ->
                            if (c == '0') {
                                tmp += numbers[index + 1]
                            }
                            if (c == '1') {
                                tmp *= numbers[index + 1]
                            }
                            if (c == '2') {
                                tmp = "$tmp${numbers[index + 1]}".toLong()
                            }

                            if (tmp > result) {
                                return@inner2
                            }
                        }
                        if (tmp == result) {
                            solvedEquations += result
                            return@loop
                        }
                    }
                }
            }
            println("2024 day 07.2: $solvedEquations")
        }

        fun day8_1() { // 329
            val rawTextLines =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day8.txt").readLines()
            val lineLength = rawTextLines[0].length
            val numberOfLines = rawTextLines.size
            val coordinates = mutableSetOf<Pair<Pair<Int, Int>, String>>()
            rawTextLines.forEachIndexed { indexLine, line ->
                line.forEachIndexed { indexChar, c ->
                    if (c != '.') {
                        coordinates.add(Pair(Pair(indexChar, indexLine), c.toString()))
                    }
                }
            }
            val antiNodes = mutableSetOf<Pair<Int, Int>>()
            val antennas = coordinates.groupBy { it.second }
            antennas.forEach outer@{ (_, u) ->
                u.forEachIndexed { index, pair ->
                    if (index == u.size - 1) {
                        return@outer
                    }
                    for (i in index + 1 until u.size) {
                        val xDiff =
                            if (pair.first.first == u[i].first.first) 0 else pair.first.first - u[i].first.first
                        val yDiff =
                            if (pair.first.second == u[i].first.second) 0 else u[i].first.second - pair.first.second
                        if (yDiff < 0) {
                            println("ALARM Y")
                            return
                        }

                        val antiNodeXFirst = pair.first.first + xDiff
                        val antiNodeYFirst = pair.first.second - yDiff
                        if (antiNodeXFirst in 0 until lineLength && antiNodeYFirst >= 0) {
                            antiNodes.add(Pair(antiNodeXFirst, antiNodeYFirst))
                        }

                        val antiNodeXSecond = u[i].first.first - xDiff
                        val antiNodeYSecond = u[i].first.second + yDiff
                        if (antiNodeXSecond in 0 until lineLength && antiNodeYSecond < numberOfLines) {
                            antiNodes.add(Pair(antiNodeXSecond, antiNodeYSecond))
                        }
                    }
                }
            }
            println("2024 day 08.1: ${antiNodes.size}")
        }

        fun day8_2() { //1147 - 1217,1214
            val rawTextLines =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day8.txt").readLines()
            val lineLength = rawTextLines[0].length
            val numberOfLines = rawTextLines.size
            val coordinates = mutableSetOf<Pair<Pair<Int, Int>, String>>()
            rawTextLines.forEachIndexed { indexLine, line ->
                line.forEachIndexed { indexChar, c ->
                    if (c != '.') {
                        coordinates.add(Pair(Pair(indexChar, indexLine), c.toString()))
                    }
                }
            }
            val antiNodes = mutableSetOf<Pair<Int, Int>>()
            val antennas = coordinates.groupBy { it.second }
            antennas.forEach outer@{ (_, u) ->
                u.forEachIndexed { index, pair ->
                    if (index == u.size - 1) {
                        antiNodes.add(Pair(u[index].first.first, u[index].first.second))
                        return@outer
                    }
                    for (i in index + 1 until u.size) {
                        val xDiff =
                            if (pair.first.first == u[i].first.first) 0 else pair.first.first - u[i].first.first
                        val yDiff =
                            if (pair.first.second == u[i].first.second) 0 else u[i].first.second - pair.first.second
                        if (yDiff < 0) {
                            println("ALARM Y")
                            return
                        }
                        if (xDiff == 0) {
                            println("ALARM XDiff=0")
                            return
                        }
                        if (yDiff == 0) {
                            println("ALARM YDiff=0")
                            return
                        }
                        antiNodes.add(Pair(pair.first.first, pair.first.second))
                        antiNodes.add(Pair(u[i].first.first, u[i].first.second))

                        val gcd = calculateGCD(
                            abs(pair.first.first - u[i].first.first),
                            abs(pair.first.second - u[i].first.second)
                        )
                        val normalizedXDiff = xDiff / gcd
                        val normalizedYDiff = yDiff / gcd

                        val stepUp = Pair(pair.first.first + normalizedXDiff, pair.first.second - normalizedYDiff)
                        var antiNodeXFirst = stepUp.first
                        var antiNodeYFirst = stepUp.second
                        while (antiNodeXFirst in 0 until lineLength && antiNodeYFirst >= 0) {
                            antiNodes.add(Pair(antiNodeXFirst, antiNodeYFirst))
                            antiNodeXFirst += normalizedXDiff
                            antiNodeYFirst -= normalizedYDiff
                        }

                        val stepDown = Pair(pair.first.first - normalizedXDiff, pair.first.second + normalizedYDiff)
                        antiNodeXFirst = stepDown.first
                        antiNodeYFirst = stepDown.second
                        while (antiNodeXFirst in 0 until lineLength && antiNodeYFirst < numberOfLines) {
                            antiNodes.add(Pair(antiNodeXFirst, antiNodeYFirst))
                            antiNodeXFirst -= normalizedXDiff
                            antiNodeYFirst += normalizedYDiff
                        }
                    }
                }
            }
            println("2024 day 08.2: ${antiNodes.size}")
        }

        fun day9_1() { // 6353658451014
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day9.txt").readText()
            var result: Long = 0
            val listOfBlocksAndSpaces = mutableListOf<MutableList<Int>>()
            var id = 0
            var isBlock = true
            rawText.forEach {
                if (isBlock) {
                    listOfBlocksAndSpaces.add(mutableListOf(id++, it.digitToInt()))
                } else {
                    listOfBlocksAndSpaces.add(mutableListOf(-1, it.digitToInt()))
                }
                isBlock = !isBlock
            }
            val reversedListOfBlocks =
                listOfBlocksAndSpaces.reversed().filter { it[0] > -1 }.map { it.toList() }.toList()
            run outer@{
                reversedListOfBlocks.forEach loop@{ block ->
                    val indexOfFirstFreeSpace = listOfBlocksAndSpaces.indexOfFirst { it[0] < 0 }
                    if (listOfBlocksAndSpaces[indexOfFirstFreeSpace + 1][0] == -1) {
//                        print("break")
                        return@outer
                    }

                    var numberOfSpaces = listOfBlocksAndSpaces.first { it[0] < 0 }[1]
                    var numberOfBlocks = block[1]
                    val indexOfLastBlock = listOfBlocksAndSpaces.indexOfLast { it[0] > -1 }
                    var indexOfFirstSpace = listOfBlocksAndSpaces.indexOfFirst { it[0] < 0 }
                    while (numberOfSpaces < numberOfBlocks) {
                        listOfBlocksAndSpaces[indexOfFirstSpace][0] = block[0]
                        listOfBlocksAndSpaces[indexOfLastBlock][1] = numberOfBlocks - numberOfSpaces
                        listOfBlocksAndSpaces.add(mutableListOf(-1, numberOfSpaces))

                        indexOfFirstSpace = listOfBlocksAndSpaces.indexOfFirst { it[0] < 0 }
                        numberOfBlocks -= numberOfSpaces
                        numberOfSpaces = listOfBlocksAndSpaces.first { it[0] < 0 }[1]
                    }
                    if (numberOfBlocks in 1..<numberOfSpaces) {
                        listOfBlocksAndSpaces[indexOfLastBlock][0] = -1
                        listOfBlocksAndSpaces[indexOfFirstSpace][1] = numberOfSpaces - numberOfBlocks
                        listOfBlocksAndSpaces.add(indexOfFirstSpace, mutableListOf(block[0], numberOfBlocks))
                        if (indexOfLastBlock < indexOfFirstSpace) {
                            return@outer
                        }
                        return@loop
                    }
                    if (indexOfLastBlock < indexOfFirstSpace) {
                        return@outer
                    }
                    if (numberOfBlocks == numberOfSpaces && numberOfBlocks > 0) {
                        listOfBlocksAndSpaces[indexOfFirstSpace][0] = block[0]
                        listOfBlocksAndSpaces[indexOfLastBlock][0] = -1
                    }
                    if (indexOfLastBlock < indexOfFirstSpace) {
                        return@outer
                    }
                }
            }

            val listOfBlocksAndSpacesFiltered = listOfBlocksAndSpaces.filter { it[0] > -1 }
            var i = -1
            listOfBlocksAndSpacesFiltered.forEach { block ->
                for (j in 0 until block[1]) {
                    result += block[0] * ++i
                }
            }
            println("2024 day 09.1: $result")
        }

        fun day9_2() { // 6382582136592
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day9.txt").readText()
            var result: Long = 0
            val listOfBlocksAndSpaces = mutableListOf<MutableList<Int>>()
            var id = 0
            var isBlock = true
            rawText.forEach {
                if (isBlock) {
                    listOfBlocksAndSpaces.add(mutableListOf(id++, it.digitToInt()))
                } else {
                    listOfBlocksAndSpaces.add(mutableListOf(-1, it.digitToInt()))
                }
                isBlock = !isBlock
            }
            val reversedListOfBlocks =
                listOfBlocksAndSpaces.reversed().filter { it[0] > -1 }.map { it.toList() }.toList()
            run outer@{
                reversedListOfBlocks.forEach { block ->
                    val indexOfFirstFreeSpace = listOfBlocksAndSpaces.indexOfFirst { it[0] == -1 }
                    val indexOfBlock = listOfBlocksAndSpaces.indexOfFirst { it[0] == block[0] }
                    if (indexOfFirstFreeSpace > indexOfBlock) {
                        return@outer
                    }
                    val freeSpaceNeeded = block[1]
                    val indexOfFreeSpaceNeeded =
                        listOfBlocksAndSpaces.indexOfFirst { it[0] == -1 && it[1] >= freeSpaceNeeded }
                    if (indexOfFreeSpaceNeeded > -1 && indexOfFreeSpaceNeeded < indexOfBlock) {
                        val numberOfFreeSpaces = listOfBlocksAndSpaces[indexOfFreeSpaceNeeded][1]
                        listOfBlocksAndSpaces[indexOfBlock][0] = -1
                        if (numberOfFreeSpaces == freeSpaceNeeded) {
                            listOfBlocksAndSpaces[indexOfFreeSpaceNeeded][0] = block[0]
                        } else {
                            listOfBlocksAndSpaces[indexOfFreeSpaceNeeded][1] = numberOfFreeSpaces - freeSpaceNeeded
                            listOfBlocksAndSpaces.add(indexOfFreeSpaceNeeded, mutableListOf(block[0], freeSpaceNeeded))
                        }
                    }
                }
            }

            var i = -1
            listOfBlocksAndSpaces.forEach loop@{ block ->
                if (block[0] == -1) {
                    i += block[1]
                    return@loop
                }
                for (j in 0 until block[1]) {
                    result += block[0] * ++i
                }
            }
            println("2024 day 09.2: $result")
        }

        fun day10_1() { // 587
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day10.txt").readLines()
            var result = 0
            val currentPositions = mutableListOf<Pair<Int, Int>>()
            rawText.forEachIndexed { indexLine, line ->
                line.forEachIndexed inner@{ indexChar, char ->
                    if (char == '0') {
                        currentPositions.add(Pair(indexLine, indexChar))
                    }
                }
            }
            val lastRow = rawText.size
            val lastColumn = rawText[0].length
            currentPositions.forEach { outer ->
                var currentHeight = 0
                var currentPaths = mutableSetOf<Pair<Int, Int>>()
                currentPaths.add(Pair(outer.first, outer.second))
                while (currentHeight < 9) {
                    val tmp = mutableSetOf<Pair<Int, Int>>()
                    currentPaths.forEach { inner ->
                        if (inner.first + 1 < lastRow && rawText[inner.first + 1][inner.second].digitToInt() == currentHeight + 1) {
                            tmp.add(Pair(inner.first + 1, inner.second))
                        }
                        if (inner.first - 1 >= 0 && rawText[inner.first - 1][inner.second].digitToInt() == currentHeight + 1) {
                            tmp.add(Pair(inner.first - 1, inner.second))
                        }
                        if (inner.second + 1 < lastColumn && rawText[inner.first][inner.second + 1].digitToInt() == currentHeight + 1) {
                            tmp.add(Pair(inner.first, inner.second + 1))
                        }
                        if (inner.second - 1 >= 0 && rawText[inner.first][inner.second - 1].digitToInt() == currentHeight + 1) {
                            tmp.add(Pair(inner.first, inner.second - 1))
                        }
                    }
                    currentPaths = tmp.toMutableSet()
                    currentHeight++
                }
                result += currentPaths.size
            }
            println("2024 day 10.1: $result")
        }

        fun day10_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day10.txt").readLines()
            val currentPositions = mutableListOf<Pair<Int, Int>>()
            val currentPaths = mutableSetOf<MutableList<Pair<Int, Int>>>()
            rawText.forEachIndexed { indexLine, line ->
                line.forEachIndexed inner@{ indexChar, char ->
                    if (char == '0') {
                        currentPositions.add(Pair(indexLine, indexChar))
                    }
                }
            }
            val lastRow = rawText.size
            val lastColumn = rawText[0].length
            currentPositions.forEach { outer ->
                var currentHeight = 0
                var currentPathsTmp = mutableSetOf<MutableList<Pair<Int, Int>>>()
                currentPathsTmp.add(listOf(Pair(outer.first, outer.second)).toMutableList())
                while (currentHeight < 9) {
                    val tmp = mutableSetOf<MutableList<Pair<Int, Int>>>()
                    currentPathsTmp.forEachIndexed loop@{ index, inner ->
                        if (inner.size < currentHeight + 1) {
                            return@loop
                        }
                        if (inner[currentHeight].first + 1 < lastRow && rawText[inner[currentHeight].first + 1][inner[currentHeight].second].digitToInt() == currentHeight + 1) {
                            val newList = inner.toMutableList()
                            newList.add(Pair(inner[currentHeight].first + 1, inner[currentHeight].second))
                            tmp.addAll(listOf(newList))
                        }
                        if (inner[currentHeight].first - 1 >= 0 && rawText[inner[currentHeight].first - 1][inner[currentHeight].second].digitToInt() == currentHeight + 1) {
                            val newList = inner.toMutableList()
                            newList.add(Pair(inner[currentHeight].first - 1, inner[currentHeight].second))
                            tmp.addAll(listOf(newList))
                        }
                        if (inner[currentHeight].second + 1 < lastColumn && rawText[inner[currentHeight].first][inner[currentHeight].second + 1].digitToInt() == currentHeight + 1) {
                            val newList = inner.toMutableList()
                            newList.add(Pair(inner[currentHeight].first, inner[currentHeight].second + 1))
                            tmp.addAll(listOf(newList))
                        }
                        if (inner[currentHeight].second - 1 >= 0 && rawText[inner[currentHeight].first][inner[currentHeight].second - 1].digitToInt() == currentHeight + 1) {
                            val newList = inner.toMutableList()
                            newList.add(Pair(inner[currentHeight].first, inner[currentHeight].second - 1))
                            tmp.addAll(listOf(newList))
                        }
                    }
//                    currentPaths = tmp.toMutableSet()
                    currentPathsTmp.addAll(tmp)
                    currentPathsTmp = currentPathsTmp.filter { it.size == currentHeight + 2 }.toMutableSet()
                    currentHeight++
                }
                currentPaths.addAll(currentPathsTmp)
            }
            println("2024 day 10.2: ${currentPaths.size}")
        }

        fun day11_1() { // 199753
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day11.txt").readText()
            var stoneArray = rawText.split(" ").map { it.toLong() }.toMutableList()
            var blinks = 25
            while (blinks > 0) {
                val stoneArrayTmp = mutableListOf<Long>()
                stoneArray.forEachIndexed loop@{ index, s ->
                    if (s == 0L) {
                        stoneArrayTmp.add(1)
                        return@loop
                    }
                    if (s.toString().length % 2 == 0) {
                        stoneArrayTmp.add(s.toString().substring(0, s.toString().length / 2).toLong())
                        stoneArrayTmp.add(s.toString().substring(s.toString().length / 2).toLong())
                        return@loop
                    }
                    stoneArrayTmp.add(s * 2024)
                }
                stoneArray = stoneArrayTmp.toMutableList()
                blinks--
            }
            println("2024 day 11.1: ${stoneArray.size}")
        }

        fun day11_2() { // 239413123020116
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day11.txt").readText()
            val stoneArray = rawText.split(" ").map { it.toLong() }.toList()
            var counter = sortedMapOf<Long, Long>()
            stoneArray.forEach {
                counter[it] = 1
            }
            var blinks = 75
            while (blinks > 0) {
                var counterTmp = sortedMapOf<Long, Long>()
                counter.forEach { key, value ->
                    if (key == 0L) {
                        counterTmp[1] = value
                    } else if (key.toString().length % 2 == 0) {
                        val newKey1 = key.toString().substring(0, key.toString().length / 2).toLong()
                        val newKey2 = key.toString().substring(key.toString().length / 2).toLong()
                        if (counterTmp.containsKey(newKey1)) {
                            counterTmp[newKey1] = counterTmp[newKey1]?.plus(value)
                        } else {
                            counterTmp[newKey1] = value
                        }
                        if (counterTmp.containsKey(newKey2)) {
                            counterTmp[newKey2] = counterTmp[newKey2]?.plus(value)
                        } else {
                            counterTmp[newKey2] = value
                        }
                    } else {
                        val newKey = key * 2024
                        if (counterTmp.containsKey(newKey)) {
                            counterTmp[newKey] = counterTmp[newKey]?.plus(value)
                        } else {
                            counterTmp[newKey] = value
                        }
                    }
                }
                counter = counterTmp.toSortedMap()
                blinks--
            }
            println("2024 day 11.2: ${counter.values.sum()}")
        }

        fun day12_1() { // 1494342
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day12.txt").readLines()

            fun calcPerimeter(line: Int, column: Int): Int {
                var perimeter = 0
                val currentChar = rawText[line][column].toString()
                if (line > 0) {
                    if (rawText[line - 1][column].toString() != currentChar) {
                        perimeter++
                    }
                } else {
                    perimeter++
                }
                if (line < (rawText.size - 1)) {
                    if (rawText[line + 1][column].toString() != currentChar) {
                        perimeter++
                    }
                } else {
                    perimeter++
                }
                if (column > 0) {
                    if (rawText[line][column - 1].toString() != currentChar) {
                        perimeter++
                    }
                } else {
                    perimeter++
                }
                if (column < (rawText[0].length - 1)) {
                    if (rawText[line][column + 1].toString() != currentChar) {
                        perimeter++
                    }
                } else {
                    perimeter++
                }
                return perimeter
            }

            val allPath = mutableSetOf<MutableSet<Pair<Pair<Int, Int>, Int>>>()
            fun findPath(line: Int, column: Int, path: MutableSet<Pair<Pair<Int, Int>, Int>>) {
                val perimeter = calcPerimeter(line, column)
                if (!path.contains(Pair(Pair(line, column), perimeter))) {
                    path.add(Pair(Pair(line, column), perimeter))
                    val char = rawText[line][column]
                    if (line < rawText.size - 1 && rawText[line + 1][column] == char) {
                        findPath(line + 1, column, path)
                    }
                    if (line > 0 && rawText[line - 1][column] == char) {
                        findPath(line - 1, column, path)
                    }
                    if (column < rawText[0].length - 1 && rawText[line][column + 1] == char) {
                        findPath(line, column + 1, path)
                    }
                    if (column > 0 && rawText[line][column - 1] == char) {
                        findPath(line, column - 1, path)
                    }
                }
            }

            rawText.forEachIndexed { line, s ->
                s.forEachIndexed { column, _ ->
                    val perimeter = calcPerimeter(line, column)
                    if (allPath.none { it.contains(Pair(Pair(line, column), perimeter)) }) {
                        val path = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
                        findPath(line, column, path)
                        allPath.add(path)
                    }
                }
            }

            var result = 0
            allPath.forEach { path ->
                val area = path.size
                val perimeter = path.sumOf { it.second }
                result += area * perimeter
            }
            println("2024 day 12.1: $result")
        }

        fun day12_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day12.txt").readLines()

            fun calcPerimeter(line: Int, column: Int): Int {
                var perimeter = 0
                val currentChar = rawText[line][column].toString()
                if (line > 0) {
                    if (rawText[line - 1][column].toString() != currentChar) {
                        perimeter++
                    }
                } else {
                    perimeter++
                }
                if (line < (rawText.size - 1)) {
                    if (rawText[line + 1][column].toString() != currentChar) {
                        perimeter++
                    }
                } else {
                    perimeter++
                }
                if (column > 0) {
                    if (rawText[line][column - 1].toString() != currentChar) {
                        perimeter++
                    }
                } else {
                    perimeter++
                }
                if (column < (rawText[0].length - 1)) {
                    if (rawText[line][column + 1].toString() != currentChar) {
                        perimeter++
                    }
                } else {
                    perimeter++
                }
                return perimeter
            }

            val allPath = mutableSetOf<MutableSet<Pair<Pair<Int, Int>, Int>>>()
            fun findPath(line: Int, column: Int, path: MutableSet<Pair<Pair<Int, Int>, Int>>) {
                val perimeter = calcPerimeter(line, column)
                if (!path.contains(Pair(Pair(line, column), perimeter))) {
                    path.add(Pair(Pair(line, column), perimeter))
                    val char = rawText[line][column]
                    if (line < rawText.size - 1 && rawText[line + 1][column] == char) {
                        findPath(line + 1, column, path)
                    }
                    if (line > 0 && rawText[line - 1][column] == char) {
                        findPath(line - 1, column, path)
                    }
                    if (column < rawText[0].length - 1 && rawText[line][column + 1] == char) {
                        findPath(line, column + 1, path)
                    }
                    if (column > 0 && rawText[line][column - 1] == char) {
                        findPath(line, column - 1, path)
                    }
                }
            }

            rawText.forEachIndexed { line, s ->
                s.forEachIndexed { column, _ ->
                    val perimeter = calcPerimeter(line, column)
                    if (allPath.none { it.contains(Pair(Pair(line, column), perimeter)) }) {
                        val path = mutableSetOf<Pair<Pair<Int, Int>, Int>>()
                        findPath(line, column, path)
                        allPath.add(path)
                    }
                }
            }

            fun getSequences(list: List<Int>): List<List<Int>> {
                val sequences = mutableListOf<MutableList<Int>>()
                var currentSequence = mutableListOf<Int>()
                list.forEachIndexed loop@{ index, it ->
                    if (currentSequence.isEmpty() || currentSequence.last() == it - 1) {
                        currentSequence.add(it)
                    } else {
                        sequences.add(currentSequence)
                        currentSequence = mutableListOf()
                        currentSequence.add(it)
                    }

                    if (index == list.size - 1) {
                        sequences.add(currentSequence)
                    }
                }
                return sequences.toList()
            }

            var result = 0
            allPath.forEach { path ->
                var sides = 0
                val cleanedPath = path.map { it.first }.sortedBy { it.second }.sortedBy { it.first }
                val char = rawText[path.first().first.first][path.first().first.second]

                val linesY = cleanedPath.groupBy { it.first }.keys
                linesY.forEach { line ->
                    val sub = cleanedPath.filter { it.first == line }
                    val list = sub.map { it.second }
                    val sequences = getSequences(list)
                    sequences.forEach { sequence ->
                        if (line == 0) {
                            sides += 1
                        } else {
                            val noCharIndex = sequence.filter { rawText[line - 1][it] != char }
                            val sequencesTmp = getSequences(noCharIndex.sorted())
                            sides += sequencesTmp.size
                        }

                        if (line == rawText.size - 1) {
                            sides += 1
                        } else {
                            val noCharIndex = sequence.filter { rawText[line + 1][it] != char }
                            val sequencesTmp = getSequences(noCharIndex.sorted())
                            sides += sequencesTmp.size
                        }
                    }
                }

                val linesX = cleanedPath.groupBy { it.second }.keys
                linesX.forEach { line ->
                    val sub = cleanedPath.filter { it.second == line }
                    val list = sub.map { it.first }
                    val sequences = getSequences(list)
                    sequences.forEach { sequence ->
                        if (line == 0) {
                            sides += 1
                        } else {
                            val noCharIndex = sequence.filter { rawText[it][line - 1] != char }
                            val sequencesTmp = getSequences(noCharIndex.sorted())
                            sides += sequencesTmp.size
                        }

                        if (line == rawText[0].length - 1) {
                            sides += 1
                        } else {
                            val noCharIndex = sequence.filter { rawText[it][line + 1] != char }
                            val sequencesTmp = getSequences(noCharIndex.sorted())
                            sides += sequencesTmp.size
                        }
                    }
                }
                result += path.size * sides
            }
            println("2024 day 12.2: $result")
        }

        fun day13_1() { // 37901
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day13.txt").readText()
            val array = rawText.split("\r\n\r\n")
            val tokenA = 3
            val tokenB = 1
            var result = 0L

            array.forEach {
                val split = it.split("\r\n")
                var x = split[2].split(":")[1].split(",")[0].split("=")[1].toLong()
                var y = split[2].split(":")[1].split(",")[1].split("=")[1].toLong()
                val stepXA = split[0].split(":")[1].split(",")[0].split("+")[1].toLong()
                val stepYA = split[0].split(":")[1].split(",")[1].split("+")[1].toLong()
                val stepXB = split[1].split(":")[1].split(",")[0].split("+")[1].toLong()
                val stepYB = split[1].split(":")[1].split(",")[1].split("+")[1].toLong()
                var i = 0L
                val tmp = mutableSetOf<Pair<Long, Long>>()

                if (x % stepXA == 0L && y % stepYA == 0L) {
                    if (x / stepXA == y / stepYA) {
                        tmp.add(Pair(x / stepXA, 0L))
                    }
                }

                if (x % stepXB == 0L && y % stepYB == 0L) {
                    if (x / stepXB == y / stepYB) {
                        tmp.add(Pair(0L, y / stepYB))
                    }
                }

                while (x > stepXA && y > stepYA) {
                    i++
                    x -= stepXA
                    y -= stepYA
                    if (x % stepXB == 0L && stepYB * (x / stepXB) == y) {
                        println("A:$i")
                        println("B:${x / stepXB}")
                        tmp.add(Pair(i, x / stepXB))
                    }
                }
                val resultList = mutableSetOf<Long>()
                tmp.forEach { pair ->
                    resultList.add(pair.first * tokenA + (pair.second * tokenB))
                }
                if (resultList.isNotEmpty()) {
                    result += resultList.sorted().first()
                }
            }
            println("2024 day 13.1: $result")
        }

        fun day13_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day13.txt").readText()
            val array = rawText.split("\r\n\r\n")
            val tokenA = 3
            val tokenB = 1
            var result = 0L

            val extend = 10000000000000
            array.forEach {
                val split = it.split("\r\n")
                val x = split[2].split(":")[1].split(",")[0].split("=")[1].toLong() + extend
                val y = split[2].split(":")[1].split(",")[1].split("=")[1].toLong() + extend
                val buttonAX = split[0].split(":")[1].split(",")[0].split("+")[1].toLong()
                val buttonAY = split[0].split(":")[1].split(",")[1].split("+")[1].toLong()
                val buttonBX = split[1].split(":")[1].split(",")[0].split("+")[1].toLong()
                val buttonBY = split[1].split(":")[1].split(",")[1].split("+")[1].toLong()
//                val tmp = mutableSetOf<Pair<Long, Long>>()

                val first = ((x * buttonAY) - (y * buttonAX))
                val second = ((-buttonBY * buttonAX) + (buttonBX * buttonAY))
                if (first % second == 0L) {
                    val pressA = first / second
                    val third = (x - (pressA * buttonBX))
                    if (third % buttonAX == 0L) {
                        val pressB = third / buttonAX
                        result += (pressA * tokenB) + (pressB * tokenA)
                    }
                }
            }
            println("2024 day 13.2: $result")
        }

        fun day14_1() { // 211692000
            val robots: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>> =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day14.txt").readLines()
                    .map {
                        Pair(
                            Pair(
                                it.split(" ")[0].split("=")[1].split(",")[0].toInt(),
                                it.split(" ")[0].split("=")[1].split(",")[1].toInt()
                            ),
                            Pair(
                                it.split(" ")[1].split("=")[1].split(",")[0].toInt(),
                                it.split(" ")[1].split("=")[1].split(",")[1].toInt()
                            )
                        )
                    }.toMutableList()
            val width = 101
            val height = 103
            for (range in 0..<robots.size) {
                for (second in 0..99) {
                    val newPosition = Pair(
                        (robots[range].first.first + robots[range].second.first).mod(width),
                        (robots[range].first.second + robots[range].second.second).mod(height)
                    )
                    robots[range] = Pair(newPosition, Pair(robots[range].second.first, robots[range].second.second))
                }
            }
            val firstQuadrant =
                robots.filter { it.first.first < (width / 2) && it.first.second < (height / 2) }.size.toLong()
            val secondQuadrant =
                robots.filter { it.first.first > (width / 2) && it.first.second < (height / 2) }.size.toLong()
            val thirdQuadrant =
                robots.filter { it.first.first < (width / 2) && it.first.second > (height / 2) }.size.toLong()
            val fourthQuadrant =
                robots.filter { it.first.first > (width / 2) && it.first.second > (height / 2) }.size.toLong()
            val result = firstQuadrant * secondQuadrant * thirdQuadrant * fourthQuadrant
            println("2024 day 14.1: $result")
        }

        fun day14_2() { // 6587
            fun drawRobots(robots: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>>) {
                val sortedRobots = robots.sortedBy { it.first.first }.sortedBy { it.first.second }
                val lines = mutableListOf<String>()
                println(sortedRobots)
                sortedRobots.forEach loop@{
                    val lineIndex = it.first.second
                    val line =
                        "....................................................................................................."
                    if (lines.isEmpty() || lineIndex == lines.size) {
                        lines.add(line)
                    }
                    if (lines.size < lineIndex) {
                        lines.add(line)
                        return@loop
                    }
                    val first = if (it.first.first > 0) lines[lineIndex].substring(0, it.first.first) else ""
                    val second = if (it.first.first < 101) lines[lineIndex].substring(it.first.first + 1) else ""
                    val tmp = "${first}1$second"
                    lines[lineIndex] = tmp
                }

                lines.forEachIndexed { index, s ->
                    println("$index: $s")
                }
                println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
            }

            val robots: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>> =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day14.txt").readLines()
                    .map {
                        Pair(
                            Pair(
                                it.split(" ")[0].split("=")[1].split(",")[0].toInt(),
                                it.split(" ")[0].split("=")[1].split(",")[1].toInt()
                            ),
                            Pair(
                                it.split(" ")[1].split("=")[1].split(",")[0].toInt(),
                                it.split(" ")[1].split("=")[1].split(",")[1].toInt()
                            )
                        )
                    }.toMutableList()

            val width = 101
            val height = 103
            var result = 0
            run loop@{
                for (second in 0..999999999) {
                    for (range in 0..<robots.size) {
                        val newPosition = Pair(
                            (robots[range].first.first + robots[range].second.first).mod(width),
                            (robots[range].first.second + robots[range].second.second).mod(height)
                        )
                        robots[range] = Pair(newPosition, Pair(robots[range].second.first, robots[range].second.second))
                    }
                    val robotsList = robots.map { it.first }
                    val robotsSet = robots.map { it.first }.toSet()
                    // !! third time the if statement is entered is the correct one
                    if (robotsList.size == robotsSet.size) { // additional condition needed like variance: https://www.reddit.com/r/adventofcode/comments/1hts3v2/2024_day_14_part_2_c_how_to_find_the_tree_via/
//                        drawRobots(robots)
                        result = second + 1
                        return@loop
                    }
                }
            }

            println("2024 day 14.2: $result")
        }

        fun day15_1() { // 1448589
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day15.txt").readLines()
                    .filter { it.startsWith("#") }.toMutableList()
            val commands =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day15.txt").readText()
                    .split("\r\n\r\n")[1].replace("\r\n", "")

            var x = map.first { it.contains("@") }.indexOfFirst { it == '@' }
            var y = IntStream.range(1, map.size).filter { map[it].contains("@") }.findFirst().asInt
            commands.forEach command@{ c ->
                val tmpX = when (c) {
                    '<' -> -1
                    '>' -> 1
                    else -> 0
                }
                val tmpY = when (c) {
                    '^' -> -1
                    'v' -> 1
                    else -> 0
                }
                if (tmpX == 0 && tmpY == 0) {
                    println("ERROR")
                }

                if (map[y + tmpY][x + tmpX] == '#') {
                    println(map)
                    return@command
                } else if (map[y + tmpY][x + tmpX] == '.') {
                    map[y] = map[y].toCharArray().also { it[x] = '.' }.joinToString("")
                    map[y + tmpY] = map[y + tmpY].toCharArray().also { it[x + tmpX] = '@' }.joinToString("")
                    x += tmpX
                    y += tmpY
                    println(map)
                    return@command
                } else {
                    var i = 1
                    while (true) {
                        i++
                        if (map[y + (tmpY * i)][x + (tmpX * i)] == 'O') {
                            continue
                        } else if (map[y + (tmpY * i)][x + (tmpX * i)] == '#') {
                            break
                        } else {
                            map[y] = map[y].toCharArray().also { it[x] = '.' }.joinToString("")
                            map[y + tmpY] = map[y + tmpY].toCharArray().also { it[x + tmpX] = '@' }.joinToString("")
                            map[y + (tmpY * i)] =
                                map[y + (tmpY * i)].toCharArray().also { it[x + (tmpX * i)] = 'O' }.joinToString("")
                            x += tmpX
                            y += tmpY
                            break
                        }
                    }
                    println(map)
                }
            }

            var result = 0
            map.forEachIndexed outer@{ line, s ->
                if (line == 0) {
                    return@outer
                }
                s.forEachIndexed inner@{ index, c ->
                    if (c != 'O') {
                        return@inner
                    }

                    result += (100 * line) + index
                }
            }

            println("2024 day 15.1: $result")
        }

        fun day15_2() { // 1472235
            val commands =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day15.txt").readText()
                    .split("\r\n\r\n")[1].replace("\r\n", "")
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day15.txt").readLines()
                    .filter { it.startsWith("#") }
                    .map { it.replace("#", "##").replace(".", "..").replace("@", "@.").replace("O", "[]") }
                    .toMutableList()
            var x = map.first { it.contains("@") }.indexOfFirst { it == '@' }
            var y = IntStream.range(1, map.size).filter { map[it].contains("@") }.findFirst().asInt
            val height = map.size
            val width = map[0].length

            commands.forEach command@{ c ->
                val tmpX = when (c) {
                    '<' -> -1
                    '>' -> 1
                    else -> 0
                }
                val tmpY = when (c) {
                    '^' -> -1
                    'v' -> 1
                    else -> 0
                }
                if (tmpX == 0 && tmpY == 0) {
                    println("ERROR")
                }

                if (map[y + tmpY][x + tmpX] == '#') {
                    println(map)
                    return@command
                } else if (map[y + tmpY][x + tmpX] == '.') {
                    map[y] = map[y].toCharArray().also { it[x] = '.' }.joinToString("")
                    map[y + tmpY] = map[y + tmpY].toCharArray().also { it[x + tmpX] = '@' }.joinToString("")
                    x += tmpX
                    y += tmpY
                    println(map)
                    return@command
                } else {
                    if (tmpX != 0) {
                        var i = 3
                        while ((x + (tmpX * i) in 1..<width) && (map[y][x + (tmpX * i)] != '.' && map[y][x + (tmpX * i)] != '#')) {
                            i += 2
                        }
                        if ((x + (tmpX * i) < 0) || (x + (tmpX * i) >= width) || map[y][x + (tmpX * i)] == '#') {
                            return@command
                        }

                        map[y] = map[y].toCharArray().also { it[x] = '.' }.joinToString("")
                        map[y] = map[y].toCharArray().also { it[x + tmpX] = '@' }.joinToString("")
                        var toggle = if (tmpX > 0) ']' else '['
                        while (i > 1) {
                            map[y] = map[y].toCharArray().also { it[x + (tmpX * i)] = toggle }.joinToString("")
                            toggle = if (toggle == ']') '[' else ']'
                            i--
                        }
                    } else {
                        var toBeMoved = mutableSetOf<Pair<Int, Pair<Int, Int>>>()
                        toBeMoved.add(Pair(y + tmpY, Pair(x, if (map[y + tmpY][x] == '[') x + 1 else x - 1)))
                        var i = 1
                        while (true) {
                            val tmp = mutableSetOf<Pair<Int, Pair<Int, Int>>>()
                            toBeMoved.filter { first -> first.first == (y + (tmpY * i)) }.forEach filteredInner@{ p ->
                                if (map[y + (tmpY * (i + 1))][p.second.first] == '#') return@command
                                if (map[y + (tmpY * (i + 1))][p.second.second] == '#') return@command

                                if (map[y + (tmpY * (i + 1))][p.second.first] == '[') {
                                    tmp.add(Pair(y + (tmpY * (i + 1)), Pair(p.second.first, p.second.first + 1)))
                                }
                                if (map[y + (tmpY * (i + 1))][p.second.first] == ']') {
                                    tmp.add(Pair(y + (tmpY * (i + 1)), Pair(p.second.first, p.second.first - 1)))
                                }
                                if (map[y + (tmpY * (i + 1))][p.second.second] == '[') {
                                    tmp.add(Pair(y + (tmpY * (i + 1)), Pair(p.second.second, p.second.second + 1)))
                                }
                                if (map[y + (tmpY * (i + 1))][p.second.second] == ']') {
                                    tmp.add(Pair(y + (tmpY * (i + 1)), Pair(p.second.second, p.second.second - 1)))
                                }
                            }
                            if (tmp.isEmpty()) {
                                break
                            }
                            toBeMoved.addAll(tmp)
                            i++
                        }
                        println("move")
                        toBeMoved = if (c == '^') {
                            toBeMoved.sortedBy { it.first }.toMutableSet()
                        } else {
                            toBeMoved.sortedByDescending { it.first }.toMutableSet()
                        }
                        toBeMoved.forEach { pair ->
                            map[pair.first] =
                                map[pair.first].toCharArray().also { it[pair.second.first] = '.' }.joinToString("")
                            map[pair.first] =
                                map[pair.first].toCharArray().also { it[pair.second.second] = '.' }.joinToString("")
                            map[pair.first + tmpY] = map[pair.first + tmpY].toCharArray()
                                .also { it[min(pair.second.first, pair.second.second)] = '[' }.joinToString("")
                            map[pair.first + tmpY] = map[pair.first + tmpY].toCharArray()
                                .also { it[max(pair.second.first, pair.second.second)] = ']' }.joinToString("")
                        }
                        map[y] = map[y].toCharArray().also { it[x] = '.' }.joinToString("")
                        map[y + tmpY] = map[y + tmpY].toCharArray().also { it[x] = '@' }.joinToString("")
                    }
                    x += tmpX
                    y += tmpY
                }

                map.forEach {
                    println(it)
                }
                println("++++++++++++++++++++++++++++")
            }

            var result = 0
            map.forEachIndexed outer@{ line, s ->
                if (line == 0) {
                    return@outer
                }
                s.forEachIndexed inner@{ index, c ->
                    if (c != '[') {
                        return@inner
                    }

                    result += (100 * line) + index
                }
            }

            println("2024 day 15.2: $result")
        }

        fun day16_1() { // 160624
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day16.xxx").readLines()

            val startY = map.indexOfFirst { it.contains("S") }
            val startX = map.first { it.contains("S") }.indexOf('S')
            val targetY = map.indexOfFirst { it.contains("E") }
            val targetX = map.first { it.contains("E") }.indexOf('E')
            println("x:$startX,y:$startY")
            Path.setTarget(Pair(targetY, targetX))
            var allPathList = mutableListOf<Path>()
            var finished = mutableListOf<Path>()
            allPathList.add(Path(mutableSetOf(Pair(startY, startX)), 0))
            val eastWest = listOf(0, 2)
            val northSouth = listOf(1, 3)

            var maxDirectionChange = 0
            var maxSteps = 0
            while (allPathList.any { it.getState() == Path.STATE.PROCESSING }) {
                val tmp = mutableListOf<Path>()

                if (allPathList.any { it.getState() == Path.STATE.FINISHED }) {
                    allPathList.filter { it.getState() == Path.STATE.FINISHED }.forEach {
                        finished.add(it)
                        if (it.getNumberOfDirectionChanged() < maxDirectionChange || maxDirectionChange == 0) {
                            maxDirectionChange = it.getNumberOfDirectionChanged()
                        }
                        if (it.getNumberOfPathPoints() < maxSteps || maxSteps == 0) {
                            maxSteps = it.getNumberOfPathPoints()
                        }
                    }
                    allPathList.removeIf { it.getState() == Path.STATE.FINISHED }
                }
                allPathList
                    .filter { it.getNumberOfPathPoints() > maxSteps || maxSteps == 0 }
                    .groupBy { it.getLastPathPoint() }.forEach {
                        if (it.value.size == 1) {
                            tmp.add(it.value.first())
                        } else {
                            it.value.minByOrNull { path -> path.getNumberOfPathPoints() + (1000 * path.getNumberOfDirectionChanged()) }
                                ?.let { it1 -> tmp.add(it1) }
                        }
                    }
                allPathList = tmp
                val tmpOuter = allPathList.listIterator()
                while (tmpOuter.hasNext()) {
                    val nextOuter = tmpOuter.next()
//                    if (nextOuter.getState() == old.Path.STATE.FINISHED) continue
                    val lastPathPoint = nextOuter.getLastPathPoint()
                    val currentDirection = nextOuter.currentDirection
                    val plusDirection = floorMod(currentDirection + 1, 4)
                    val minusDirection = floorMod(currentDirection - 1, 4)
                    val newY =
                        lastPathPoint.first + (if (eastWest.contains(currentDirection)) 0 else (if (currentDirection == 1) -1 else 1))
                    val newX =
                        lastPathPoint.second + (if (northSouth.contains(currentDirection)) 0 else (if (currentDirection == 0) 1 else -1))
                    val turnPlusY =
                        lastPathPoint.first + (if (eastWest.contains(plusDirection)) 0 else (if (currentDirection == 0) -1 else 1))
                    val turnPlusX =
                        lastPathPoint.second + (if (northSouth.contains(plusDirection)) 0 else (if (currentDirection == 1) -1 else 1))
                    val turnMinusY =
                        lastPathPoint.first + (if (eastWest.contains(minusDirection)) 0 else (if (currentDirection == 0) 1 else -1))
                    val turnMinusX =
                        lastPathPoint.second + (if (northSouth.contains(minusDirection)) 0 else (if (currentDirection == 1) 1 else -1))

                    if (map[newY][newX] != '#') {
                        if (map[turnPlusY][turnPlusX] == '.') {
                            val tmp1 = nextOuter.copy(
                                pathPoints = nextOuter.pathPoints.toMutableSet(),
                                directionChanged = nextOuter.getNumberOfDirectionChanged()
                            )
                            val test = tmp1.addPathPoint(Pair(turnPlusY, turnPlusX), plusDirection)
                            if (test) tmpOuter.add(tmp1)

                        }
                        if (map[turnMinusY][turnMinusX] == '.') {
                            val tmp2 = nextOuter.copy(
                                pathPoints = nextOuter.pathPoints.toMutableSet(),
                                directionChanged = nextOuter.getNumberOfDirectionChanged()
                            )
                            val test = tmp2.addPathPoint(Pair(turnMinusY, turnMinusX), minusDirection)
                            if (test) tmpOuter.add(tmp2)

                        }

                        val test = nextOuter.addPathPoint(Pair(newY, newX), currentDirection)
                        if (!test) tmpOuter.remove()
                    } else {
                        if (map[turnPlusY][turnPlusX] == '#' && map[turnMinusY][turnMinusX] == '#') {
                            tmpOuter.remove()
                        } else if (map[turnPlusY][turnPlusX] == '.' && map[turnMinusY][turnMinusX] == '.') {
                            val tmp3 = nextOuter.copy(
                                pathPoints = nextOuter.pathPoints.toMutableSet(),
                                directionChanged = nextOuter.getNumberOfDirectionChanged()
                            )
                            var test = tmp3.addPathPoint(Pair(turnMinusY, turnMinusX), minusDirection)
                            if (test) tmpOuter.add(tmp3)
                            test = nextOuter.addPathPoint(Pair(turnPlusY, turnPlusX), plusDirection)
                            if (!test) tmpOuter.remove()
                        } else if (map[turnMinusY][turnMinusX] == '.') {
                            val test = nextOuter.addPathPoint(Pair(turnMinusY, turnMinusX), minusDirection)
                            if (!test) tmpOuter.remove()
                        } else if (map[turnPlusY][turnPlusX] == '.') {
                            val test = nextOuter.addPathPoint(Pair(turnPlusY, turnPlusX), plusDirection)
                            if (!test) tmpOuter.remove()
                        }
                    }
                }
            }

            val result =
                finished.minByOrNull { it.getNumberOfPathPoints() + (1000 * it.getNumberOfDirectionChanged()) }!!
            val result2 =
                finished.filter { it.getNumberOfPathPoints() + (1000 * it.getNumberOfDirectionChanged()) == 160624 }
            println("finished: ${finished.size}")
            println("finishedMin: ${result2.size}")
            println("2024 day 16.1: ${result.getNumberOfPathPoints() + (1000 * result.getNumberOfDirectionChanged())}")
        }

        fun day16_2() { // 692
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day16.xxx").readLines()

            val startY = map.indexOfFirst { it.contains("S") }
            val startX = map.first { it.contains("S") }.indexOf('S')
            val targetY = map.indexOfFirst { it.contains("E") }
            val targetX = map.first { it.contains("E") }.indexOf('E')
            println("x:$startX,y:$startY")
            Path.setTarget(Pair(targetY, targetX))
            var allPathList = mutableListOf<Path>()
            val finished = mutableListOf<Path>()
            allPathList.add(Path(mutableSetOf(Pair(startY, startX)), 0))
            val eastWest = listOf(0, 2)
            val northSouth = listOf(1, 3)

            while (allPathList.any { it.getState() == Path.STATE.PROCESSING }) {

                val tmp = mutableListOf<Path>()
                allPathList.filter { it.getState() == Path.STATE.FINISHED }.forEach {
                    finished.add(it)
                }
                allPathList.removeIf { (it.getState() == Path.STATE.FINISHED) || (it.getNumberOfPathPoints() + (1000 * it.getNumberOfDirectionChanged())) > 160624 }
                allPathList
                    .groupBy { Pair(it.getLastPathPoint(), it.currentDirection) }.forEach { itOuter ->
                        val minScorePath =
                            itOuter.value.minByOrNull { path -> path.getNumberOfPathPoints() + (1000 * path.getNumberOfDirectionChanged()) }
                        val minScoreValue = minScorePath?.getNumberOfPathPoints()
                            ?.plus(1000 * minScorePath.getNumberOfDirectionChanged())
                        val allMinScorePath =
                            itOuter.value.filter { path -> (path.getNumberOfPathPoints() + (1000 * path.getNumberOfDirectionChanged())) == minScoreValue }
                        val allPathPoints = mutableSetOf<Pair<Int, Int>>()
                        allMinScorePath.forEach {
                            allPathPoints.addAll(it.getAllPathPoints())
                        }
                        val allExistingPathPoints = mutableSetOf<Pair<Int, Int>>()
                        allMinScorePath.forEach {
                            allExistingPathPoints.addAll(it.getAllExistingPathPoints())
                        }
                        if (minScorePath != null) {
                            minScorePath.addAllExistingPathPoints(allExistingPathPoints)
                            minScorePath.addAllExistingPathPoints(allPathPoints)
                            tmp.add(minScorePath)
                        }
                    }
                allPathList = tmp
                val tmpOuter = allPathList.listIterator()
                while (tmpOuter.hasNext()) {
                    val nextOuter = tmpOuter.next()
                    val lastPathPoint = nextOuter.getLastPathPoint()
                    val currentDirection = nextOuter.currentDirection
                    val plusDirection = floorMod(currentDirection + 1, 4)
                    val minusDirection = floorMod(currentDirection - 1, 4)
                    val newY =
                        lastPathPoint.first + (if (eastWest.contains(currentDirection)) 0 else (if (currentDirection == 1) -1 else 1))
                    val newX =
                        lastPathPoint.second + (if (northSouth.contains(currentDirection)) 0 else (if (currentDirection == 0) 1 else -1))
                    val turnPlusY =
                        lastPathPoint.first + (if (eastWest.contains(plusDirection)) 0 else (if (currentDirection == 0) -1 else 1))
                    val turnPlusX =
                        lastPathPoint.second + (if (northSouth.contains(plusDirection)) 0 else (if (currentDirection == 1) -1 else 1))
                    val turnMinusY =
                        lastPathPoint.first + (if (eastWest.contains(minusDirection)) 0 else (if (currentDirection == 0) 1 else -1))
                    val turnMinusX =
                        lastPathPoint.second + (if (northSouth.contains(minusDirection)) 0 else (if (currentDirection == 1) 1 else -1))

                    if (map[newY][newX] != '#') {
                        if (map[turnPlusY][turnPlusX] == '.') {
                            val tmp1 = nextOuter.copy(
                                pathPoints = nextOuter.pathPoints.toMutableSet(),
                                directionChanged = nextOuter.getNumberOfDirectionChanged()
                            )
                            tmp1.addAllExistingPathPoints(nextOuter.getAllExistingPathPoints())
                            val test = tmp1.addPathPoint(Pair(turnPlusY, turnPlusX), plusDirection)
                            if (test) tmpOuter.add(tmp1)

                        }
                        if (map[turnMinusY][turnMinusX] == '.') {
                            val tmp2 = nextOuter.copy(
                                pathPoints = nextOuter.pathPoints.toMutableSet(),
                                directionChanged = nextOuter.getNumberOfDirectionChanged()
                            )
                            tmp2.addAllExistingPathPoints(nextOuter.getAllExistingPathPoints())
                            val test = tmp2.addPathPoint(Pair(turnMinusY, turnMinusX), minusDirection)
                            if (test) tmpOuter.add(tmp2)

                        }

                        val test = nextOuter.addPathPoint(Pair(newY, newX), currentDirection)
                        if (!test) tmpOuter.remove()
                    } else {
                        if (map[turnPlusY][turnPlusX] == '#' && map[turnMinusY][turnMinusX] == '#') {
                            tmpOuter.remove()
                        } else if (map[turnPlusY][turnPlusX] == '.' && map[turnMinusY][turnMinusX] == '.') {
                            val tmp3 = nextOuter.copy(
                                pathPoints = nextOuter.pathPoints.toMutableSet(),
                                directionChanged = nextOuter.getNumberOfDirectionChanged()
                            )
                            tmp3.addAllExistingPathPoints(nextOuter.getAllExistingPathPoints())
                            var test = tmp3.addPathPoint(Pair(turnMinusY, turnMinusX), minusDirection)
                            if (test) tmpOuter.add(tmp3)
                            test = nextOuter.addPathPoint(Pair(turnPlusY, turnPlusX), plusDirection)
                            if (!test) tmpOuter.remove()
                        } else if (map[turnMinusY][turnMinusX] == '.') {
                            val test = nextOuter.addPathPoint(Pair(turnMinusY, turnMinusX), minusDirection)
                            if (!test) tmpOuter.remove()
                        } else if (map[turnPlusY][turnPlusX] == '.') {
                            val test = nextOuter.addPathPoint(Pair(turnPlusY, turnPlusX), plusDirection)
                            if (!test) tmpOuter.remove()
                        }
                    }
                }
            }

            val result =
                finished.minByOrNull { it.getNumberOfPathPoints() + (1000 * it.getNumberOfDirectionChanged()) }!!
            println("2024 day 16.2: ${result.getNumberOfAllExistingPathPoints() + 1}")
        }

        fun day17_1() { // 7,0,3,1,2,6,3,7,1
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day17.txt").readLines()

            var registerA: Long = map.first().split(":")[1].trim().toLong()
            var registerB: Long = map[1].split(":")[1].trim().toLong()
            var registerC: Long = map[2].split(":")[1].trim().toLong()
            val prog = map[4].split(":")[1].trim().replace(",", "").map { it.digitToInt() }.toList()
            var insP = 0
            val result = mutableListOf<String>()

            while (true) {
                val currentINSTRUCTION = INSTRUCTION.entries.toTypedArray()[prog[insP]]
                val currentLiteral = prog[insP + 1]
                val currentCombo: Long = when (prog[insP + 1]) {
                    in 0..3 -> prog[insP + 1].toLong()
                    4 -> registerA
                    5 -> registerB
                    6 -> registerC
                    else -> {
                        throw Exception("wrong")
                    }
                }

                fun calcAdv(): Long {
//                    println("${registerA.shr(currentCombo.toInt())}")
                    return registerA.shr(3)
                }

                fun calcBxl(): Long {
                    print("$registerB,$currentLiteral - ")
                    println("regB: ${registerB xor currentLiteral.toLong()}")
                    return registerB xor currentLiteral.toLong()
                }

                fun calcBst(): Long {
//                    println("${currentCombo.and(7L)}")
                    return registerA.and(7L)
                }

                fun calcJnz() {
//                    if(registerA == 0L){
//                        println("${insP + 2}")
//                    } else{
//                        println("$currentLiteral")
//                    }
//                    println("jump: $currentLiteral")
                    insP = if (registerA == 0L) insP else currentLiteral - 2
                }

                fun calcBxc(): Long {
//                    println("${registerB xor registerC}")
                    return registerB xor registerC
                }

                fun calcOut(): Long {
//                    println("${currentCombo.and(7L)}")
                    return currentCombo.and(7L)
                }

//                fun calcBdv(): Long {
////                    println("${registerA shr currentCombo.toInt()}")
//                    return registerA shr currentCombo.toInt()
//                }

                fun calcCdv(): Long {
//                    println("${registerA shr currentCombo.toInt()}")
                    return registerA shr currentCombo.toInt()
                }

                when (currentINSTRUCTION) {
                    INSTRUCTION.ADV -> registerA = calcAdv()                 // 4
                    INSTRUCTION.BXL -> registerB = calcBxl()                 // 2, 6
                    INSTRUCTION.BST -> registerB = calcBst()                 // 1
                    INSTRUCTION.JNZ -> calcJnz()                             // 8
                    INSTRUCTION.BXC -> registerB = calcBxc()                 // 5
                    INSTRUCTION.OUT -> result.add(calcOut().toString())      // 7
                    INSTRUCTION.BDV -> println("nothing")                    //
                    INSTRUCTION.CDV -> registerC = calcCdv()                 // 3
                }

                insP += 2
                if (insP >= prog.size) {
                    break
                }
            }

            println("2024 day 17.1: ${result.joinToString(",")}")
        }


        fun day17_2() { // 109020013201563
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day17.txt").readLines()

            var registerA: Long
            var registerB: Long
            var registerC: Long
            val prog = map[4].split(":")[1].trim().replace(",", "").map { it.digitToInt() }.toList()
            var result = mutableListOf<String>()

            var currentRound = 15
            var regAStart = 0L
            while (currentRound > -1) {
                if (result.toString() != prog.subList(currentRound, prog.size).toString()) {
                    regAStart++
                    result = mutableListOf()
                } else {
                    if (result.size == prog.size) {
                        break
                    }
                    currentRound--
                    regAStart = (regAStart - (regAStart % 8)) * 8
                    result = mutableListOf()
                }
                var insP = 0
                registerA = regAStart
                registerB = 0
                registerC = 0
                while (true) {
                    val currentINSTRUCTION = INSTRUCTION.entries.toTypedArray()[prog[insP]]
                    val currentLiteral = prog[insP + 1]
                    val currentCombo: Long = when (prog[insP + 1]) {
                        in 0..3 -> prog[insP + 1].toLong()
                        4 -> registerA
                        5 -> registerB
                        6 -> registerC
                        else -> {
                            throw Exception("wrong")
                        }
                    }

                    fun calcAdv(): Long {
//                    println("${registerA.shr(currentCombo.toInt())}")
                        return registerA.shr(3)
                    }

                    fun calcBxl(): Long {
//                    println("${registerB xor currentLiteral.toLong()}")
                        return registerB xor currentLiteral.toLong()
                    }

                    fun calcBst(): Long {
                        return registerA.and(7L)
                    }

                    fun calcJnz() {
                        insP = if (registerA == 0L) insP else currentLiteral - 2
                    }

                    fun calcBxc(): Long {
//                    println("${registerB xor registerC}")
                        return registerB xor registerC
                    }

                    fun calcOut(): Long {
                        return registerB.and(7L)
                    }

//                fun calcBdv(): Long {
////                    println("${registerA shr currentCombo.toInt()}")
//                    return registerA shr currentCombo.toInt()
//                }

                    fun calcCdv(): Long {
//                    println("${registerA shr currentCombo.toInt()}")
                        return registerA shr currentCombo.toInt()
                    }

                    when (currentINSTRUCTION) {
                        INSTRUCTION.ADV -> registerA = calcAdv()                 // 4
                        INSTRUCTION.BXL -> registerB = calcBxl()                 // 2, 6
                        INSTRUCTION.BST -> registerB = calcBst()                 // 1
                        INSTRUCTION.JNZ -> calcJnz()                             // 8
                        INSTRUCTION.BXC -> registerB = calcBxc()                 // 5
                        INSTRUCTION.OUT -> result.add(calcOut().toString())      // 7
                        INSTRUCTION.BDV -> println("nothing")                    //
                        INSTRUCTION.CDV -> registerC = calcCdv()                 // 3
                    }

                    insP += 2
                    if (insP >= prog.size) {
                        break
                    }
                }
            }


            println("2024 day 17.2: $regAStart")
        }


        enum class INSTRUCTION { ADV, BXL, BST, JNZ, BXC, OUT, BDV, CDV }

        fun day18_1() { //286
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day18.txt").readLines()
                    .subList(0, 1024)

//            println("map: $map")
            val noVertex = mutableSetOf<Pair<Int, Int>>()
            map.forEach { line ->
                noVertex.add(Pair(line.split(",")[0].toInt(), line.split(",")[1].toInt()))
            }

            val g: Graph<Pair<Int, Int>, DefaultEdge?> =
                SimpleGraph<Pair<Int, Int>, DefaultEdge?>(DefaultEdge::class.java)

            val maxRange = 70
            for (x in 0..maxRange) {
                for (y in 0..maxRange) {
                    if (!noVertex.contains(Pair(x, y))) {
                        g.addVertex(Pair(x, y))
                    }

                }
            }

            // add edges
            for (x in 0..maxRange) {
                for (y in 0..maxRange) {
                    // add horizontal
                    if (!noVertex.contains(Pair(x, y)) && !noVertex.contains(Pair(x + 1, y)) && x < maxRange) {
                        g.addEdge(Pair(x, y), Pair(x + 1, y))
                    }
                    // add vertical
                    if (!noVertex.contains(Pair(x, y)) && !noVertex.contains(Pair(x, y + 1)) && y < maxRange) {
                        g.addEdge(Pair(x, y), Pair(x, y + 1))
                    }
                }
            }

            val dijkstraAlg =
                DijkstraShortestPath<Pair<Int, Int>, DefaultEdge?>(g)
            val iPaths = dijkstraAlg.getPaths(Pair(0, 0))

            println("2024 day 18.1: ${iPaths.getPath(Pair(maxRange, maxRange)).length}")
        }

        fun day18_2() { //20,64
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day18.txt").readLines()

            var result = ""
            val noVertex = mutableSetOf<Pair<Int, Int>>()
            run ready@{
                map.forEachIndexed { index, line ->
                    noVertex.add(Pair(line.split(",")[0].toInt(), line.split(",")[1].toInt()))

                    val g: Graph<Pair<Int, Int>, DefaultEdge?> =
                        SimpleGraph<Pair<Int, Int>, DefaultEdge?>(DefaultEdge::class.java)

                    val maxRange = 70
                    for (x in 0..maxRange) {
                        for (y in 0..maxRange) {
                            if (!noVertex.contains(Pair(x, y))) {
                                g.addVertex(Pair(x, y))
                            }

                        }
                    }

                    // add edges
                    for (x in 0..maxRange) {
                        for (y in 0..maxRange) {
                            // add horizontal
                            if (!noVertex.contains(Pair(x, y)) && !noVertex.contains(Pair(x + 1, y)) && x < maxRange) {
                                g.addEdge(Pair(x, y), Pair(x + 1, y))
                            }
                            // add vertical
                            if (!noVertex.contains(Pair(x, y)) && !noVertex.contains(Pair(x, y + 1)) && y < maxRange) {
                                g.addEdge(Pair(x, y), Pair(x, y + 1))
                            }
                        }
                    }

                    val dijkstraAlg =
                        DijkstraShortestPath<Pair<Int, Int>, DefaultEdge?>(g)
                    val iPaths = dijkstraAlg.getPaths(Pair(0, 0))
                    if (iPaths.getPath(Pair(maxRange, maxRange)) == null) {
                        result = map[index]
                        return@ready
                    }
                }
            }

            println("2024 day 18.2: $result")
        }

        fun day19_1() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day19.txt").readLines()

            val patterns = map[0].split(", ").sortedBy { it.length }.reversed()
            val designs = map.subList(2, map.size).filter { !it.startsWith("wrbbr") }

            var good = 0
            designs.forEach { design ->
                var result = mutableSetOf(design)
//                var length = design.length
//                var newLength = 0
                loop@ while (true) {
//                    length = result.length
//                    newLength = result.length
                    var newResult = mutableSetOf<String>()
                    result.forEach { tmpDesign ->
                        patterns.forEach { pattern ->
                            if (tmpDesign.startsWith(pattern)) {
                                val tmp = tmpDesign.replace(pattern, "")
                                if(tmp.isEmpty()){
                                    println("good: $design")
                                    good++
                                    break@loop
                                }
                                newResult.add(tmp)
                            }
                        }
                    }
                    if(result == newResult){
                        println("bad: $design")
                        break@loop
                    }
                    result = newResult
                }
            }

            println("good: $good")
            println("2024 day 19.1: ")
        }

        fun day19_2() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day19.txt").readLines()

            println(map)
        }

        fun day20_1() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day20.txt").readLines()

            println(map)
        }

        fun day20_2() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day20.txt").readLines()

            println(map)
        }

        fun day21_1() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day21.txt").readLines()

            println(map)
        }

        fun day21_2() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day21.txt").readLines()

            println(map)
        }

        fun day22_1() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day22.txt").readLines()

            println(map)
        }

        fun day22_2() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day22.txt").readLines()

            println(map)
        }

        fun day23_1() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day23.txt").readLines()

            println(map)
        }

        fun day23_2() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day23.txt").readLines()

            println(map)
        }

        fun day24_1() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day24.txt").readLines()

            println(map)
        }

        fun day24_2() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day24.txt").readLines()

            println(map)
        }

        fun day25_1() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day25.txt").readLines()

            println(map)
        }

        fun day25_2() {
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day25.txt").readLines()

            println(map)
        }

//        fun test() {
//            val rawText =
//                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2024\\day7.txt").readLines()
//            val result = Day07(rawText).solvePart2()
//            println(result)
//        }

        fun advent2024() {
//            test()
//            day1_1()
//            day1_2()
//            day2_1()
//            day2_2()
//            day3_1()
//            day3_2()
//            day4_1()
//            day4_2()
//            day5_1()
//            day5_2()
//            day6_1()
//            day6_2()
//            day7_1()
//            day7_2()
//            day8_1()
//            day8_2()
//            day9_1()
//            day9_2()
//            day10_1()
//            day10_2()
//            day11_1()
//            day11_2()
//            day12_1()
//            day12_2()
//            day13_1()
//            day13_2()
//            day14_1()
//            day14_2()
//            day15_1()
//            day15_2()
//            day16_1()
//            day16_2()
//            day17_1()
//            day17_2()
//            day18_1()
//            day18_2()
            day19_1()
//            day19_2()
//            day20_1()
//            day20_2()
//            day21_1()
//            day21_2()
//            day22_1()
//            day22_2()
//            day23_1()
//            day23_2()
//            day24_1()
//            day24_2()
//            day25_1()
//            day25_2()
        }
    }
}

data class Path(
    var pathPoints: MutableSet<Pair<Int, Int>>,
    var currentDirection: Int,
    var directionChanged: Int = 0
) {

    enum class STATE { PROCESSING, FINISHED, DEAD }

    //    private var directionChanged = 0
    private var state = STATE.PROCESSING
    private val allExistingPathPoints = mutableSetOf<Pair<Int, Int>>()

    fun addAllExistingPathPoints(allPAthPoints: Set<Pair<Int, Int>>) {
        allExistingPathPoints.addAll(allPAthPoints)
    }

    fun getAllExistingPathPoints(): MutableSet<Pair<Int, Int>> {
        return allExistingPathPoints
    }

    fun getNumberOfAllExistingPathPoints(): Int {
        return allExistingPathPoints.size
    }

    fun addPathPoint(newPathPoint: Pair<Int, Int>, direction: Int): Boolean {
        if (state == STATE.DEAD) return false
        if (newPathPoint == targetPathPoint) {
            state = STATE.FINISHED
            println("found FINISHED: ${this.getNumberOfPathPoints()}, ${this.getNumberOfAllExistingPathPoints()}")
            return true
        }

        val size = pathPoints.size
        pathPoints.add(newPathPoint)
        if (size == pathPoints.size) {
            this.setToDead()
            return false
        }

        if (currentDirection != direction) {
            currentDirection = direction
            directionChanged++
        }
        return true
    }

    fun getAllPathPoints(): MutableSet<Pair<Int, Int>> {
        return pathPoints
    }

    fun setToDead() {
//        println("set to DEAD")
        state = STATE.DEAD
    }

    fun getNumberOfDirectionChanged(): Int {
        return directionChanged
    }

    fun getNumberOfPathPoints(): Int {
        return pathPoints.size
    }

    fun getState(): STATE {
        return state
    }

    fun getLastPathPoint(): Pair<Int, Int> {
        return pathPoints.last()
    }

    companion object {
        private lateinit var targetPathPoint: Pair<Int, Int>

        fun setTarget(targetPathPoint: Pair<Int, Int>) {
            Companion.targetPathPoint = targetPathPoint
        }
    }
}

