package lovemetal.bossbar;

import lombok.Getter;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class BossBarTracker {

    @Getter
    private static final BossBarTracker instance = new BossBarTracker();

    private final Map<UUID, BossBar> trackedBossBars = new HashMap<>();

    public void addPlayer(UUID playerUUID, BossBar bossBar) {
        trackedBossBars.put(playerUUID, bossBar);
    }

    public BossBar getBossBar(UUID playerUUID) {
        return trackedBossBars.get(playerUUID);
    }

    public void removePlayer(UUID playerUUID) {
        trackedBossBars.remove(playerUUID);
    }
}
