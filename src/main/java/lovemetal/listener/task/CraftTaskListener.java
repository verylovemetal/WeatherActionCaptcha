package lovemetal.listener.task;

import lovemetal.Main;
import lovemetal.bossbar.impl.BossBarUpdate;
import lovemetal.data.TaskData;
import lovemetal.task.TaskType;
import lovemetal.tracker.PlayerTaskTracker;
import lovemetal.utils.TaskUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CraftTaskListener implements Listener {

    @EventHandler
    public void onPlayerCraft(CraftItemEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        TaskData taskData = PlayerTaskTracker.getInstance().getTask(playerUUID);
        if (taskData.getTaskType() != TaskType.CRAFT_ITEM) return;

        ItemStack craftItem = event.getRecipe().getResult();
        Material craftItemMaterial = craftItem.getType();

        Material needItemMaterial = Material.valueOf(Main.getInstance().getConfig()
                .getString("tasks." + taskData.getTaskPath() + ".result-item"));

        if (!craftItemMaterial.equals(needItemMaterial)) {
            return;
        }

        event.setCancelled(false);
        TaskUtils.updateOrComplete(taskData, playerUUID, craftItem.getAmount());
    }
}
