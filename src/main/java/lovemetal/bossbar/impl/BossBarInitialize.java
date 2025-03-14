package lovemetal.bossbar.impl;

import lombok.Getter;
import lovemetal.Main;
import lovemetal.bossbar.BossBarTracker;
import lovemetal.bossbar.IBossBar;
import lovemetal.data.TaskData;
import lovemetal.tracker.PlayerTaskTracker;
import lovemetal.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BossBarInitialize implements IBossBar {

    @Getter
    private static final BossBarInitialize instance = new BossBarInitialize();

    @Override
    public void action(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        TaskData taskData = PlayerTaskTracker.getInstance().getTask(playerUUID);
        String title = Main.getInstance().getConfig()
                .getString("boss-bar-settings.title")
                .replace("%task%", Main.getInstance().getConfig().getString("tasks." + taskData.getTaskPath() + ".task-name"))
                .replaceAll("%amount%",  taskData.getNumberOfCompletions() + "")
                .replaceAll("%amount-to-complete%", taskData.getCompleteAmount() + "");
        BarColor color = BarColor.valueOf(Main.getInstance().getConfig().getString("tasks." + taskData.getTaskPath() + ".boss-bar-color"));

        BossBar bossBar = Bukkit.createBossBar(ChatUtils.format(title), color, BarStyle.SOLID);
        bossBar.setProgress(0.0f);
        bossBar.addPlayer(player);

        BossBarTracker.getInstance().addPlayer(playerUUID, bossBar);
    }
}
