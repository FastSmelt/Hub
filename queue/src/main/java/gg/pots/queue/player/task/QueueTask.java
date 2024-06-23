package gg.pots.queue.player.task;

import gg.pots.queue.server.Server;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
@Getter
public class QueueTask extends BukkitRunnable {

    private final Server server;

    @Override
    public void run() {
        if (this.server.isPaused())
            return;

        if (this.server.getQueuedPlayers().isEmpty())
            return;

        this.server.movePlayer();
    }
}
