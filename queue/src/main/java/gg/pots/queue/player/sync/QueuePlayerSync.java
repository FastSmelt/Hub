package gg.pots.queue.player.sync;

import gg.pots.permissions.api.utility.CC;
import gg.pots.queue.QueueSpigotPlugin;
import gg.pots.queue.player.QueuePlayer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class QueuePlayerSync extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final QueuePlayer queuePlayer = QueueSpigotPlugin.instance.playerHandler.findPlayer(player);

            if (queuePlayer == null) {
                player.kickPlayer(CC.translate("&cYou have been kicked from the queue due to an error. \nPlease rejoin the queue."));
            }

            queuePlayer.setQueueRank(QueueSpigotPlugin.instance.queueHandler.findQueuedPlayer(player));
        });
    }
}
