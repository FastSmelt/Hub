package gg.pots.lobby.scoreboard;

import gg.pots.data.DataAPI;
import gg.pots.lobby.LobbySpigotPlugin;
import gg.pots.lobby.rank.RankHandler;
import gg.pots.lobby.util.CC;
import io.github.nosequel.scoreboard.element.ScoreboardElement;
import io.github.nosequel.scoreboard.element.ScoreboardElementHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class LobbyBoard implements ScoreboardElementHandler {

    @Override
    public ScoreboardElement getElement(Player player) {
        final ScoreboardElement element = new ScoreboardElement();
        final DataAPI dataAPI = DataAPI.getInstance();

        element.setTitle(CC.translate(LobbySpigotPlugin.instance.getScoreboardFile().getString("scoreboard.title")));

        try {
            for (final String line : LobbySpigotPlugin.instance.getScoreboardFile().getStringList("scoreboard.lines")) {
                element.add(this.replace(line, new HashMap<String, String>() {
                    {
                        this.put("%online_players%", String.valueOf(dataAPI.getOnlinePlayers()));
                        this.put("%max_players%", String.valueOf(Bukkit.getMaxPlayers()));
                        this.put("%rank%", RankHandler.getInstance().getAdapter().getDisplayName(player.getUniqueId()));
                    }
                }));
            }
        }
        catch (Exception ignored) {
            element.add(CC.translate("&cAn error occurred while"));
            element.add(CC.translate("&cloading your board..."));
        }
        return element;
    }

    private String replace(String string, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            string = string.replace(entry.getKey(), entry.getValue());
        }

        return CC.translate(string);
    }
}
