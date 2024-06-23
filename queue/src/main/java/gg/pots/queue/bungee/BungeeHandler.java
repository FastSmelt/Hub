package gg.pots.queue.bungee;

import gg.pots.queue.QueueSpigotPlugin;
import gg.pots.queue.server.Server;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

@Getter
public class BungeeHandler implements PluginMessageListener {

    /**
     * Send a {@link Player} to a {@link Server}
     *
     * @param player the player.
     * @param server the server.
     */

    public void sendPlayerToServer(Player player, Server server) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server.getName());
            player.sendPluginMessage(QueueSpigotPlugin.instance, "BungeeCord", byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.close();
            dataOutputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

    }
}
