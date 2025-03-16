package lovemetal.runnable;

import lovemetal.Main;
import lovemetal.utils.StringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CaptchaKickTask extends BukkitRunnable {

    private final UUID playerUUID;

    public CaptchaKickTask(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    @Override
    public void run() {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        player.kick(new StringBuilder(Main.getInstance().getConfig().getString("messages.kick-message")).getAsComponent());
    }
}
