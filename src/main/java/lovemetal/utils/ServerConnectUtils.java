package lovemetal.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.experimental.UtilityClass;
import lovemetal.Main;
import org.bukkit.entity.Player;

@UtilityClass
public class ServerConnectUtils {

    public void connectPlayer(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(Main.getInstance().getConfig().getString("settings.server-after-captcha"));
        player.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
    }
}
