package gg.pots.lobby.listener;

import gg.pots.lobby.LobbySpigotPlugin;
import gg.pots.lobby.item.ItemHandler;
import gg.pots.lobby.item.selector.ServerSelector;
import gg.pots.lobby.util.CC;
import gg.pots.lobby.util.player.PlayerUtility;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        PlayerUtility.resetPlayer(player);
        ItemHandler.loadItems(player.getInventory());

        for (String message : LobbySpigotPlugin.instance.getConfig().getStringList("welcome.message")) {
            player.sendMessage(CC.translate(message));
        }

        event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onSelectorInteraction(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getItem().getType().equals(Material.valueOf(LobbySpigotPlugin.instance.itemFile.getString("server-selector.material")))) {
                new ServerSelector(event.getPlayer()).updateMenu();
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();

        if (player.hasMetadata("buildmode")) {
            return;
        }

        player.sendMessage(CC.translate("&cYou can't build in the hub!"));
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        if (player.hasMetadata("buildmode")) {
            return;
        }

        player.sendMessage(CC.translate("&cYou can't break blocks in the hub!"));
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        final Entity entity = event.getEntity();

        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setHealth(((LivingEntity) entity).getMaxHealth());
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            entity.teleport(entity.getWorld().getSpawnLocation());
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onMoveItem(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) {
            return;
        }

        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onCropInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }

        if (event.getClickedBlock().getType().equals(Material.CROPS)) {
            event.setCancelled(true);
        }
    }
}
