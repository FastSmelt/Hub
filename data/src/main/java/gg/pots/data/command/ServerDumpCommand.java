package gg.pots.data.command;

import gg.pots.data.DataSpigotPlugin;
import gg.pots.data.server.Server;
import gg.pots.data.server.ServerHandler;
import gg.pots.data.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ServerDumpCommand implements CommandExecutor {

    private final ServerHandler serverHandler;

    public ServerDumpCommand(DataSpigotPlugin plugin) {
        this.serverHandler = plugin.getServerHandler();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("data.admin")) {
            sender.sendMessage(CC.translate("&cNo permission."));
            return true;
        }

        this.serverHandler.getServerMap().values().forEach(server -> {
            sender.sendMessage(this.getServerDump(server));
        });

        return true;
    }

    /**
     * Get the server dump
     *
     * @param server the server to get the dump from.
     * @return the server dump.
     */

    private String[] getServerDump(Server server) {
        return new String[] {
                CC.translate("&7&m----------------------------------------"),
                CC.translate("&eServer: &f" + server.getServerName()),
                CC.translate("&ePlayers: &f" + server.getOnlinePlayers()),
                CC.translate("&eMax Players: &f" + server.getMaxPlayers()),
                CC.translate("&eMotd: &f" + server.getMotd()),
                CC.translate("&eStatus: &f" + server.getServerStatus().getFormat()),
                CC.translate("&eBase Version: &f" + server.getBaseServerVersion()),
                CC.translate("&eTPS: &f" + server.getTps()),
                CC.translate("&eLast Update: &f" + (System.currentTimeMillis() - server.getLastUpdate()) + "ms ago"),
                CC.translate("&7&m----------------------------------------")
        };
    }
}
