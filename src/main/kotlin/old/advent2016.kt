package old

import java.io.File
import java.lang.Math.floorMod
import kotlin.math.abs

class Advent2016 {
    companion object {
        fun day1_1() { // 300
            val directions =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2016\\day1.txt").readText()
                    .split(", ")
            var x = 0
            var y = 0

            var orientation = 0
            var blocks: Int
            directions.forEach {
                orientation += if (it.startsWith("R")) 1 else -1
                blocks = it.substring(1).toInt()
                when (floorMod(orientation, 4)) {
                    0 -> y += blocks
                    1 -> x += blocks
                    2 -> y -= blocks
                    3 -> x -= blocks
                }
            }
            println("2016 day 01.1: ${abs(x) + abs(y)}")
        }

        fun day1_2() {
            val directions =
                File("C:\\Users\\bala\\IdeaProjects\\AdventOfCodce\\src\\main\\resources\\2016\\day1.txt").readText()
                    .split(", ")

            var x = 0
            var y = 0
            var orientation = 0
            var blocks: Int
            val mySet = mutableSetOf<Pair<Int, Int>>()
            mySet.add(Pair(y, x))
            run outer@{
                directions.forEach {
                    orientation += if (it.startsWith("R")) 1 else -1
                    blocks = it.substring(1).toInt()
                    when (floorMod(orientation, 4)) {
                        0 -> {
                            for (i in 1..blocks) {
                                val size = mySet.size
                                mySet.add(Pair(++y, x))
                                if (size == mySet.size) {
                                    return@outer
                                }
                            }
                        }

                        1 -> {
                            for (i in 1..blocks) {
                                val size = mySet.size
                                mySet.add(Pair(y, ++x))
                                if (size == mySet.size) {
                                    return@outer
                                }
                            }
                        }
                        2 -> {
                            for (i in 1..blocks) {
                                val size = mySet.size
                                mySet.add(Pair(--y, x))
                                if (size == mySet.size) {
                                    return@outer
                                }
                            }
                        }
                        3 -> {
                            for (i in 1..blocks) {
                                val size = mySet.size
                                mySet.add(Pair(y, --x))
                                if (size == mySet.size) {
                                    return@outer
                                }
                            }
                        }
                    }
                }
            }

            println("2016 day 01.2: ${abs(x) + abs(y)}")
        }

        fun advent2016() {
//            day1_1()
            day1_2()
        }
    }
}