package gg.pots.data.thread;

import com.google.gson.JsonObject;
import gg.pots.data.DataSpigotPlugin;
import gg.pots.data.redis.JedisHandler;
import gg.pots.data.redis.JedisPublisher;
import gg.pots.data.server.Server;
import gg.pots.data.server.ServerHandler;
import gg.pots.data.server.ServerStatus;
import org.bukkit.Bukkit;

public class ServerUpdateThread extends Thread {

    private final ServerHandler serverHandler;
    private final JedisHandler jedisManager;

    private final String serverName;
    private final long updateInterval;

    public ServerUpdateThread(ServerHandler serverHandler, JedisHandler jedisManager) {
        this.setName("Data-Bukkit-ServerUpdate");

        this.serverHandler = serverHandler;
        this.jedisManager = jedisManager;

        this.serverName = DataSpigotPlugin.instance.settingsFile.getString("SERVER.NAME");
        this.updateInterval = DataSpigotPlugin.instance.settingsFile.getLong("SERVER.UPDATE-INTERVAL");
    }

    @Override
    public void run() {
        while(true) {
            final JsonObject object = new JsonObject();
            final Server server = this.serverHandler.getServerByName(this.serverName.toLowerCase());

            if (server == null) {
                Server serverToUpdate = new Server(this.serverName);

                object.addProperty("serverName", serverToUpdate.getServerName());
                object.addProperty("onlinePlayers", serverToUpdate.getOnlinePlayers());
                object.addProperty("maxPlayers", serverToUpdate.getMaxPlayers());
                object.addProperty("motd", serverToUpdate.getMotd());
                object.addProperty("serverStatus", ServerStatus.LOADING.toString());
                object.addProperty("baseServerVersion", serverToUpdate.getBaseServerVersion());
            } else {
                object.addProperty("serverName", server.getServerName());
                object.addProperty("onlinePlayers", Bukkit.getOnlinePlayers().size());
                object.addProperty("maxPlayers", Bukkit.getMaxPlayers());
                object.addProperty("motd", Bukkit.getMotd());
                object.addProperty("serverStatus", Bukkit.hasWhitelist() ? ServerStatus.WHITELISTED.toString() : ServerStatus.ONLINE.toString());
                object.addProperty("baseServerVersion", server.getBaseServerVersion());
            }

            new JedisPublisher(this.jedisManager).publishData("ServerUpdate" + "///" + object);

            try {
                Thread.sleep(this.updateInterval);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
