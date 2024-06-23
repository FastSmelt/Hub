package gg.pots.lobby.command.setspawn;

import gg.pots.lobby.util.CC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(CC.translate("&cYou must be a player to execute this command."));
            return true;
        }

        final Player player = (Player) sender;

        if (!player.hasPermission("lobby.admin")) {
            player.sendMessage(CC.translate("&cYou do not have permission to execute this command."));
            return true;
        }

        final Location location = player.getLocation();

        location.getWorld().setSpawnLocation(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );

        location.setPitch(location.getPitch());
        location.setYaw(location.getYaw());

        player.sendMessage(CC.translate("&aYou have set the spawn location to your current location."));
        return true;
    }
}
