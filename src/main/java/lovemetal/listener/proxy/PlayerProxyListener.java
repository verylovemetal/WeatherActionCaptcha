package lovemetal.listener.proxy;

import lovemetal.Main;
import lovemetal.utils.ProxyUtils;
import lovemetal.utils.StringBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.Arrays;

public class PlayerProxyListener implements Listener {

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        FileConfiguration config = Main.getInstance().getConfig();
        if (!config.getBoolean("proxy.enable")) {
            return;
        }

        String loginIP = Arrays.toString(event.getAddress().getAddress()).replace("/", "");
        if (ProxyUtils.getProxyList().contains(loginIP)) {
            event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                   new StringBuilder(config.getString("messages.blocked-ip-join")).getAsComponent());
            return;
        }

        event.allow();
    }
}
