
import kotlin.jvm.java


class Advent2025 {
    companion object {

        fun day1_1() { // 1102
            val rawText =
                this::class.java.classLoader?.getResource("2025/day1.txt")?.readText()?.split("\r\n") ?: return
            var start = 50
            var result = 0
            rawText.forEach {
                val number = it.substring(1).toInt()
                if ((if (it.startsWith("R")) start + number else start - number) % 100 == 0) {
                    println("yes")
                    result++
                }
                start = if (it.startsWith("R")) start + number else start - number
            }

            println("2025 day 1.1: $result")
        }

        fun day1_2() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day1.txt")?.readText()?.split("\r\n") ?: return

            val mod = 100
            var start = 50
            var result = 0
            rawText.forEach {
                var number = it.substring(1).toInt()
                println("number = $number; ${number / mod}; ${number % mod}")
                result += number / mod
                number %= mod
                if ((if (it.startsWith("R")) start + number else start - number) % mod == 0) {
                    println("yes")
                    result++
                    start = 0
                    return@forEach
                }
                if (start == 0) {
                    start = if (it.startsWith("R")) number else 0 - number
                    if (start < 0) {
                        start += 100
                    }
                    return@forEach
                }
                val sign = start > 0
                start = if (it.startsWith("R")) start + number else start - number
                if (sign != (start > 0) || (start > 100)) {
                    result++
                }
                if (start < 0) {
                    start += 100
                }
                if (start > 100) {
                    start %= 100
                }
                if (start == 100) {
                    start = 0
                }
            }

            println("2025 day 1.2: $result")
        }

        fun day2_1() { /// 40055209690
            val rawText =
                this::class.java.classLoader?.getResource("2025/day2.txt")?.readText()?.split(",") ?: return
            var result = 0L
            rawText.forEach {
                val start = it.split("-")[0]
                val end = it.split("-")[1]
                val lengthStart = start.length
                val lengthEnd = end.length
                if (lengthStart == lengthEnd && lengthStart % 2 != 0) {
                    println("not checked: $start")
                    return@forEach
                }

                for (i in start.toLong()..end.toLong()) {
                    if (i.toString().length > 3 && i.toString().length % 2 == 0) {
                        val first = i.toString().substring(0, (i.toString().length / 2))
                        val second = i.toString().substring(i.toString().length / 2)
                        if (first == second) {
                            println("$i")
                            result += i
                        }
                    }

                    if (i.toString().length == 2) {
                        val first = i.toString()[0]
                        val second = i.toString()[1]
                        if (first == second) {
                            println("found: $i")
                            result += i
                        }
                    }
                }
            }

            println("2025 day 2.1: $result")
        }

        fun day2_2() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day2.txt")?.readText()?.split(",") ?: return
            var result = 0L
            var counter = 0
            val primes = setOf(2, 3, 5, 7)
            val finalSet = mutableListOf<Long>()

            rawText.forEach {
                val start = it.split("-")[0]
                val end = it.split("-")[1]

                val divisors = mutableSetOf<Int>()
                var previousLength = 0

                rangeLoop@ for (i in start.toLong()..end.toLong()) {
                    val iLength = i.toString().length
                    val pattern = Regex("^([0-9])\\1*$")
                    if (iLength > 1 && pattern.containsMatchIn(i.toString())) {
//                        println("found: $i")
                        result += i
                        counter++
                        finalSet.add(i)
                        continue
                    }
                    if (primes.contains(iLength)) {
                        continue
                    }
                    if (iLength == 1) {
                        continue
                    }

                    if (previousLength != iLength || divisors.isEmpty()) {
                        for (j in 2..iLength) {
                            if (iLength % j == 0) {
                                divisors.add(j)
                            }
                            if (j > (iLength / 2)) {
                                break
                            }
                        }
//                        print(divisors)
                    }
                    previousLength = iLength

                    var subParts: List<String>?
                    divisors.forEach { divisor ->
                        subParts = i.toString().chunked(divisor)
                        if (subParts.all { part -> part == subParts[0] }) {
//                            println(i)
                            counter++
                            finalSet.add(i)
                            result += i
                            continue@rangeLoop
                        }
                    }

                }
            }

