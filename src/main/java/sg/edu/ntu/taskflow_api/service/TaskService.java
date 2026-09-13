package sg.edu.ntu.taskflow_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import sg.edu.ntu.taskflow_api.model.Task;
import sg.edu.ntu.taskflow_api.repository.TaskRepository;

/**
 * Holds the business logic for tasks.
 *
 * Sits between the controller (which speaks HTTP) and the repository
 * (which speaks storage). Neither of those should contain rules - this is
 * where rules live.
 *
 * @Service tells Spring to create ONE instance of this class at startup.
 */
@Service
public class TaskService {

    /**
     * The repository we depend on.
     * 'final' means it must be set in the constructor and can never change.
     */
    private final TaskRepository taskRepository;

    /**
     * CONSTRUCTOR INJECTION.
     *
     * We never write "new TaskRepository()" here. At startup Spring sees this
     * constructor needs a TaskRepository, finds the one it created, and passes
     * it in for us.
     *
     * No @Autowired needed: a class with a single constructor gets it
     * automatically (Spring 4.3+).
     */
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /** Returns every task. */
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    /** Finds one task. Empty Optional means "no task with that id". */
    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /** Creates a new task. The repository assigns the id. */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /** Updates an existing task. Empty Optional means the id was not found. */
    public Optional<Task> updateTask(Long id, Task task) {
        return taskRepository.update(id, task);
    }

    /** Deletes a task. Returns true if a task was actually removed. */
    public boolean deleteTask(Long id) {
        return taskRepository.deleteById(id);
    }

    /**
     * Marks a task as complete.
     *
     * This is the real business logic in this class: find it, flip the flag,
     * save it back.
     *
     * IMPORTANT: we call update(), NOT save(). save() always assigns a brand
     * new id, so it would create a duplicate task instead of completing this one.
     */
    public Optional<Task> markTaskAsComplete(Long id) {
        Optional<Task> found = taskRepository.findById(id);

        if (found.isEmpty()) {
            return Optional.empty();
        }

        Task task = found.get();
        task.setCompleted(true);
        return taskRepository.update(id, task);
    }
}
