import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Advent2024Test {

    @Test
    fun go() {
        val test = mutableSetOf<Pair<Pair<Int,Int>,Int>>()
        test.add(Pair(Pair(1,2),1))
        test.add(Pair(Pair(1,2),1))
        test.add(Pair(Pair(1,2),2))
        println("size: ${test.size}")
    }
}