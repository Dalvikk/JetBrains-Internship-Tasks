import java.util.*
import kotlin.collections.HashMap

fun main() {
    try {
        val storage = TaskStorage()
        Scanner(System.`in`).use { sc ->
            println("Enter tasks count:")
            while (!sc.hasNextInt()) {
                println("${sc.next()} isn't a Int. Please, retry:")
            }
            val n = sc.nextInt()
            println("Enter $n tasks name")
            for (i in 1..n) {
                storage.putTask(SimpleTask(sc.next()))
            }
            sc.nextLine() // line separator
            for (task in storage.values) {
                println("Enter dependencies (or empty line) for ${task.getName()}, separated by whitespaces")
                val line = sc.nextLine().trim()
                if (line.isEmpty()) continue
                val dependencies = line.split("\\s+".toRegex()).map {
                    storage.getTask(it)
                }
                task.addDependencies(dependencies)
            }
        }
        val executor = SimpleExecutor()
        executor.execute(storage.values)
    } catch (e: NoSuchElementException) {
        println("String expected, but no more tokens are available.")
    } catch (e: IllegalStateException) {
        println("Something went wrong. Please, try again.")
    } catch (e: Exception) { // IllegalArgumentException
        println(e.message)
    }
}
