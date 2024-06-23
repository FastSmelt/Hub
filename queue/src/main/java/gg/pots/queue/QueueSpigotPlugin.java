package gg.pots.queue;

import gg.pots.permissions.api.configuration.ConfigFile;
import gg.pots.queue.bungee.BungeeHandler;
import gg.pots.queue.bungee.QueueHandler;
import gg.pots.queue.command.CommandHandler;
import gg.pots.queue.player.QueuePlayerHandler;
import gg.pots.queue.player.listener.ProfileListener;
import gg.pots.queue.server.ServerHandler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class QueueSpigotPlugin extends JavaPlugin {

    public static QueueSpigotPlugin instance;

    public ServerHandler serverHandler;
    public QueuePlayerHandler playerHandler;

    public QueueHandler queueHandler;
    public BungeeHandler bungeeHandler;
    public CommandHandler commandHandler;

    public ConfigFile messagesFile;
    public ConfigFile serverFile;
    public ConfigFile ranksFile;
    public ConfigFile settingsFile;

    @Override
    public void onEnable() {
        instance = this;

        this.messagesFile = new ConfigFile(this, "messages.yml");
        this.serverFile = new ConfigFile(this, "servers.yml");
        this.ranksFile = new ConfigFile(this, "ranks.yml");
        this.settingsFile = new ConfigFile(this, "settings.yml");

        this.serverHandler = new ServerHandler();
        this.playerHandler = new QueuePlayerHandler();
        this.queueHandler = new QueueHandler();
        this.bungeeHandler = new BungeeHandler();
        this.commandHandler = new CommandHandler();

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", bungeeHandler);

        Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);
    }

    @Override
    public void onDisable() {
        this.playerHandler.getQueuePlayerMap().clear();

        instance = null;
    }
}
