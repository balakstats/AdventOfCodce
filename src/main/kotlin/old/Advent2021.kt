package old

import java.io.File

class Advent2021 {
    companion object {
        fun day1_1() { // 1681
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2021\\day1.txt").readLines()
                    .map { it.toInt() }
            var result = 0
            map.forEachIndexed { index, i ->
                if (index == 0) return@forEachIndexed
                result += if (map[index - 1] < i) 1 else 0
            }
            println("2021 day 1.1: $result")
        }

        fun day1_2() { // 1704
            val map =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2021\\day1.txt").readLines()
                    .map { it.toInt() }

            val newMap = map.toMutableList()
            map.forEachIndexed { index, i ->
                if(index > map.size - 3) return@forEachIndexed
                newMap[index] = newMap[index] + newMap[index + 1] + newMap[index + 2]
            }
            newMap.removeLast()
            newMap.removeLast()
            var result = 0
            newMap.forEachIndexed go@{ index, i ->
                if (index == 0) return@go
                result += if (newMap[index - 1] < i) 1 else 0
            }

            println(newMap)
            println("2021 day 1.2: $result")
        }

        fun advent2021() {
//            day1_1()
            day1_2()
        }
    }
}