            println("2025 day 2.2: $result")
            println("2025 day 2.2: $counter")
            println(finalSet.sorted())
        }

        fun go() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day2.txt")?.readText()?.split(",") ?: return

            val finalSet = mutableListOf<Long>()
            var invalidIdSum = 0L

            var counter = 0
            rawText.forEach { element ->
                val start = element.split("-")[0]
                val end = element.split("-")[1]
                rangeLoop@ for (id in start.toLong()..end.toLong()) {
                    val idString = id.toString()
                    for (patternSize in 1..idString.length / 2) {
                        if (idString.length % patternSize == 0) {
                            val chunked = idString.chunked(patternSize)
                            if (chunked.all { it == chunked[0] }) {
//                                println("Found invalid id $id for pattern size $patternSize")
                                invalidIdSum += id
                                finalSet.add(id)
                                counter++
                                continue@rangeLoop
                            }
                        }
                    }
                }
            }
            println("Sum: $invalidIdSum")
            println("Sum: $counter")
            println(finalSet.sorted())
        }

        fun day3_1() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day3.txt")?.readText()?.split("\r\n") ?: return

            var result = 0L
            var i = 0
            rawText.forEach { line ->
                val charArray = line.map { Integer.parseInt(it.toString()) }
                val find = charArray.sortedDescending()
                val first = find[0]
                val second = find[1]
                if (first == second) {
                    result += Integer.parseInt("${first}$second")
                    println("a${i++}: ${Integer.parseInt("${first}$second")} - $result")
                } else {
                    val numberOfElements = charArray.size
                    val indexFirst = charArray.indexOfFirst { it == first }
                    val indexSecond = charArray.indexOfFirst { it == second }
                    if (indexFirst < indexSecond) {
                        val add = Integer.parseInt("${first}$second")
                        result += add
                        println("b${i++}: $add - $result")
                        return@forEach
                    }
                    if (indexFirst > indexSecond) {
                        if (indexFirst == numberOfElements - 1) {
                            val add = Integer.parseInt("${second}$first")
                            result += add
                            println("c${i++}: $add - $result")
                        } else {
                            val tmpArray = charArray.subList(indexFirst + 1, numberOfElements)
                            val newSecond = tmpArray.sortedDescending()[0]
                            val add = Integer.parseInt("${first}$newSecond")
                            result += add
                            println("d${i++}: $add - $result")
                        }
                        return@forEach
                    }
                }
            }


            println("2025 day 3.1: $result")
        }

        fun day3_2() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day3.txt")?.readText()?.split("\r\n") ?: return

            var result = 0L
            val length = rawText[0].length

            rawText.forEach { line ->
                var tmpResult = ""
                var currentIndex = 0
                for (index in 11 downTo 0) {
                    val subLine = line.substring(currentIndex, length - index).map { Integer.parseInt(it.toString()) }
                    val highestValue = subLine.sortedDescending()[0]
                    currentIndex += subLine.indexOfFirst { it == highestValue } + 1
                    tmpResult = "$tmpResult$highestValue"
                    println("index: $subLine; $tmpResult")
                }
                result += tmpResult.toLong()
            }


            println("2025 day 3.2: $result")
        }

        private fun day4_1() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day4.txt")?.readText()?.split("\r\n") ?: return
            var result = 0
            val maxY = rawText.size
            val maxX = rawText[0].length


            rawText.forEachIndexed { y, line ->
                line.forEachIndexed { x, _ ->
                    if (rawText[y][x] != '@') {
                        return@forEachIndexed
                    }
                    var adjRollsFound = 0
                    // look back
                    if (y > 0 && x > 0 && (rawText[y - 1][x - 1] == '@')) {
                        adjRollsFound++
                    }
                    if (x > 0 && (rawText[y][x - 1] == '@')) {
                        adjRollsFound++
                    }
                    if (y < maxY - 1 && x > 0 && (rawText[y + 1][x - 1] == '@')) {
                        adjRollsFound++
                    }

                    // look straight
                    if (y > 0 && (rawText[y - 1][x] == '@')) {
                        adjRollsFound++
                    }
                    if (y < maxY - 1 && (rawText[y + 1][x] == '@')) {
                        adjRollsFound++
                    }

                    // look ahead
                    if (y > 0 && x < maxX - 1 && (rawText[y - 1][x + 1] == '@')) {
                        adjRollsFound++
                    }
                    if (x < maxX - 1 && (rawText[y][x + 1] == '@')) {
                        adjRollsFound++
                    }
                    if (y < maxY - 1 && x < maxX - 1 && (rawText[y + 1][x + 1] == '@')) {
                        adjRollsFound++
                    }

                    if (adjRollsFound < 4) {
                        result += 1
                    }
                }
            }

            println("2025 day 4.1: $result")
        }

        private fun day4_2() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day4.txt")?.readText()?.split("\r\n")?.toMutableList()
                    ?: return

            var result = 0
            val maxY = rawText.size
            val maxX = rawText[0].length

            var removeY = -1
            var removeX = -1
            loop@ while (true) {
                if (removeX >= 0) {
                    val tmp = rawText[removeY].toCharArray()
                    tmp[removeX] = '.'
                    rawText[removeY] =
                        tmp.contentToString().replace(",", "").replace("[", "").replace("]", "").replace(" ", "")
                    removeX = -1
                    removeY = -1
                }
                run go@{
                    rawText.forEachIndexed outer@{ y, line ->
                        line.forEachIndexed { x, _ ->
                            if (rawText[y][x] != '@') {
                                if (x == maxX - 1 && y == maxY - 1) {
                                    break@loop
                                }
                                return@forEachIndexed
                            }
                            var adjRollsFound = 0
                            // look back
                            if (y > 0 && x > 0 && (rawText[y - 1][x - 1] == '@')) {
                                adjRollsFound++
                            }
                            if (x > 0 && (rawText[y][x - 1] == '@')) {
                                adjRollsFound++
                            }
                            if (y < maxY - 1 && x > 0 && (rawText[y + 1][x - 1] == '@')) {
                                adjRollsFound++
                            }

                            // look straight
                            if (y > 0 && (rawText[y - 1][x] == '@')) {
                                adjRollsFound++
                            }
                            if (y < maxY - 1 && (rawText[y + 1][x] == '@')) {
                                adjRollsFound++
                            }

                            // look ahead
                            if (y > 0 && x < maxX - 1 && (rawText[y - 1][x + 1] == '@')) {
                                adjRollsFound++
                            }
                            if (x < maxX - 1 && (rawText[y][x + 1] == '@')) {
                                adjRollsFound++
                            }
                            if (y < maxY - 1 && x < maxX - 1 && (rawText[y + 1][x + 1] == '@')) {
                                adjRollsFound++
                            }

                            if (adjRollsFound < 4) {
                                result++
                                removeX = x
                                removeY = y
                                if (x == maxX - 1 && y == maxY - 1) {
                                    break@loop
                                }
                                return@go
                            }
                            if (x == maxX - 1 && y == maxY - 1) {
                                break@loop
                            }
                        }
                    }
                }
            }

            println("2025 day 4.2: $result")
        }

        fun day5_1() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day5.txt")?.readText()?.split("\r\n") ?: return
            var result = 0

            val ranges = rawText.filter { it.contains("-") }
            val ids =
                rawText.filter { !it.contains("-") && it.isNotEmpty() }.map { it.toLong() }.toMutableSet().sorted()
            ids.forEach outer@{ id ->
                ranges.forEach {
                    val start = it.split("-")[0].toLong()
                    val stop = it.split("-")[1].toLong()
                    if (id in start..stop) {
                        result++
                        return@outer
                    }
                }
            }
            println("2025 day 5.1: $result")
        }

        fun day5_2() { // 336495597913098
            val rawText =
                this::class.java.classLoader?.getResource("2025/day5.txt")?.readText()?.split("\r\n") ?: return
            var result = 0L

            val ranges =
                rawText.filter { it.contains("-") }.map { Pair(it.split("-")[0].toLong(), it.split("-")[1].toLong()) }
                    .sortedBy { it.first }
            val distinctRanges = mutableListOf<Pair<Long, Long>>()
            ranges.forEach {
                if (distinctRanges.isEmpty()) {
                    distinctRanges.add(it)
                    return@forEach
                }

                if (it.first <= distinctRanges.last().second) {
                    if (it.second > distinctRanges.last().second) {
                        distinctRanges[distinctRanges.size - 1] = Pair(distinctRanges.last().first, it.second)
                    }
                } else {
                    distinctRanges.add(it)
                }
            }

            distinctRanges.forEach {
                result += it.second - it.first + 1
            }

            println("2025 day 5.2: $result")
        }

        fun day6_1() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day6.txt")?.readText()?.split("\r\n") ?: return
            var result = 0L
            val numOfLines = rawText.size
            val operators = rawText[numOfLines - 1].split("\\s+".toRegex())
            rawText[0].split("\\s+".toRegex()).forEachIndexed { index, firstElement ->
                var tmpResult = 0L + Integer.parseInt(firstElement)
                if (operators[index].trim() == "+") {
                    for (i in 1 until numOfLines - 1) {
                        tmpResult += rawText[i].split("\\s+".toRegex())[index].toLong()
                    }
                }
                if (operators[index].trim() == "*") {
                    for (i in 1 until numOfLines - 1) {
                        tmpResult *= rawText[i].split("\\s+".toRegex())[index].toLong()
                    }
                }
                result += tmpResult
            }

            println("2025 day 6.1: $result")
        }

        fun day6_2() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day6.txt")?.readText()?.split("\r\n") ?: return
            var result = 0L
            var operator = rawText[4][0]
            var tmpResult = if (operator == '*') 1L else 0L
            val row1Max = rawText[1].length
            val row2Max = rawText[2].length
            val row3Max = rawText[3].length
            rawText[0].forEachIndexed { index, ch ->
                if (ch == ' ' && rawText[1][index] == ' ' && rawText[2][index] == ' ' && rawText[3][index] == ' ') {
                    operator = rawText[4][index + 1]
                    result += tmpResult
                    println("index: $index, $tmpResult")
                    tmpResult = if (operator == '*') 1L else 0L
                    return@forEachIndexed
                }

                if (operator == '+') {
                    tmpResult += Integer.parseInt("$ch${if (index > row1Max - 1) "" else rawText[1][index]}${if (index > row2Max - 1) "" else rawText[2][index]}${if (index > row3Max - 1) "" else rawText[3][index]}".trim())
                }
                if (operator == '*') {
                    tmpResult *= Integer.parseInt("$ch${if (index > row1Max - 1) "" else rawText[1][index]}${if (index > row2Max - 1) "" else rawText[2][index]}${if (index > row3Max - 1) "" else rawText[3][index]}".trim())
                }
            }
            println("last: $tmpResult")
            result += tmpResult
            println("2025 day 6.2: $result")
        }

        fun day7_1() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day7.txt")?.readText()?.split("\r\n") ?: return
            var result = 0L

            val beams = mutableListOf(rawText[0].indexOfFirst { it == 'S' })
            rawText.forEachIndexed { index, string ->
                if (index % 2 != 0 || index == 0) {
                    return@forEachIndexed
                }

                string.forEachIndexed { index, ch ->
                    if (ch == '^') {
                        val beam = beams.firstOrNull { it == index }
                        if (beam != null) {
                            result++
                            beams.removeAll { it == index }
                            beams.add(index - 1)
                            beams.add(index + 1)
                        }
                    }
                }
            }

            println("2025 day 7.1: $result")
        }

        fun day7_2() {
            val rawText =
                this::class.java.classLoader?.getResource("2025/day7.txt")?.readText()?.split("\r\n") ?: return
            var result = 0L

            var paths = mutableListOf(Pair(1, rawText[0].indexOfFirst { it == 'S' }))
            result++
            rawText.forEachIndexed { index, string ->
                if (index % 2 != 0 || index == 0) {
                    return@forEachIndexed
                }
                println("index: $index, ${paths.size}")

                string.forEachIndexed { innerIndex, ch ->
                    val beams = paths.firstOrNull { it.second == innerIndex }
                    if (ch == '^' && beams != null) {
                        paths.remove(beams)
                        paths.add(Pair(beams.first + 1, innerIndex + 1))
                        result++

                    }
                }
            }

            // 3263 too low
            println("2025 day 7.2: $result")
        }

        fun advent2025() {
//            day1_1()
//            day1_2()
//            day2_1()
//            day2_2()
//            go()
//            day3_1()
//            day3_2()
//            day4_1()
//            day4_2()
//            day5_1()
//            day5_2()
//            day6_1()
//            day6_2()
//            day7_1()
            day7_2()
        }


    }
}