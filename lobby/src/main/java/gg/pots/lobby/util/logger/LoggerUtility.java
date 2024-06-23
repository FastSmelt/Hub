package gg.pots.lobby.util.logger;

import gg.pots.lobby.util.CC;
import org.bukkit.Bukkit;

public class LoggerUtility {

    public static void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate(message));
    }
}
