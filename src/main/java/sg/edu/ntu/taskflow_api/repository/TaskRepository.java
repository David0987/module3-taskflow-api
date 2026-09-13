package sg.edu.ntu.taskflow_api.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import sg.edu.ntu.taskflow_api.model.Task;

/**
 * Stores Task objects in memory.
 *
 * @Repository tells Spring to create ONE instance of this class at startup
 * and manage it. Because there is only one instance, the map below is shared
 * by every HTTP request - that is what makes it work as storage.
 *
 * There is no database here. Everything disappears when the app restarts.
 */
@Repository
public class TaskRepository {

    /**
     * The storage. Key = task id, value = the task itself.
     * Created right here at declaration, so it is NEVER null.
     * 'final' means the map object can never be swapped for another one.
     */
    private final Map<Long, Task> tasks = new HashMap<>();

    /** Our id generator. The next task saved gets id 1, then 2, then 3... */
    private long nextId = 1;

    /**
     * Returns all tasks as a List.
     * We wrap it in a new ArrayList so the caller gets a COPY.
     * If we returned tasks.values() directly, a caller could clear it
     * and wipe our storage.
     */
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * Finds one task by id. No loop needed - the map jumps straight to it.
     * Optional.ofNullable gives an empty Optional when the id is not found.
     */
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    /**
     * Saves a NEW task. Always assigns a fresh id, so this is create-only.
     * Do not use it to edit an existing task - use update() for that.
     */
    public Task save(Task task) {
        task.setId(nextId);
        nextId++;
        tasks.put(task.getId(), task);
        return task;
    }

    /**
     * Updates an existing task. Copies the new values onto the stored task,
     * keeping the original id. Returns empty if the id does not exist.
     */
    public Optional<Task> update(Long id, Task updated) {
        Task existing = tasks.get(id);
        if (existing == null) {
            return Optional.empty();
        }
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.isCompleted());
        return Optional.of(existing);
    }

    /**
     * Deletes a task by id.
     * Map.remove() returns the removed value, or null if nothing was there,
     * so this tells the caller whether a delete actually happened.
     */
    public boolean deleteById(Long id) {
        return tasks.remove(id) != null;
    }
}
