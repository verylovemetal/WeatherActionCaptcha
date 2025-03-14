package lovemetal.listener.task;

import lovemetal.Main;
import lovemetal.data.TaskData;
import lovemetal.task.TaskType;
import lovemetal.tracker.PlayerTaskTracker;
import lovemetal.utils.TaskUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class BreakBlockListener implements Listener {

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        TaskData taskData = PlayerTaskTracker.getInstance().getTask(playerUUID);
        if (taskData.getTaskType() != TaskType.BREAK_BLOCK) return;

        Material breakBlockType = event.getBlock().getType();
        Material needBlockType = Material.valueOf(Main.getInstance().getConfig()
                .getString("tasks." + taskData.getTaskPath() + ".block-type"));

        if (!breakBlockType.equals(needBlockType)) {
            return;
        }

        TaskUtils.updateOrComplete(taskData, playerUUID, 1);
    }
}
