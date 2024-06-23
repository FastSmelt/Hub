package gg.pots.queue.command;

import gg.pots.queue.command.queue.QueueCommands;
import io.github.nosequel.command.bukkit.BukkitCommandHandler;

public class CommandHandler {

    public CommandHandler() {
        final BukkitCommandHandler commandHandler = new BukkitCommandHandler("queue");

        commandHandler.registerCommand(new QueueCommands());
    }
}
