package lovemetal.utils;

import lombok.experimental.UtilityClass;
import lovemetal.Main;
import lovemetal.bossbar.impl.BossBarUpdate;
import lovemetal.data.TaskData;
import lovemetal.task.TaskType;
import lovemetal.task.impl.PlayerCaptchaComplete;
import lovemetal.tracker.PlayerTaskTracker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class TaskUtils {

    private static final FileConfiguration CONFIG = Main.getInstance().getConfig();

    public TaskData createRandomTaskData(UUID playerUUID) {
        Set<String> taskKeys = CONFIG.getConfigurationSection("tasks")
                .getKeys(false);

        List<String> taskList = new ArrayList<>(taskKeys);
        String randomTypeName = taskList.get(ThreadLocalRandom.current().nextInt(taskList.size()));
        String randomType = CONFIG.getString("tasks." + randomTypeName + ".type");

        TaskType[] taskTypes = TaskType.values();
        TaskType taskType = Arrays.stream(taskTypes)
                .filter(foundTask -> randomType.equals(foundTask.name()))
                .findFirst()
                .orElse(null);

        int amountToComplete = CONFIG.getInt("tasks." + randomTypeName + ".amount-to-complete");
        return new TaskData(playerUUID, taskType, randomTypeName, 0, amountToComplete);
    }

    public void updateOrComplete(TaskData taskData, UUID playerUUID, int numberOfCompletions) {
        int finalCompletions = taskData.getNumberOfCompletions() + numberOfCompletions;
        TaskData updatedTaskData = new TaskData(playerUUID,
                taskData.getTaskType(),
                taskData.getTaskPath(),
                finalCompletions,
                taskData.getCompleteAmount());

        PlayerTaskTracker.getInstance().addPlayer(playerUUID, updatedTaskData);
        BossBarUpdate.getInstance().action(playerUUID);

        if (finalCompletions >= taskData.getCompleteAmount()) {
            PlayerCaptchaComplete.getInstance().action(playerUUID);
        }
    }
}
