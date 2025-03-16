package lovemetal.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringBuilder {

    private final String formatText;

    public StringBuilder(String from) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(from);
        while (matcher.find()) {
            String hexCode = from.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace("#", "x");
            char[] ch = replaceSharp.toCharArray();
            java.lang.StringBuilder builder = new java.lang.StringBuilder();
            for (char c : ch)
                builder.append("&").append(c);
            from = from.replace(hexCode, builder.toString());
            matcher = pattern.matcher(from);
        }

        formatText = ChatColor.translateAlternateColorCodes('&', from);
    }

    public String getAsString() {
        return formatText;
    }

    public Component getAsComponent() {
        return Component.text(getAsString());
    }
}
