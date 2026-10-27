package sg.edu.ntu.taskflow_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import sg.edu.ntu.taskflow_api.model.Task;
import sg.edu.ntu.taskflow_api.service.TaskService;

/**
 * Adds a starting task when the app boots.
 *
 * The repository keeps tasks in memory only, so the list is empty after every
 * restart. This puts one task back each time, so GET /api/tasks always has
 * something to show.
 *
 * @Component tells Spring to create ONE instance of this class at startup.
 * Because it implements CommandLineRunner, Spring calls run() once the app
 * has fully started.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final TaskService taskService;

    /** Constructor injection - Spring passes in the TaskService. */
    public DataSeeder(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) {
        // id is null: the repository assigns it (this first task gets id 1).
        taskService.createTask(new Task(null, "David finishing assignment", "Module 3 TaskFlow assignment", false));
    }
}
