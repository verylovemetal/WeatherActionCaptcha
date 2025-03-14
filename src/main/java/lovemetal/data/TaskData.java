package lovemetal.data;

import lombok.Data;
import lovemetal.task.TaskType;

import java.util.UUID;

@Data
public class TaskData {
    private final UUID playerUUID;
    private final TaskType taskType;
    private final String taskPath;
    private final int numberOfCompletions;
    private final int completeAmount;
}
