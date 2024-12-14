import Utils.Companion.isReportSafe
import Utils.Companion.readIntCsv
import Utils.Companion.readStringCsv
import java.io.File
import kotlin.math.abs

class Advent2024 {
    // C:\Users\bala\IdeaProjects\AdventOfCodce\src\main\resources\2024\day1_list1.csv
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

        fun day7_1() {

        }

        fun advent2024() {
            day1_1()
            day1_2()
            day2_1()
            day2_2()
            day3_1()
            day3_2()
            day4_1()
            day4_2()
            day5_1()
            day5_2()
            day6_1()
            day6_2()
            day7_1()
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
//            day19_1()
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