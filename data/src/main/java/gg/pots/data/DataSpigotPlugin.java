package gg.pots.data;

import gg.pots.data.command.ServerDumpCommand;
import gg.pots.data.redis.JedisHandler;
import gg.pots.data.server.ServerHandler;
import gg.pots.data.task.ServerHeartbeatTask;
import gg.pots.data.thread.ServerUpdateThread;
import gg.pots.data.util.configuration.ConfigFile;
import gg.pots.data.util.tps.TPSRunnable;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class DataSpigotPlugin extends JavaPlugin {

    public static DataSpigotPlugin instance;

    private final TPSRunnable tpsRunnable = new TPSRunnable();

    public ServerHandler serverHandler;
    public JedisHandler jedisHandler;

    public ConfigFile settingsFile;

    @Override
    public void onEnable() {
        instance = this;

        this.settingsFile = new ConfigFile(this, "settings.yml");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TPSRunnable(), 0L, 1L);
        this.serverHandler = new ServerHandler();
        this.jedisHandler = new JedisHandler(serverHandler);

        new ServerUpdateThread(serverHandler, jedisHandler).start();
        new ServerHeartbeatTask(serverHandler).runTaskTimerAsynchronously(this, 0L, 200L);

        this.getCommand("serverdump").setExecutor(new ServerDumpCommand(this));

        new DataAPI();
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
