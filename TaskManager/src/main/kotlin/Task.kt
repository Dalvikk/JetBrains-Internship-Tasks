interface Task {
    enum class TaskStates {
        NOT_VISITED,
        WALKING_DEPENDENCIES_TREE,
        DEPENDENCIES_WAITING,
        RUNNING,
        COMPLETED,
    }

    suspend fun execute()

    fun dependencies(): Collection<Task>

    fun addDependencies(dependencies: Collection<Task>)

    fun getName(): String

    fun getState(): TaskStates

    fun setState(state: TaskStates)
}
