package old

import java.io.File
import kotlin.math.abs

class Advent2017 {
    companion object {

        fun day1_1() { // 1171
            val rawText =
                File("C:\\Users\\bala\\adventOfCode\\adventOfCode\\src\\main\\resources\\2017\\day1.txt").readText()
            var result = 0

            rawText.forEachIndexed { index, c ->
                if (index == rawText.length - 1) {
                    if(rawText[index] == rawText[0]){
                        result += rawText[0].digitToInt()
                    }
                    println("2017 day 01.1: $result")
                    return
                }
                if (c == rawText[index + 1]) {
                    result += c.digitToInt()
                }
            }
        }

        fun advent2017() {
            day1_1()
//            day1_2()
        }
    }
}