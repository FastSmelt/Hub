package gg.pots.queue.player.data;

import gg.pots.queue.server.Server;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class QueueData {

    public Server server;
    public QueueRank queueRank;
}
