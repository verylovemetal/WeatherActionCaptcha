package lovemetal.task.impl;

import lombok.Getter;
import lovemetal.Main;
import lovemetal.bossbar.BossBarTracker;
import lovemetal.runnable.PlayerTransferTask;
import lovemetal.task.ITaskAction;
import lovemetal.tracker.PlayerTaskTracker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerCaptchaDeactivate implements ITaskAction {

    @Getter
    private static final PlayerCaptchaDeactivate instance = new PlayerCaptchaDeactivate();

    @Override
    public void action(UUID playerUUID) {
        PlayerTaskTracker.getInstance().removePlayer(playerUUID);
        BossBarTracker.getInstance().removePlayer(playerUUID);
        new PlayerTransferTask(playerUUID).runTaskTimer(Main.getInstance(), 0L, 50L);

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        player.getInventory().clear();
        player.giveExpLevels(player.getLevel() * -1);
    }
}
