package gg.pots.data.task;

import gg.pots.data.DataSpigotPlugin;
import gg.pots.data.server.Server;
import gg.pots.data.server.ServerHandler;
import gg.pots.data.util.CC;
import gg.pots.data.util.logger.LoggerUtility;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerHeartbeatTask extends BukkitRunnable {

    private final ServerHandler serverHandler;
    private final long updateInterval;

    public ServerHeartbeatTask(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
        this.updateInterval = DataSpigotPlugin.instance.settingsFile.getLong("SERVER.UPDATE-INTERVAL");
    }

    @Override
    public void run() {
        for (Server server : this.serverHandler.getServerMap().values()) {
            if (server.getLastUpdate() + (this.updateInterval * 2) < System.currentTimeMillis()) {
                LoggerUtility.sendMessage(CC.translate("&cServer " + server.getServerName() + " hasn't had a heartbeat for 30s, setting server state as offline"));
                this.serverHandler.getServerMap().remove(server.getServerName().toLowerCase());
            }
        }
    }
}