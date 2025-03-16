package lovemetal.bossbar.impl;

import lombok.Getter;
import lovemetal.Main;
import lovemetal.bossbar.BossBarTracker;
import lovemetal.bossbar.IBossBar;
import lovemetal.data.TaskData;
import lovemetal.tracker.PlayerTaskTracker;
import lovemetal.utils.StringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class BossBarUpdate implements IBossBar {

    @Getter
    private static final BossBarUpdate instance = new BossBarUpdate();

    @Override
    public void action(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        BossBar bossBar = BossBarTracker.getInstance().getBossBar(playerUUID);
        if (bossBar == null) return;

        TaskData taskData = PlayerTaskTracker.getInstance().getTask(playerUUID);
        int completeAmount = taskData.getCompleteAmount();
        int numberOfCompletions = Math.min(taskData.getNumberOfCompletions(), completeAmount);

        String title = Main.getInstance().getConfig()
                .getString("boss-bar-settings.title")
                .replaceAll("%task%", Main.getInstance().getConfig().getString("tasks." + taskData.getTaskPath() + ".task-name"))
                .replaceAll("%amount%",  numberOfCompletions + "")
                .replaceAll("%amount-to-complete%", completeAmount + "");

        double progress = (double) numberOfCompletions / completeAmount;
        progress = Math.min(progress, 1.0);

        bossBar.setTitle(new StringBuilder(title).getAsString());
        bossBar.setProgress(progress);
    }
}
