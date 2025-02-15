package old

import java.io.File

class Advent2019 {
    companion object{
        fun day1_1(){ // 3443395
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2019\\day1.txt").readLines()
            val result = map.sumOf { (it.toInt() / 3) - 2 }
            println("2019 day 1.1: $result")
        }

        fun day1_2(){ // 5162216
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2019\\day1.txt").readLines()

            val result = map.sumOf {
                var tmp = (it.toInt() / 3) - 2
                var result = tmp
                while(tmp > 8){
                    tmp = (tmp / 3) - 2
                    result += tmp
                }
                result
            }

            println("2019 day 1.2: $result")
        }


        fun advent2019(){
//            day1_1()
            day1_2()
        }
    }
}