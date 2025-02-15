package old

import java.io.File

class Advent2023 {
    companion object {
        fun day1_1() { // 53651
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2023\\day1.txt").readLines()
                    .map {
                        ("[0-9]".toRegex().find(it)?.value ?: "") + ("[0-9]".toRegex().find(it.reversed())?.value ?: "")
                    }.sumOf { it.toInt() }
            println("2023 day 1.1: $map")
        }

        fun day1_2() { // 53894
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2023\\day1.txt").readLines()
                    .map {
                        ("[0-9]|one|two|three|four|five|six|seven|eight|nine".toRegex().find(it)?.value
                            ?: "") + "|" + ("[0-9]|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin".toRegex()
                            .find(it.reversed())?.value ?: "")
                    }.map {
                        var one = ""
                        var two = ""
                        if (it.split("|")[0].length == 1) {
                            one = it.split("|")[0]
                        } else {
                            one = when (it.split("|")[0]) {
                                "one" -> "1"
                                "eno" -> "1"
                                "two" -> "2"
                                "owt" -> "2"
                                "three" -> "3"
                                "eerht" -> "3"
                                "four" -> "4"
                                "ruof" -> "4"
                                "five" -> "5"
                                "evif" -> "5"
                                "six" -> "6"
                                "xis" -> "6"
                                "seven" -> "7"
                                "neves" -> "7"
                                "eight" -> "8"
                                "thgie" -> "8"
                                "nine" -> "9"
                                "enin" -> "9"
                                else -> "x"
                            }
                        }
                        if (it.split("|")[1].length == 1) {
                            two = it.split("|")[1]
                        } else {
                            two = when (it.split("|")[1]) {
                                "one" -> "1"
                                "eno" -> "1"
                                "two" -> "2"
                                "owt" -> "2"
                                "three" -> "3"
                                "eerht" -> "3"
                                "four" -> "4"
                                "ruof" -> "4"
                                "five" -> "5"
                                "evif" -> "5"
                                "six" -> "6"
                                "xis" -> "6"
                                "seven" -> "7"
                                "neves" -> "7"
                                "eight" -> "8"
                                "thgie" -> "8"
                                "nine" -> "9"
                                "enin" -> "9"
                                else -> "y"
                            }
                        }

                        (one  + two).toInt()
                    }.sum()

            println(map)
            println("2023 day 1.2: ")
        }

        fun advent2023() {
//            day1_1()
            day1_2()
        }
    }
}