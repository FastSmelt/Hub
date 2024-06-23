package gg.pots.lobby.item;

import gg.pots.lobby.LobbySpigotPlugin;
import gg.pots.lobby.util.CC;
import gg.pots.lobby.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemHandler {

    public static final ItemStack serverSelector = new ItemBuilder(Material.valueOf(LobbySpigotPlugin.instance.getItemFile().getString("server-selector.material")))
            .setDisplayName(CC.translate(LobbySpigotPlugin.instance.getItemFile().getString("server-selector.name")))
            .setLore(CC.translate(LobbySpigotPlugin.instance.getItemFile().getStringList("server-selector.lore")))
            .buildItem();

    /**
     * Load all items into the {@link Player}'s {@link Inventory}
     *
     * @param inventory the inventory.
     */

    public static void loadItems(PlayerInventory inventory) {
        inventory.setItem(LobbySpigotPlugin.instance.getItemFile().getInt("server-selector.slot"), serverSelector);
    }
}
