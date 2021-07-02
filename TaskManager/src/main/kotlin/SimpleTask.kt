import kotlinx.coroutines.delay
import kotlin.random.Random

class SimpleTask(
    private val name: String,
    private val dependencies: MutableCollection<Task> = ArrayList()
) : Task {
    private var state = Task.TaskStates.NOT_VISITED

    override suspend fun execute() {
        println("[${toString()}]: Task started.")
        val time = Random.nextInt(5000)
        delay(time.toLong())
        println("[${toString()}]: All done after $time ms sleep.")
    }

    override fun dependencies(): Collection<Task> {
        return dependencies
    }

    override fun addDependencies(dependencies: Collection<Task>) {
        this.dependencies.addAll(dependencies)
    }

    override fun getName(): String {
        return name
    }

    override fun getState(): Task.TaskStates {
        return this.state
    }

    override fun setState(state: Task.TaskStates) {
        this.state = state
    }

    override fun toString(): String {
        return name
    }
}
