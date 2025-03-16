package lovemetal;

import lombok.Getter;
import lovemetal.listener.player.PlayerConnectListener;
import lovemetal.listener.player.PlayerListener;
import lovemetal.listener.proxy.PlayerProxyListener;
import lovemetal.listener.task.*;
import lovemetal.listener.world.WorldListener;
import lovemetal.utils.ProxyUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        registerListeners();

        ProxyUtils.loadProxyList();
    }

    public void registerListeners() {
        List.of(
                new PlayerConnectListener(),
                new CraftTaskListener(),
                new BreakBlockListener(),
                new EnchantItemListener(),
                new PlayerListener(),
                new WorldListener(),
                new DisenchantListener(),
                new PlayerProxyListener()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }
}
