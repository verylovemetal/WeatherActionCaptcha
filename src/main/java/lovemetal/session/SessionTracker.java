package lovemetal.session;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lovemetal.Main;
import lovemetal.data.TaskData;

import java.time.Duration;
import java.util.UUID;

@Getter
public class SessionTracker {

    @Getter
    private static final SessionTracker instance = new SessionTracker();

    private final Cache<UUID, TaskData> sessions = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(Main.getInstance().getConfig().getInt("settings.session-time")))
            .build();

    public void addSession(UUID playerUUID, TaskData taskData) {
        sessions.put(playerUUID, taskData);
    }

    public TaskData getSession(UUID playerUUID) {
        return sessions.getIfPresent(playerUUID);
    }
}
