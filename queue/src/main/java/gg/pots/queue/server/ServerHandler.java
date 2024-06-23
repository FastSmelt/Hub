package gg.pots.queue.server;

import gg.pots.queue.QueueSpigotPlugin;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ServerHandler {

    private final List<Server> servers = new ArrayList<>();

    public ServerHandler() {
        for (String name : QueueSpigotPlugin.instance.serverFile.getConfiguration().getStringList("servers")) {
            final Server server = new Server(name);

            this.servers.add(server);
        }
    }

    /**
     * Find a {@link Server} by a {@link String}
     *
     * @param name the name.
     * @return the server or null.
     */

    public Server findServer(String name) {
        for (Server server : this.servers) {
            if (server.getName().equalsIgnoreCase(name)) {
                return server;
            }
        }

        return null;
    }
}
