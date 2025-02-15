package old

import java.io.File

class Advent2018 {
    companion object{
        fun day1_1(){ // 590
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2018\\day1.txt").readLines()
            val result = map.sumOf { it.toInt() }
            println("2018 day 1.1: $result")
        }

        fun day1_2(){ // 83445
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2018\\day1.txt").readLines()
            var result = 0
            val frequencies = mutableSetOf<Int>()
            frequencies.add(0)
            var index = 0
            run outer@{
                while(true) {
                    println("loop: ${++index}")
                    map.forEach {
                        val size = frequencies.size
                        frequencies.add(frequencies.last() + it.toInt())
                        if (size == frequencies.size) {
                            result = frequencies.last() + it.toInt()
                            return@outer
                        }
                    }
                    val first = frequencies.minOf { it }
                    frequencies.removeIf { it < first + 590 }
                }
            }

            println("2018 day 1.2: $result")
        }


        fun advent2018(){
//            day1_1()
            day1_2()
        }
    }
}