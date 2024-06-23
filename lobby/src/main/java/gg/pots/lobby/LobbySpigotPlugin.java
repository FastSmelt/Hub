package gg.pots.lobby;

import gg.pots.basics.core.CoreAPI;
import gg.pots.basics.core.ServiceHandler;
import gg.pots.lobby.command.CommandHandler;
import gg.pots.lobby.listener.PlayerListener;
import gg.pots.lobby.rank.RankHandler;
import gg.pots.lobby.scoreboard.LobbyBoard;
import gg.pots.lobby.tablist.TablistProvider;
import gg.pots.lobby.tablist.TablistVersion;

import gg.pots.lobby.util.configuration.ConfigFile;
import gg.pots.lobby.util.logger.LoggerUtility;
import io.github.nosequel.menu.MenuHandler;
import io.github.nosequel.scoreboard.ScoreboardHandler;
import io.github.nosequel.tab.shared.TabHandler;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class LobbySpigotPlugin extends JavaPlugin {

    public static LobbySpigotPlugin instance;

    public CoreAPI coreAPI;

    public CommandHandler commandHandler;

    public ConfigFile itemFile;
    public ConfigFile scoreboardFile;
    public ConfigFile tablistFile;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();

        if (this.getServer().getPluginManager().getPlugin("core") == null) {
            this.getServer().getPluginManager().disablePlugin(this);

            throw new RuntimeException("Core plugin not found.");
        } else {
            this.getLogger().info("Core plugin found.");
        }

        this.coreAPI = new CoreAPI(ServiceHandler.getInstance());
        LoggerUtility.sendMessage("&aSuccessfully loaded the CoreAPI.");

        this.commandHandler = new CommandHandler();

        this.itemFile = new ConfigFile(this, "items.yml");
        this.scoreboardFile = new ConfigFile(this, "scoreboard.yml");
        this.tablistFile = new ConfigFile(this, "tablist.yml");

        new RankHandler();

        new MenuHandler(this);
        new ScoreboardHandler(this, new LobbyBoard(), 20L);

        if (this.tablistFile.getConfiguration().getBoolean("tablist.enabled")) {
            new TabHandler(
                    TablistVersion.valueOf(this.tablistFile.getConfiguration().getString("tablist.version")).getAdapter(),
                    new TablistProvider(),
                    this,
                    20L
            );
        }

        this.getServer().getWorlds().forEach(world ->
                world.getEntities().forEach(Entity::remove)
        );

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
