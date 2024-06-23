package gg.pots.lobby.item.selector;

import gg.pots.data.DataAPI;
import gg.pots.data.server.Server;
import gg.pots.data.server.ServerStatus;
import gg.pots.lobby.LobbySpigotPlugin;

import gg.pots.lobby.util.CC;
import io.github.nosequel.menu.Menu;
import io.github.nosequel.menu.buttons.Button;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public class ServerSelector extends Menu {

    private final DataAPI dataAPI = DataAPI.getInstance();

    /**
     * Constructor to create a new {@link ServerSelector} object.
     * <p><
     * This method calls the {@link Menu#Menu(Player, String, int)}
     * constructor.
     *
     * @param player the player to open the menu for.
     */

    public ServerSelector(Player player) {
        super(player, CC.translate(LobbySpigotPlugin.instance
                        .getConfig()
                        .getString("selector.name")),

                LobbySpigotPlugin.instance
                        .getConfig()
                        .getInt("selector.size"));
    }

    @Override
    public void tick() {
        final ConfigurationSection keySection = LobbySpigotPlugin.instance.getConfig().getConfigurationSection("servers");

        for (String key : keySection.getKeys(false)) {
            final ConfigurationSection section = LobbySpigotPlugin.instance.getConfig().getConfigurationSection("servers." + key);
            final String serverName = section.getString("server");

            this.buttons[section.getInt("slot")] = new Button(Material.valueOf(section.getString("material")))
                    .setDisplayName(CC.translate(section.getString("name")))
                    .setLore(this.getLore(section.getStringList("lore"), serverName))
                    .setClickAction(event -> {
                        this.getPlayer().performCommand(section.getString("command"));
                        event.setCancelled(true);
                    });
        }
    }

    /**
     * Get the lore of the item.
     *
     * @param lore the lore.
     * @param serverName the serverName.
     * @return the lore.
     */

    private String[] getLore(List<String> lore, String serverName) {
        final String[] strings = new String[lore.size()];
        final Server server = this.dataAPI.getServerHandler().getServerByName(serverName);

        for (int i = 0; i < lore.size(); i++) {
            if (server != null) {
                strings[i] = CC.translate(lore.get(i)
                        .replace("%online_players%", server.getOnlinePlayers() + "")
                        .replace("%status%", server.getServerStatus().getFormat()));
            } else {
                strings[i] = CC.translate(lore.get(i))
                        .replace("%online_players%", "0")
                        .replace("%status%", ServerStatus.OFFLINE.getFormat());
            }
        }

        return strings;
    }
}
