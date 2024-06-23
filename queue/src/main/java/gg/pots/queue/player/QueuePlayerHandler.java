package gg.pots.queue.player;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class QueuePlayerHandler {

    private final Map<UUID, QueuePlayer> queuePlayerMap = new HashMap<>();

    /**
     * Find a player in the map, if they are not found, create a new one.
     *
     * @param uniqueId the unique identifier.
     * @return the player.
     */

    public QueuePlayer findOrCreatePlayer(UUID uniqueId) {
        QueuePlayer queuePlayer = this.queuePlayerMap.get(uniqueId);

        if (queuePlayer == null) {
            queuePlayer = new QueuePlayer(
                    uniqueId
            );

            this.queuePlayerMap.put(uniqueId, queuePlayer);
        }

        return queuePlayer;
    }

    /**
     * Find a {@link QueuePlayer} by a {@link Player} object.
     *
     * @param player the player.
     * @return the player or null.
     */

    public QueuePlayer findPlayer(Player player) {
        return this.queuePlayerMap.get(player.getUniqueId());
    }
}
