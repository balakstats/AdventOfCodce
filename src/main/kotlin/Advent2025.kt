import java.io.File


class Advent2025 {
    companion object {

        fun day1_1() { // 1102
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2025\\day1.txt").readLines()
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
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2025\\day1.txt").readLines()

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
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2025\\day2.txt").readText()
                    .split(",")
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
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2025\\day2.txt").readText()
                    .split(",")
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

                    if(previousLength != iLength || divisors.isEmpty()) {
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
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2025\\day2.txt").readText()
                    .split(",")

//            val ranges = input.split(',')
//                .map { it.split('-') }
//                .map { Range(it[0].toLong(), it[1].toLong()) }
            val finalSet = mutableListOf<Long>()
            var invalidIdSum = 0L

            var counter = 0
            rawText.forEach { element ->
                val start = element.split("-")[0]
                val end = element.split("-")[1]
                rangeLoop@ for (id in start.toLong()..end.toLong()) {
                    val idString = id.toString()
                    for (patternSize in 1 .. idString.length / 2) {
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

        fun advent2025() {
//            day1_1()
//            day1_2()
//            day2_1()
            day2_2()
            go()
        }
    }
}