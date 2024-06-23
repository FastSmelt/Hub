package gg.pots.lobby.command.build;

import gg.pots.lobby.LobbySpigotPlugin;
import gg.pots.lobby.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class BuildCommand implements CommandExecutor {

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

        if (player.hasMetadata("buildmode")) {
            player.removeMetadata("buildmode", LobbySpigotPlugin.instance);
            player.sendMessage(CC.translate("&cYou are no longer in build mode."));
        } else {
            player.setMetadata("buildmode", new FixedMetadataValue(LobbySpigotPlugin.instance, true));
            player.sendMessage(CC.translate("&aYou are now in build mode."));
        }

        return true;
    }
}
