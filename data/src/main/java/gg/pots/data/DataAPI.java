package gg.pots.data;

import gg.pots.data.server.Server;
import gg.pots.data.server.ServerHandler;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class DataAPI {

    private static DataAPI apiInstance;

    private final ServerHandler serverHandler;

    private int onlinePlayers;

    public DataAPI() {
        apiInstance = this;

        this.serverHandler = DataSpigotPlugin.instance.getServerHandler();

        new BukkitRunnable() {
            public void run() {
                int online = 0;

                for (Server server : serverHandler.getServerMap().values()) {
                    online = online + server.getOnlinePlayers();
                }

                onlinePlayers = online;
            }
        }.runTaskTimerAsynchronously(DataSpigotPlugin.instance, 0L, 40L);
    }

    public Server findServer(String name) {
        return this.serverHandler.getServerByName(name.toLowerCase());
    }

    public int getOnlinePlayers() {
        return this.onlinePlayers;
    }

    public static DataAPI getInstance() {
        return apiInstance;
    }
}
