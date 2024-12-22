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
                if (temp == -1 && result == 0) {
                    result = index
                }
            }
            println("2015 day 01.2: $result")
        }

        fun day2_1() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day2.txt").readLines()
            var result = 0
            rawText.forEach {
                val dimensions = it.split("x")
                val surface1 = 2 * dimensions[0].toInt() * dimensions[1].toInt()
                val surface2 = 2 * dimensions[0].toInt() * dimensions[2].toInt()
                val surface3 = 2 * dimensions[1].toInt() * dimensions[2].toInt()
                val smallest = listOf(surface1, surface2, surface3).sorted()[0] / 2
                result += surface1 + surface2 + surface3 + smallest
            }
            println("2015 day 02.1: $result")
        }

        fun day2_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day2.txt").readLines()
//            val rawText = listOf("2x3x4")
            var result = 0
            rawText.forEach {
                val dimensions = it.split("x")
                val x = dimensions[0].toInt()
                val y = dimensions[1].toInt()
                val z = dimensions[2].toInt()
                val sorted = listOf(x, y, z).sorted()
//                val surface1 = 2 * dimensions[0].toInt() * dimensions[1].toInt()
//                val surface2 = 2 * dimensions[0].toInt() * dimensions[2].toInt()
//                val surface3 = 2 * dimensions[1].toInt() * dimensions[2].toInt()
//                val smallest = listOf(surface1,surface2,surface3).sorted()[0] / 2
                result += sorted[0] + sorted[0] + sorted[1] + sorted[1]
                result += x * y * z
            }
            println("2015 day 02.2: $result")
        }

        fun advent2015() {
            day1_1()
            day1_2()
            day2_1()
            day2_2()
        }
    }
}