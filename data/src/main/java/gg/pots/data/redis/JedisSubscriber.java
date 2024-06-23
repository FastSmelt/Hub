package gg.pots.data.redis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gg.pots.data.server.Server;
import gg.pots.data.util.CC;
import gg.pots.data.util.logger.LoggerUtility;
import redis.clients.jedis.JedisPubSub;

public class JedisSubscriber extends JedisPubSub {

    private final JedisHandler jedisManager;
    private final JsonParser jsonParser = new JsonParser();

    public JedisSubscriber(JedisHandler jedisManager) {
        this.jedisManager = jedisManager;
    }

    @Override
    public void onMessage(String channel, String message) {
        if (channel.equalsIgnoreCase(this.jedisManager.getJedisChannel())) {
            final String[] data = message.split("///");
            final String command = data[0];

            if (command.equals("ServerUpdate")) {
                final JsonObject object = jsonParser.parse(data[1]).getAsJsonObject();

                this.jedisManager.getServerHandler().initOrUpdateServer(new Server(object));
                
            } else {
                LoggerUtility.sendMessage(CC.translate("&cCouldn't recognize packet: " + command));
            }
        }
    }
}
