package gg.pots.queue.player.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class QueueRank {

    public String permission;
    public int priority;
    public boolean bypass;
}
