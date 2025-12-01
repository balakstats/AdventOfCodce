import java.io.File


class Advent2025 {
    companion object {

        fun day1_1(){
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2025\\day1.txt").readLines()
            var start = 50
            var result = 0
            rawText.forEach {
                val number = it.substring(1).toInt()
                if( (if (it.startsWith("R")) start + number else start - number) % 100 == 0){
                    println("yes")
                    result++
                }
                start = if (it.startsWith("R")) start + number else start - number
            }

            println("2025 day 1.1: $result")
        }

        fun day1_2(){
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2025\\day1.txt").readLines()


        }

        fun advent2025() {
//            day1_1()
            day1_2()
        }
    }
}