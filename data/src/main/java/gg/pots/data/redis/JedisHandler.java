package gg.pots.data.redis;

import gg.pots.data.DataSpigotPlugin;
import gg.pots.data.server.ServerHandler;
import gg.pots.data.util.CC;
import gg.pots.data.util.logger.LoggerUtility;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.CompletableFuture;

@Getter
public class JedisHandler {

    private final ServerHandler serverHandler;

    private final JedisPool jedisPool;
    private final Jedis jedis;

    private final String jedisChannel;
    private final String jedisPassword;

    private final boolean jedisAuth;

    public JedisHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;

        final String databasePath = "DATABASE.REDIS.";

        this.jedisChannel = DataSpigotPlugin.instance.settingsFile.getString(databasePath + "CHANNEL");
        this.jedisAuth = DataSpigotPlugin.instance.settingsFile.getBoolean(databasePath + "AUTH.ENABLED");
        this.jedisPassword = DataSpigotPlugin.instance.settingsFile.getString(databasePath + "AUTH.PASSWORD");

        this.jedisPool = new JedisPool(
                DataSpigotPlugin.instance.settingsFile.getString(databasePath + "ADDRESS"),
                DataSpigotPlugin.instance.settingsFile.getInt(databasePath + "PORT"));

        this.jedis = this.jedisPool.getResource();
        this.authenticate();
        this.subscribe();
    }

    /**
     * Authenticate jedis
     */
    public void authenticate() {
        if (this.jedisAuth) {
            this.jedis.auth(this.jedisPassword);
        }
    }

    /**
     * Try to subscribe to the jedis channel
     */
    public void subscribe() {
        CompletableFuture.runAsync(() -> {
            try {
                this.jedis.subscribe(new JedisSubscriber(this), this.jedisChannel);

                LoggerUtility.sendMessage(CC.translate("&aSubscribed to jedis channel: " + this.jedisChannel));
            } catch (Exception exception) {
                exception.printStackTrace();

                LoggerUtility.sendMessage(CC.translate("&cCould not subscribe to jedis channel: " + this.jedisChannel + " for " + exception.getMessage()));
            }
        });
    }
}
