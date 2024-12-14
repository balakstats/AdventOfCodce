import java.io.File

class Advent2015 {

    companion object {

        fun day1_1() { // 280
            val rawText =
                File("C:\\Users\\bala\\adventOfCode\\adventOfCode\\src\\main\\resources\\2015\\day1.txt").readText()
            val up = rawText.split("(").size - 1
            val down = rawText.split(")").size - 1

            println("2015 day 01.1: ${up - down}")
        }

        fun day1_2() { // 1797
            val rawText =
                File("C:\\Users\\bala\\adventOfCode\\adventOfCode\\src\\main\\resources\\2015\\day1.txt").readText()
            var index = 0
            var temp = 0
            var result = 0
            rawText.forEach {
                index++
                temp += if (it.toString() == "(") 1 else -1
                if(temp == -1 && result == 0){
                    result = index
                }
            }
            println("2015 day 01.2: $result")
        }

        fun advent2015() {
            day1_1()
            day1_2()
        }
    }
}