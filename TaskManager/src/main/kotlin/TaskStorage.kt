data class TaskStorage(val map: LinkedHashMap<String, Task> = LinkedHashMap()) : MutableMap<String, Task> by map {
    fun getTask(key: String): Task {
        val res = map[key]
        require(res != null) { """Task with "$key" name doesn't exist.""" }
        return res
    }

    fun putTask(task: Task) {
        require(!contains(task.getName())) { """Task with "${task.getName()}" name already exists.""" }
        map[task.getName()] = task
    }
}
