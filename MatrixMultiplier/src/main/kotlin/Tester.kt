import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun randomVector(m: Int): IntArray = IntArray(m) { Random.nextInt() }

fun randomMatrix(n: Int, m: Int): Matrix = Matrix(*Array(n) { randomVector(m) })

fun randomSpeedTest(repeatCount: Int, start: Int, end: Int, step: Int) {
    for (n in start..end step step) {
        var time: Long = 0
        for (i in 1..repeatCount) {
            time += speedTest(randomMatrix(n, n), randomMatrix(n, n))
        }
        println("n = $n, time = ${time / repeatCount} ms")
    }
}

fun speedTest(A: Matrix, B: Matrix): Long {
    return measureTimeMillis {
        A * B
    }
}

fun randomEqualTest(count: Int, maxN: Int, minN: Int = 1, correctFunction: (A: Matrix, B: Matrix) -> Matrix) {
    for (_i in 1..count) {
        val n = Random.nextInt(minN, maxN + 1)
        val m = Random.nextInt(minN, maxN + 1)
        val k = Random.nextInt(minN, maxN + 1)
        val a = randomMatrix(n, m)
        val b = randomMatrix(m, k)
        equalTest(a, b, a * b, correctFunction)
    }
    println("$count tests passed.")
}

fun equalTest(a: Matrix, b: Matrix, result: Matrix, func: (Matrix, Matrix) -> Matrix) {
    val answer = func(a, b)
    if (answer != result)
        throw RuntimeException(
            """Algorithm failed.
                    |A:
                    |$a
                    |B:
                    |$b
                    |Answer: 
                    |$answer
                    |Your result:
                    |$result
                    """.trimMargin()
        )
}
