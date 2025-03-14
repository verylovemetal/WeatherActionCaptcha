package lovemetal.task.impl;

import lombok.Getter;
import lovemetal.Main;
import lovemetal.bossbar.impl.BossBarInitialize;
import lovemetal.data.TaskData;
import lovemetal.runnable.CaptchaKickTask;
import lovemetal.runnable.PlayerTransferTask;
import lovemetal.scoreboard.TabScoreboard;
import lovemetal.session.SessionTracker;
import lovemetal.task.ITaskAction;
import lovemetal.tracker.PlayerTaskTracker;
import lovemetal.utils.ChatUtils;
import lovemetal.utils.ServerConnectUtils;
import lovemetal.utils.TaskUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PlayerCaptchaInitialize implements ITaskAction {

    private static final FileConfiguration CONFIG = Main.getInstance().getConfig();

    @Getter
    private static final PlayerCaptchaInitialize instance = new PlayerCaptchaInitialize();

    @Override
    public void action(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        for (String playerName : CONFIG.getStringList("settings.players-without-captcha")) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                new PlayerTransferTask(playerUUID).runTaskTimer(Main.getInstance(), 0L, 20L);
                return;
            }
        }

        TabScoreboard.getInstance().setTabScoreboard(playerUUID);
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            onlinePlayer.hidePlayer(Main.getInstance(), player);
        });

        player.teleportAsync(new Location(
                Bukkit.getWorld(CONFIG.getString("settings.spawn-point.world")),
                CONFIG.getDouble("settings.spawn-point.x-coord"),
                CONFIG.getDouble("settings.spawn-point.y-coord"),
                CONFIG.getDouble("settings.spawn-point.z-coord")
        ));

        if (SessionTracker.getInstance().getSession(playerUUID) != null) {
            new PlayerTransferTask(playerUUID).runTaskTimer(Main.getInstance(), 0L, 50L);
            return;
        }

        PlayerTaskTracker.getInstance().addPlayer(playerUUID, TaskUtils.createRandomTaskData(playerUUID));
        TaskData taskData = PlayerTaskTracker.getInstance().getTask(playerUUID);

        player.sendMessage(ChatUtils.format(CONFIG.getString("messages.player-connect-message")
                        .replaceAll("%task%", CONFIG.getString("tasks." + taskData.getTaskPath() + ".task-name"))
        ));

        playerGiveItem(player, taskData.getTaskPath());
        BossBarInitialize.getInstance().action(playerUUID);
        new CaptchaKickTask(playerUUID).runTaskLater(Main.getInstance(), CONFIG.getInt("settings.captcha-time") * 20L);
    }

    private void playerGiveItem(Player player, String taskPath) {
        List<String> giveItemList = CONFIG.getStringList("tasks." + taskPath + ".items-to-give");
        if (giveItemList.isEmpty()) return;

        giveItemList.forEach(itemData -> {
            try {
                String[] parts = itemData.split(":");

                String itemToGive = parts[0].toUpperCase();
                int amount = parts.length == 2 ? Integer.parseInt(parts[1]) : 1;

                if (itemToGive.equalsIgnoreCase("EXPERIENCE")) {
                    player.giveExpLevels(amount);
                    return;
                }

                Material material = Material.valueOf(itemToGive);
                ItemStack itemStack = new ItemStack(material, amount);

                Enchantment enchantment = parts.length == 3 ? Enchantment.getByName(parts[2]) : null;

                if (enchantment != null) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.addEnchant(enchantment, 1, true);
                    itemStack.setItemMeta(itemMeta);
                }

                player.getInventory().addItem(itemStack);
            } catch (NumberFormatException exception) {
                Main.getInstance().getLogger().warning("Некорректное количество у предмета: " + itemData);
            }
        });
    }
}
