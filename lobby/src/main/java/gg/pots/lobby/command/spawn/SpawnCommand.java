package gg.pots.lobby.command.spawn;

import gg.pots.lobby.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(CC.translate("&cYou must be a player to execute this command."));
            return true;
        }

        final Player player = (Player) sender;

        player.teleport(player.getWorld().getSpawnLocation());
        player.sendMessage(CC.translate("&aYou have been teleported to the spawn."));

        return true;
    }
}
