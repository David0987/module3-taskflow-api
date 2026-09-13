package sg.edu.ntu.taskflow_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A single task in the TaskFlow application.
 *
 * This is a plain POJO (Plain Old Java Object): it only holds data.
 * There are NO Spring annotations here on purpose - the model layer
 * knows nothing about the web, the database, or Spring itself.
 */
@Getter             // Lombok writes getId(), getTitle(), getDescription(), isCompleted()
@Setter             // Lombok writes setId(...), setTitle(...), setDescription(...), setCompleted(...)
@NoArgsConstructor  // Lombok writes: new Task()
@AllArgsConstructor // Lombok writes: new Task(id, title, description, completed)
public class Task {

    /** Unique identifier. Null until the task has been saved. */
    private Long id;

    /** Short name of the task, e.g. "Buy milk". */
    private String title;

    /** Optional longer detail about the task. */
    private String description;

    /** Done or not. A brand new task starts as false. */
    private boolean completed;
}
