package gg.pots.queue.bungee;

import gg.pots.queue.QueueSpigotPlugin;
import gg.pots.queue.player.data.QueueRank;
import gg.pots.queue.player.sync.QueuePlayerSync;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QueueHandler {

    private final List<QueueRank> queueRanks = new ArrayList<>();

    public QueueHandler() {
        QueueSpigotPlugin.instance.ranksFile.getConfiguration().getConfigurationSection("ranks")
                .getKeys(false)
                .forEach(key -> {
                    final String permission = QueueSpigotPlugin.instance.ranksFile.getConfiguration()
                            .getString("ranks." + key + ".permission");

                    final int priority = QueueSpigotPlugin.instance.ranksFile.getConfiguration()
                            .getInt("ranks." + key + ".priority");

                    final boolean bypass = QueueSpigotPlugin.instance.ranksFile.getConfiguration()
                            .getBoolean("ranks." + key + ".bypass");

                    final QueueRank queueRank = new QueueRank(
                            permission,
                            priority,
                            bypass
                    );

                    this.queueRanks.add(queueRank);
                });

        new QueuePlayerSync().runTaskTimerAsynchronously(QueueSpigotPlugin.instance, 10L, 10L);
    }

    /**
     * Finds the rank with the lowest priority.
     * @return the rank with the lowest priority.
     */

    public QueueRank findByLowestPriority() {
        return this.queueRanks.stream()
                .min(Comparator.comparingInt(QueueRank::getPriority))
                .orElse(null);
    }

    /**
     * Find a {@link QueueRank} by a {@link Player}.
     *
     * @param player the player.
     * @return the queue rank.
     */

    public QueueRank findQueuedPlayer(Player player) {
        final List<QueueRank> filteredQueueRanks = this.queueRanks.stream()
                .filter(queueRank -> player.hasPermission(queueRank.getPermission()))
                .sorted(Comparator.comparingInt(QueueRank::getPriority))
                .collect(Collectors.toList());

        if (filteredQueueRanks.isEmpty()) {
            return this.findByLowestPriority();
        }

        return filteredQueueRanks.get(0);
    }
}
