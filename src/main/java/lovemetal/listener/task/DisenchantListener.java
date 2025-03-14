package lovemetal.listener.task;

import lovemetal.data.TaskData;
import lovemetal.task.TaskType;
import lovemetal.tracker.PlayerTaskTracker;
import lovemetal.utils.TaskUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DisenchantListener implements Listener {

    @EventHandler
    public void onDisenchantItem(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        if (event.getInventory().getType() != InventoryType.GRINDSTONE) return;
        ItemStack resultItem = event.getInventory().getItem(2);

        if (event.getSlot() != 2 && event.getClick() != ClickType.LEFT) return;
        if (resultItem == null || resultItem.getEnchantments().isEmpty()) return;

        TaskData taskData = PlayerTaskTracker.getInstance().getTask(playerUUID);
        if (taskData.getTaskType() != TaskType.REMOVE_ENCHANT) return;

        TaskUtils.updateOrComplete(taskData, playerUUID, 1);
    }
}
