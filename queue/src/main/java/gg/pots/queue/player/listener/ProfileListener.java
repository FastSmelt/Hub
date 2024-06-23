package gg.pots.queue.player.listener;

import gg.pots.queue.QueueSpigotPlugin;
import gg.pots.queue.player.QueuePlayer;
import gg.pots.queue.server.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ProfileListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        final UUID uniqueId = event.getUniqueId();

        QueueSpigotPlugin.instance.playerHandler.findOrCreatePlayer(
                uniqueId
        );
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final QueuePlayer queuePlayer = QueueSpigotPlugin.instance.playerHandler.findPlayer(event.getPlayer());

        queuePlayer.setQueueRank(QueueSpigotPlugin.instance.queueHandler.findQueuedPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final QueuePlayer queuePlayer = QueueSpigotPlugin.instance.playerHandler.findPlayer(event.getPlayer());

        if (queuePlayer == null) {
            return;
        }

        if (queuePlayer.isQueueing()) {
            final Server server = queuePlayer.getQueueData().getServer();

            server.removePlayerFromQueue(event.getPlayer());
            queuePlayer.setQueueData(null);
            QueueSpigotPlugin.instance.playerHandler.getQueuePlayerMap().remove(event.getPlayer().getUniqueId());
        }
    }
}
