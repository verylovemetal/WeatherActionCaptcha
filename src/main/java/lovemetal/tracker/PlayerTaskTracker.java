package lovemetal.tracker;

import lombok.Getter;
import lovemetal.data.TaskData;
import lovemetal.utils.TaskUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTaskTracker {

    @Getter
    private static final PlayerTaskTracker instance = new PlayerTaskTracker();

    private final Map<UUID, TaskData> tasks = new HashMap<>();

    public void addPlayer(UUID playerUUID, TaskData taskData) {
        tasks.put(playerUUID, taskData);
    }

    public TaskData getTask(UUID playerUUID) {
        return tasks.getOrDefault(playerUUID, TaskUtils.createRandomTaskData(playerUUID));
    }

    public void removePlayer(UUID playerUUID) {
        tasks.remove(playerUUID);
    }
}
