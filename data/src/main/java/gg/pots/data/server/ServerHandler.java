package gg.pots.data.server;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ServerHandler {

    private final Map<String, Server> serverMap = new ConcurrentHashMap<>();

    /**
     * Method to initialise or update an existing server
     *
     * @param server The server object to update
     */
    public void initOrUpdateServer(Server server) {
        if (this.serverMap.get(server.getServerName().toLowerCase()) != null) {
            this.serverMap.replace(server.getServerName().toLowerCase(), server);
            return;
        }

        this.serverMap.put(server.getServerName().toLowerCase(), server);
    }

    /**
     * Method to get a server by its name
     *
     * @param serverName The name of the server
     * @return The server object found. Default: null
     */
    public Server getServerByName(String serverName) {
        return this.serverMap.getOrDefault(serverName.toLowerCase(), null);
    }
}
