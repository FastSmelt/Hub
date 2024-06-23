package gg.pots.queue.player.task;

import gg.pots.queue.QueueConstants;
import gg.pots.queue.server.Server;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class QueueMessageTask extends BukkitRunnable {

    private final Server server;

    @Override
    public void run() {
        final List<UUID> queuedPlayers = server.getQueuedPlayers();

        if (queuedPlayers == null || queuedPlayers.isEmpty()) {
            this.cancel();
        }

        int position = 1;

        for (UUID playerId : queuedPlayers) {
            final Player player = Bukkit.getPlayer(playerId);

            if (player != null) {
                player.sendMessage(QueueConstants.REMINDER_QUEUE.get()
                        .replace("%position%", String.valueOf(position))
                        .replace("%waiting%", String.valueOf(server.getQueuedPlayers().size())));

                position++;
            }
        }
    }
}
