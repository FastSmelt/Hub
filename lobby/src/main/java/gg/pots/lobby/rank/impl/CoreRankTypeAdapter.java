package gg.pots.lobby.rank.impl;

import gg.pots.basics.core.CoreAPI;
import gg.pots.lobby.LobbySpigotPlugin;
import gg.pots.lobby.rank.RankTypeAdapter;

import java.util.UUID;

public class CoreRankTypeAdapter implements RankTypeAdapter {

    private final CoreAPI coreAPI = LobbySpigotPlugin.instance.getCoreAPI();

    @Override
    public String getDisplayName(UUID uniqueId) {
        return this.coreAPI.findRank(uniqueId).getDisplayName();
    }
}
