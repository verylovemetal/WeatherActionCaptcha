package lovemetal;

import lombok.Getter;
import lovemetal.listener.player.PlayerConnectListener;
import lovemetal.listener.player.PlayerListener;
import lovemetal.listener.task.*;
import lovemetal.listener.world.WorldListener;
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
    }

    public void registerListeners() {
        List.of(
                new PlayerConnectListener(),
                new CraftTaskListener(),
                new BreakBlockListener(),
                new PlaceBlockListener(),
                new EnchantItemListener(),
                new PlayerListener(),
                new WorldListener(),
                new DisenchantListener()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }
}
