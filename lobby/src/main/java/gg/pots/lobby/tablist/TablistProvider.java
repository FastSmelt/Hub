package gg.pots.lobby.tablist;

import gg.pots.data.DataAPI;
import gg.pots.data.server.Server;
import gg.pots.data.server.ServerHandler;
import gg.pots.data.server.ServerStatus;
import gg.pots.lobby.LobbySpigotPlugin;
import gg.pots.lobby.rank.RankHandler;
import gg.pots.lobby.util.CC;
import io.github.nosequel.tab.shared.entry.TabElement;
import io.github.nosequel.tab.shared.entry.TabElementHandler;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class TablistProvider implements TabElementHandler {

    @Override
    public TabElement getElement(Player player) {
        final TabElement element = new TabElement();

        final AtomicInteger x = new AtomicInteger();
        final AtomicInteger y = new AtomicInteger();

        LobbySpigotPlugin.instance.tablistFile.getConfiguration().getConfigurationSection("tablist").getKeys(false).forEach(string -> {
            if (string.contains("left") || string.contains("right") || string.contains("middle")) {
                LobbySpigotPlugin.instance.tablistFile.getConfiguration().getStringList("tablist." + string)
                        .forEach(str -> element.add(x.get(), y.getAndIncrement(), this.translate(player, str)));

                x.getAndIncrement();
                y.set(0);
            }
        });

        return element;
    }

    /**
     * Translate a {@link String}
     *
     * @param player the player.
     * @param string the string.
     * @return the translated string.
     */

    private String translate(Player player, String string) {
        final ServerHandler handler = DataAPI.getInstance().getServerHandler();
        boolean shouldAttemptReplace = true;

        // Grabs the server's status, online players, and max players
        for (Server server : handler.getServerMap().values()) {
            if (string.contains(server.getServerName().toLowerCase())) {
                shouldAttemptReplace = false;

                string = string.replace("%" + server.getServerName().toLowerCase() + "_status%", server.getServerStatus().getFormat())
                        .replace("%" + server.getServerName().toLowerCase() + "_online%", String.valueOf(server.getOnlinePlayers()))
                        .replace("%" + server.getServerName().toLowerCase() + "_max%", String.valueOf(server.getMaxPlayers()));
            }
        }

        // Attempt to replace the server's status, online players, and max players
        if (shouldAttemptReplace) {
            for (String section : string.split(" ")) {
                if (section.endsWith("_status%")) {
                    string = string.replace(section, ServerStatus.OFFLINE.getFormat());
                } else if (section.endsWith("_online%")) {
                    string = string.replace(section, "0");
                } else if (section.endsWith("_max%")) {
                    string = string.replace(section, "0");
                }
            }
        }

        // Grabs all online players on the network
        string = string.replace("%online_players%", String.valueOf(DataAPI.getInstance().getOnlinePlayers()));

        // Grabs the player's rank
        if (player != null && RankHandler.getInstance().getAdapter() != null) {
            string = string.replace("%rank%", RankHandler.getInstance().getAdapter().getDisplayName(player.getUniqueId()));
        }

        return CC.translate(string);
    }
}
