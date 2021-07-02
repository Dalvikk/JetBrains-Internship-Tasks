import Task.TaskStates
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SimpleExecutor : TaskExecutor {
    override fun execute(tasks: Collection<Task>) {
        val map = HashMap<Task, Job>()
        tasks.forEach {
            if (it.getState() == TaskStates.NOT_VISITED)
                dfs(it, map)
        }
        runBlocking {
            for (job in map.values) {
                job.join()
            }
        }
    }

    private fun dfs(task: Task, map: MutableMap<Task, Job>) {
        task.setState(TaskStates.WALKING_DEPENDENCIES_TREE)
        for (dependency in task.dependencies()) {
            when (dependency.getState()) {
                TaskStates.WALKING_DEPENDENCIES_TREE -> {
                    throw IllegalArgumentException("""Illegal dependencies. "$task" required previously completed "$dependency" task, which required completed "$task".""")
                }
                TaskStates.NOT_VISITED -> {
                    dfs(dependency, map)
                }
                TaskStates.DEPENDENCIES_WAITING, TaskStates.RUNNING, TaskStates.COMPLETED -> {
                }
            }
        }
        task.setState(TaskStates.DEPENDENCIES_WAITING)
        map[task] = GlobalScope.launch {
            for (dependency in task.dependencies()) {
                map[dependency]!!.join()
            }
            task.setState(TaskStates.RUNNING)
            task.execute()
            task.setState(TaskStates.COMPLETED)
        }
    }
}

