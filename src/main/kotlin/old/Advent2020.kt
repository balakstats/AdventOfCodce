package old

import java.io.File

class Advent2020 {
    companion object {
        fun day1_1() { // 802011
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2020\\day1.txt").readLines()
                    .map { it.toInt() }
            val pairSumsTo2020 = map.filter { map.contains(2020 - it) }
            println("2020 day 1.1: ${pairSumsTo2020.first() * pairSumsTo2020.last()}")
        }

        fun day1_2() { // 248607374
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2020\\day1.txt").readLines()
                    .map { it.toInt() }
            var first = 0L
            var second = 0L
            var third = 0L
            run outer@{
                map.forEach { firstTmp ->
                    map.forEach { secondTmp ->
                        map.forEach { thirdTmp ->
                            if(firstTmp + secondTmp + thirdTmp == 2020){
                                first = firstTmp.toLong()
                                second = secondTmp.toLong()
                                third = thirdTmp.toLong()
                                return@outer
                            }
                        }
                    }
                }
            }
            println("2020 day 1.2: ${first * second * third}")
        }

        fun advent2020() {
//            day1_1()
            day1_2()
        }
    }
}