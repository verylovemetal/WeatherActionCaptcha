package lovemetal.scoreboard;

import lombok.Getter;
import lovemetal.Main;
import lovemetal.utils.StringBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Getter
public class TabScoreboard {

    @Getter
    private static final TabScoreboard instance = new TabScoreboard();

    public void setTabScoreboard(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) return;

        List<String> headerList = Main.getInstance().getConfig().getStringList("tab-scoreboard.header");
        List<String> footerList = Main.getInstance().getConfig().getStringList("tab-scoreboard.footer");

        Component header = new StringBuilder(String.join("\n", headerList)).getAsComponent();
        Component footer = new StringBuilder(String.join("\n", footerList)).getAsComponent();

        player.sendPlayerListHeaderAndFooter(header, footer);
    }
}
