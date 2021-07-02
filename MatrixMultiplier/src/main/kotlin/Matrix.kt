operator fun Matrix.times(other: Matrix): Matrix {
    requireMultiplySize(this, other)
    return multiplyFast(this, other)
}

fun multiplyFast(A: Matrix, B: Matrix): Matrix {
    val array = Array(A.n) { IntArray(B.m) { 0 } }
    for (i in 0 until A.n) {
        for (k in 0 until A.m) {
            val first = A[i][k]
            for (j in 0 until B.m) {
                array[i][j] += first * B[k][j]
            }
        }
    }
    return Matrix(*array)
}

fun blockedSlow(A: Matrix, B: Matrix): Matrix {
    val array = Array(A.n) { IntArray(B.m) { 0 } }
    val blockSize = 128
    for (kk in 0 until A.n step blockSize) {
        for (jj in 0 until A.n step blockSize) {
            for (i in 0 until A.n) {
                for (j in jj until jj + blockSize) {
                    var sum = array[i][j]
                    for (k in kk until kk + blockSize) {
                        sum += A[i][k] * B[k][j]
                    }
                    array[i][j] = sum
                }
            }
        }
    }
    return Matrix(*array)
}

fun multiplyNaive(A: Matrix, B: Matrix): Matrix {
    val array = Array(A.n) { IntArray(B.m) { 0 } }
    for (i in 0 until A.n) {
        for (j in 0 until B.m) {
            var sum = 0
            for (k in 0 until A.m) {
                sum += A[i][k] * B[k][j]
            }
            array[i][j] = sum
        }
    }
    return Matrix(*array)
}

private operator fun Matrix.get(i: Int): IntArray {
    return elements[i]
}


@Suppress("NOTHING_TO_INLINE")
internal inline fun requireMultiplySize(A: Matrix, B: Matrix) {
    require(A.m == B.n)
}

class Matrix(vararg _elements: IntArray) {
    internal val n = _elements.size
    internal val elements = _elements.toList()
    internal val m: Int

    init {
        if (n == 0) {
            throw IllegalArgumentException("Rows count must be positive")
        }
        m = elements[0].size
        for (i in 1 until n) {
            if (elements[i].size != m) {
                throw IllegalArgumentException("Rows length must be the same")
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Matrix
        if (n != other.n || m != other.m) return false
        for (i in 0 until n) {
            if (!elements[i].contentEquals(other.elements[i])) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = n
        result = 31 * result + elements.hashCode()
        result = 31 * result + m
        return result
    }

    override fun toString(): String {
        return """Matrix(n = $n, m = $m)
            |${elements.joinToString("\n") { it.contentToString() }}
        """.trimMargin()
    }
}
