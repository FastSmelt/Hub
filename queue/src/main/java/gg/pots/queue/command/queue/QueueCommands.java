package gg.pots.queue.command.queue;

import gg.pots.permissions.api.utility.CC;
import gg.pots.queue.QueueConstants;
import gg.pots.queue.QueueSpigotPlugin;
import gg.pots.queue.player.QueuePlayer;
import gg.pots.queue.player.data.QueueData;
import gg.pots.queue.server.Server;
import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.annotation.Help;
import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;
import io.github.nosequel.command.exception.ConditionFailedException;
import org.bukkit.Bukkit;

public class QueueCommands {

    @Help
    @Command(label = "queue", permission = "queue.admin")
    public void help(BukkitCommandExecutor executor) {}

    @Command(label = "joinqueue", description = "Join a queue")
    @Subcommand(label = "join", parentLabel = "queue", description = "Join a queue")
    public void joinQueue(BukkitCommandExecutor executor, String name) throws ConditionFailedException {
        final QueuePlayer trainPlayer = QueueSpigotPlugin.instance.playerHandler.findPlayer(executor.getPlayer());

        if (trainPlayer == null) {
            throw new ConditionFailedException(CC.translate("&cSomething went wrong, please try again later."));
        }

        if (trainPlayer.isQueueing()) {
            executor.sendMessage(QueueConstants.ALREADY_IN_QUEUE.get()
                    .replace("%server%", trainPlayer.getQueueData().getServer().getName()));
            return;
        }

        final Server server = QueueSpigotPlugin.instance.serverHandler.findServer(name);

        if (server == null) {
            executor.sendMessage("&cWe could not find that server.");
            return;
        }

        if (trainPlayer.getQueueRank().isBypass()) {
            executor.sendMessage(QueueConstants.SENT_QUEUE.get()
                    .replace("%server%", server.getName()));
            QueueSpigotPlugin.instance.bungeeHandler.sendPlayerToServer(executor.getPlayer(), server);
            return;
        }

        final QueueData queueData = new QueueData(server, trainPlayer.getQueueRank());
        trainPlayer.setQueueData(queueData);

        server.addPlayerToQueue(executor.getPlayer());

        executor.sendMessage(QueueConstants.JOINED_QUEUE.get()
                .replace("%position%", String.valueOf(server.getQueuedPlayers().indexOf(executor.getPlayer().getUniqueId()) + 1))
                .replace("%server%", server.getName())
                .replace("%waiting%", String.valueOf(server.getQueuedPlayers().size())));
    }

    @Command(label = "leavequeue", description = "Leave a queue")
    @Subcommand(label = "leave", parentLabel = "queue", description = "Leave a queue")
    public void leaveQueue(BukkitCommandExecutor executor, String name) {
        final QueuePlayer trainPlayer = QueueSpigotPlugin.instance.playerHandler.findPlayer(executor.getPlayer());

        if (!trainPlayer.isQueueing()) {
            executor.sendMessage(QueueConstants.NOT_IN_QUEUE.get());
            return;
        }

        final Server server = trainPlayer.getQueueData().getServer();

        server.removePlayerFromQueue(executor.getPlayer());
        trainPlayer.setQueueData(null);

        executor.sendMessage(QueueConstants.LEFT_QUEUE.get()
                .replace("%server%", server.getName()));
    }

    @Command(label = "pause", permission = "queue.admin", description = "Pause a queue")
    @Subcommand(label = "pause", parentLabel = "queue", description = "Pause a queue")
    public void pauseQueue(BukkitCommandExecutor executor, String name) {
        final Server server = QueueSpigotPlugin.instance.serverHandler.findServer(name);

        if (server == null) {
            executor.sendMessage("&cThat server does not exist.");
            return;
        }

        if (server.isPaused()) {
            server.setPaused(false);
            Bukkit.broadcastMessage(QueueConstants.RESUMED_QUEUE.get()
                    .replace("%server%", server.getName()));
        } else {
            server.setPaused(true);
            Bukkit.broadcastMessage(QueueConstants.PAUSED_QUEUE.get()
                    .replace("%server%", server.getName()));
        }
    }
}
