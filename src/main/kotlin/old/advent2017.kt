package old

import java.io.File

class Advent2017 {
    companion object {

        fun day1_1() { // 1171
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2017\\day1.txt").readText()
            var result = 0

            run outer@{
                rawText.forEachIndexed { index, c ->
                    if (index == rawText.length - 1) {
                        if (rawText[index] == rawText[0]) {
                            result += rawText[0].digitToInt()
                        }
                        return@outer
                    }
                    if (c == rawText[index + 1]) {
                        result += c.digitToInt()
                    }
                }
            }

            println("2017 day 01.1: $result")
        }

        fun day1_2() { // 1024
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2017\\day1.txt").readText()

            val length = rawText.length
            var result = 0
            run outer@{
                rawText.forEachIndexed { index, c ->
                    println("$c,${rawText[(index + (length / 2)) % length]}")
                    if (c == rawText[(index + (length / 2)) % length]) {
                        result += c.digitToInt()
                    }
                }
            }

            println("2017 day 01.2: $result")
        }

        fun advent2017() {
//            day1_1()
            day1_2()
        }
    }
}