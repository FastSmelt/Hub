package gg.pots.queue.player;

import gg.pots.queue.player.data.QueueData;
import gg.pots.queue.player.data.QueueRank;
import lombok.Data;

import java.util.UUID;

@Data
public class QueuePlayer {

    public UUID uniqueId;

    public QueueRank queueRank;
    public QueueData queueData;

    /**
     * Constructor to create a new {@link QueuePlayer} object.
     *
     * @param uniqueId the unique identifier.
     */

    public QueuePlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public boolean isQueueing() {
        return this.queueData != null;
    }
}
