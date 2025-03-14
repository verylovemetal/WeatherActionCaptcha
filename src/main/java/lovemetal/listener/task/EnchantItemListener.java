package lovemetal.listener.task;

import lovemetal.data.TaskData;
import lovemetal.task.TaskType;
import lovemetal.tracker.PlayerTaskTracker;
import lovemetal.utils.TaskUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.UUID;

public class EnchantItemListener implements Listener {

    @EventHandler
    public void onPlayerEnchant(EnchantItemEvent event) {
        event.setCancelled(true);

        Player player = event.getEnchanter();
        UUID playerUUID = player.getUniqueId();

        TaskData taskData = PlayerTaskTracker.getInstance().getTask(playerUUID);
        if (taskData.getTaskType() != TaskType.ENCHANT_ITEM) return;

        event.setCancelled(false);
        TaskUtils.updateOrComplete(taskData, playerUUID, event.getItem().getAmount());
    }
}
