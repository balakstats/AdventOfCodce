package old

import java.io.File

class Advent2022 {
    companion object {
        fun day1_1() { // 69310
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2022\\day1.txt").readText()
                    .split("\r\n\r\n").map { e -> e.split("\r\n").map { it.toInt() } }.map { it.sum() }.sorted()
            println(map)
            println("2022 day 1.1: ${map.last()}")
        }

        fun day1_2() { // 206104
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2022\\day1.txt").readText()
                    .split("\r\n\r\n").map { e -> e.split("\r\n").map { it.toInt() } }.map { it.sum() }
                    .sortedDescending()
            println(map)
            println("2022 day 1.2: ${map[0] + map[1] + map[2]}")
        }

        fun advent2022() {
//            day1_1()
            day1_2()
        }
    }
}