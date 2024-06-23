package gg.pots.lobby.rank;

import gg.pots.lobby.rank.impl.CoreRankTypeAdapter;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class RankHandler {

    private final RankTypeAdapter adapter;

    @Getter
    private static RankHandler instance;

    public RankHandler() {
        instance = this;

        if (Bukkit.getPluginManager().getPlugin("core") != null) {
            this.adapter = new CoreRankTypeAdapter();
        } else {
            this.adapter = uniqueId -> "Default";
        }
    }
}
