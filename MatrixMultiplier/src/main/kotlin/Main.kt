import java.util.*
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis

fun main() {
    println("Считывание первой матрицы")
    val a = readMatrix()
    println("Считывание второй матрицы")
    val b = readMatrix()
    val c: Matrix
    println(
        "Time : ${
            measureTimeMillis {
                c = a * b
            }
        } ms"
    )
    println(
        """result:
        |$c
    """.trimMargin()
    )
    println("Check via naive algorithm...")
    equalTest(a, b, c, ::multiplyNaive)
    println("Random speed testing")
    randomSpeedTest(3, start = 100, end = 2000, step = 100)
}

fun readMatrix(): Matrix {
    val sc = Scanner(System.`in`)
    println("Введите n:")
    val n = safeReadInt(sc)
    println("Введите m:")
    val m = safeReadInt(sc)
    println("Введите матрицу из ${n * m} элементов:")
    val array = Array(n) { IntArray(m) { 0 } }
    for (intArray in array) {
        for (i in 0 until m) {
            intArray[i] = safeReadInt(sc)
        }
    }
    return Matrix(*array)
}

fun safeReadInt(sc: Scanner): Int {
    if (sc.hasNextInt()) {
        return sc.nextInt()
    } else {
        println("${sc.next()} не является числом. Пожалуйста, повторите попытку")
        exitProcess(0)
    }
}
