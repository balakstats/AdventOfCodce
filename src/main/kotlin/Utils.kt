import java.io.File
import kotlin.math.abs

class Utils {
    companion object {
        fun readIntCsv(absolutePath: String): List<Int> {
            return File(absolutePath).readLines().map { it.toInt() }
        }

        fun readStringCsv(absolutePath: String): List<String> {
            return File(absolutePath).readLines()
        }

        fun isReportSafe(list: List<Int>): Boolean {
            val numbersIterator = list.iterator()
            var previousNumber = -1
            while (numbersIterator.hasNext()) {
                val next = numbersIterator.next()
                if (previousNumber < 0) {
                    previousNumber = next
                    continue
                }
                if (abs(previousNumber - next) > 3) {
                    return false
                }
                previousNumber = next
            }
            return true
        }

    }
}