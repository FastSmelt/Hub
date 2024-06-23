package gg.pots.queue.server;

import gg.pots.queue.QueueConstants;
import gg.pots.queue.QueueSpigotPlugin;
import gg.pots.queue.player.QueuePlayer;
import gg.pots.queue.player.message.QueueMessage;
import gg.pots.queue.player.task.QueueMessageTask;
import gg.pots.queue.player.task.QueueTask;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Server {

    private final List<UUID> queuedPlayers = new ArrayList<>();

    public String name;
    public int online;

    public boolean paused = false;

    /**
     * Constructor to create a new {@link Server} object.
     *
     * @param name the name.
     */

    public Server(String name) {
        this.name = name;
        this.online = 0;

        new QueueTask(this).runTaskTimer(QueueSpigotPlugin.getPlugin(QueueSpigotPlugin.class),
                QueueMessage.QUEUE_DELAY,
                QueueMessage.QUEUE_DELAY
        );

        new QueueMessageTask(this).runTaskTimer(QueueSpigotPlugin.getPlugin(QueueSpigotPlugin.class),
                QueueMessage.QUEUE_REMINDER_DELAY,
                QueueMessage.QUEUE_REMINDER_DELAY
        );
    }

    /**
     * Add a {@link Player} to the queue.
     *
     * @param player the player.
     */

    public void addPlayerToQueue(Player player) {
        final QueuePlayer queuePlayer = QueueSpigotPlugin.instance.playerHandler.findPlayer(player);

        if (queuePlayer != null && this.queuedPlayers.contains(player.getUniqueId())) {
            return;
        }

        this.queuedPlayers.add(this.findQueuePosition(queuePlayer), player.getUniqueId());
    }

    /**
     * Remove a {@link Player} from the queue.
     *
     * @param player the player.
     */

    public void removePlayerFromQueue(Player player) {
        final QueuePlayer queuePlayer = QueueSpigotPlugin.instance.playerHandler.findPlayer(player);

        if (queuePlayer != null && !this.queuedPlayers.contains(player.getUniqueId())) {
            return;
        }

        this.queuedPlayers.remove(player.getUniqueId());
    }

    /**
     * Move a {@link Player} from the queue to the server.
     */

    public void movePlayer() {
        final UUID uniqueId = this.queuedPlayers.get(0);
        final QueuePlayer queuePlayer = QueueSpigotPlugin.instance.playerHandler.findOrCreatePlayer(uniqueId);

        final Player player = Bukkit.getPlayer(uniqueId);
        player.sendMessage(QueueConstants.SENT_QUEUE.get()
                .replace("%server%", this.name));

        this.queuedPlayers.remove(uniqueId);
        queuePlayer.setQueueData(null);
    }

    /**
     * Find the position of a {@link Player} in the queue.
     *
     * @param queuePlayer the player.
     * @return the position.
     */

    public int findQueuePosition(QueuePlayer queuePlayer) {
        if (this.queuedPlayers.isEmpty()) {
            return 0;
        }

        if (queuePlayer.getQueueRank() == null) {
            return this.queuedPlayers.size();
        }

        for (int i = 0; i < this.queuedPlayers.size(); ++i) {
            final UUID queueIdentifier = this.queuedPlayers.get(i);
            final QueuePlayer targetPlayer = QueueSpigotPlugin.instance.playerHandler.findOrCreatePlayer(queueIdentifier);

            final int targetPriority = targetPlayer.getQueueRank() != null ? targetPlayer.getQueueRank().getPriority() : Integer.MAX_VALUE;
            final int playerPriority = queuePlayer.getQueueRank().getPriority();
            if (targetPriority < playerPriority) {
                return i;
            }
        }

        return this.queuedPlayers.size();
    }
}
