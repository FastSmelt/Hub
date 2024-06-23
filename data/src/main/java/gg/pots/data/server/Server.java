package gg.pots.data.server;

import com.google.gson.JsonObject;
import gg.pots.data.DataSpigotPlugin;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

@Data
public class Server {

    private final String serverName;
    private ServerStatus serverStatus;

    private int onlinePlayers;
    private int maxPlayers;

    private final String motd;
    private final String baseServerVersion;

    private final String tps;

    private long lastUpdate;

    /**
     * Constructor to create a new {@link Server} object
     *
     * @param serverName the serverName.
     */

    public Server(String serverName) {
        this.serverName = serverName;
        this.onlinePlayers = Bukkit.getOnlinePlayers().size();
        this.maxPlayers = Bukkit.getMaxPlayers();
        this.motd = Bukkit.getMotd();
        this.serverStatus = Bukkit.hasWhitelist() ? ServerStatus.WHITELISTED : ServerStatus.ONLINE;
        this.baseServerVersion = Bukkit.getVersion();
        this.tps = getTicksPerSecondFormatted();
        this.lastUpdate = System.currentTimeMillis();
    }

    /**
     * Constructor to initialise an {@link Server} object from a {@link JsonObject}
     *
     * @param object the object.
     */

    public Server(JsonObject object) {
        this.serverName = object.get("serverName").getAsString();
        this.onlinePlayers = object.get("onlinePlayers").getAsInt();
        this.maxPlayers = object.get("maxPlayers").getAsInt();
        this.motd = object.get("motd").getAsString();
        this.serverStatus = ServerStatus.valueOf(object.get("serverStatus").getAsString());
        this.baseServerVersion = object.get("baseServerVersion").getAsString();
        this.tps = getTicksPerSecondFormatted();
        this.lastUpdate = System.currentTimeMillis();
    }

    /**
     * Get the TPS formatted to 2 decimal places of a {@link Server}
     *
     * @return the TPS formatted to 2 decimal places.
     */

    public static String getTicksPerSecondFormatted() {
        return ChatColor.GREEN + String.format("%.2f", Math.min(DataSpigotPlugin.instance.getTpsRunnable().getTPS(), 20.0));
    }
}
