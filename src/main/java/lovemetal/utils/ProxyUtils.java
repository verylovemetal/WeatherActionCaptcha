package lovemetal.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lovemetal.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public class ProxyUtils {

    private static final FileConfiguration CONFIG = Main.getInstance().getConfig();
    private static final Set<String> PROXY_LIST = new HashSet<>();

    @SneakyThrows
    public void loadProxyList() {
        if (!CONFIG.getBoolean("proxy.enable")) {
            return;
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            for (String proxyURL : CONFIG.getStringList("proxy.url")) {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(proxyURL).openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        System.err.println("Ошибка соединения с " + proxyURL);
                        continue;
                    }

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                        reader.lines()
                                .map(line -> line.split(":")[0].trim())
                                .forEach(PROXY_LIST::add);
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка загрузки прокси с " + proxyURL + ": " + e.getMessage());
                }
            }
        }, 0L, CONFIG.getLong("proxy.period-of-update") * 20L);
    }

    public Set<String> getProxyList() {
        return PROXY_LIST;
    }
}
