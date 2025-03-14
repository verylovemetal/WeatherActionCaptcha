package lovemetal.runnable;

import lovemetal.utils.ServerConnectUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerTransferTask extends BukkitRunnable {

    private final UUID playerUUID;

    public PlayerTransferTask(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    @Override
    public void run() {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) {
            cancel();
            return;
        }

        ServerConnectUtils.connectPlayer(player);
    }
}
