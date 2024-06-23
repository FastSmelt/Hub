package gg.pots.lobby.command;

import gg.pots.lobby.LobbySpigotPlugin;
import gg.pots.lobby.command.build.BuildCommand;
import gg.pots.lobby.command.setspawn.SetSpawnCommand;
import gg.pots.lobby.command.spawn.SpawnCommand;

public class CommandHandler {

    public CommandHandler() {
        LobbySpigotPlugin.instance.getCommand("spawn").setExecutor(new SpawnCommand());
        LobbySpigotPlugin.instance.getCommand("setspawn").setExecutor(new SetSpawnCommand());
        LobbySpigotPlugin.instance.getCommand("build").setExecutor(new BuildCommand());
    }
}
