package lovemetal.task.impl;

import lombok.Getter;
import lovemetal.Main;
import lovemetal.session.SessionTracker;
import lovemetal.task.ITaskAction;
import lovemetal.tracker.PlayerTaskTracker;
import lovemetal.utils.ServerConnectUtils;
import lovemetal.utils.StringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerCaptchaComplete implements ITaskAction {

    @Getter
    private static final PlayerCaptchaComplete instance = new PlayerCaptchaComplete();

    @Override
    public void action(UUID playerUUID) {
        PlayerCaptchaDeactivate.getInstance().action(playerUUID);

        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        player.sendMessage(new StringBuilder(Main.getInstance().getConfig().getString("messages.complete-captcha")).getAsString());

        SessionTracker.getInstance().addSession(playerUUID, PlayerTaskTracker.getInstance().getTask(playerUUID));
    }
}