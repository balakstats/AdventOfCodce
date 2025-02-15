package old

import java.io.File

class Advent2020 {
    companion object {
        fun day1_1() { // 802011
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2020\\day1.txt").readLines()
                    .map { it.toInt() }
            val pairSumsTo2020 = map.filter { map.contains(2020 - it) }
            println("2020 day 1.1: ${pairSumsTo2020.first() * pairSumsTo2020.last() }")
        }


        fun advent2020() {
            day1_1()
//            day1_2()
        }
    }
}