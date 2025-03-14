package lovemetal.listener.player;

import lovemetal.Main;
import lovemetal.task.impl.PlayerCaptchaDeactivate;
import lovemetal.task.impl.PlayerCaptchaInitialize;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerConnectListener implements Listener {

    @EventHandler
    public void onPlayerConnectEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        event.joinMessage(null);
        PlayerCaptchaInitialize.getInstance().action(playerUUID);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        event.quitMessage(null);
        PlayerCaptchaDeactivate.getInstance().action(playerUUID);
    }
}
