package old

import java.io.File

class Advent2015 {

    companion object {

        fun day1_1() { // 280
            val rawText =
                File("C:\\Users\\bala\\adventOfCode\\adventOfCode\\src\\main\\resources\\2015\\day1.txt").readText()
            val up = rawText.split("(").size - 1
            val down = rawText.split(")").size - 1

            print("2015 day 01.1: ${up - down}, ")
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
            print("2015 day 01.2: $result, ")
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
            print("2015 day 02.1: $result, ")
        }

        fun day2_2() {
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day2.txt").readLines()
            var result = 0
            rawText.forEach {
                val dimensions = it.split("x")
                val x = dimensions[0].toInt()
                val y = dimensions[1].toInt()
                val z = dimensions[2].toInt()
                val sorted = listOf(x, y, z).sorted()
                result += sorted[0] + sorted[0] + sorted[1] + sorted[1]
                result += x * y * z
            }
            print("2015 day 02.2: $result, ")
        }

        fun day3_1() { // 3_1: 2081, 3_2 2341
            val rawText =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day3.txt").readText()

            val allVerticesSanta = mutableListOf<Pair<Int, Int>>()
            val allVerticesRobo = mutableListOf<Pair<Int, Int>>()

            allVerticesSanta.add(Pair(0, 0))
            allVerticesRobo.add(Pair(0, 0))
            fun addVertice(list: MutableList<Pair<Int, Int>>, direction: Char) {

                when (direction) {
                    '^' -> list.add(Pair(list.last().first, list.last().second + 1))
                    'v' -> list.add(Pair(list.last().first, list.last().second - 1))
                    '>' -> list.add(Pair(list.last().first + 1, list.last().second))
                    '<' -> list.add(Pair(list.last().first - 1, list.last().second))
                }

            }

            var toggle = true
            rawText.forEach { direction ->
                if (toggle) {
                    addVertice(allVerticesSanta, direction)
                } else {
                    addVertice(allVerticesRobo, direction)
                }
                toggle = !toggle
            }

            allVerticesSanta.addAll(allVerticesRobo)
            println("2015 day3.2: ${allVerticesSanta.toSet().size}")
        }

//        fun day3_2() {
//            val rawText =
//                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2015\\day3.txt").readText()
//
//
//
//            println("2015 day3.2: ")
//        }

        fun advent2015() {
//            day1_1()
//            day1_2()
//            day2_1()
//            day2_2()
            day3_1()
//            day3_2()
        }
    }
}