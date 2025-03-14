package lovemetal.listener.task;

import lovemetal.data.TaskData;
import lovemetal.task.TaskType;
import lovemetal.tracker.PlayerTaskTracker;
import lovemetal.utils.TaskUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.UUID;

public class PlaceBlockListener implements Listener {

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        TaskData taskData = PlayerTaskTracker.getInstance().getTask(playerUUID);
        if (taskData.getTaskType() != TaskType.PLACE_BLOCK) return;

        TaskUtils.updateOrComplete(taskData, playerUUID, 1);
    }
}