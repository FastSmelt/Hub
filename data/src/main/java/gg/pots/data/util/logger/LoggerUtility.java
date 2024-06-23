package gg.pots.data.util.logger;

import gg.pots.data.util.CC;
import org.bukkit.Bukkit;

public class LoggerUtility {

    public static void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate(message));
    }
}
