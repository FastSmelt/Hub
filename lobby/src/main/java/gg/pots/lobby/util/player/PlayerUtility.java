package gg.pots.lobby.util.player;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class PlayerUtility {

    /**
     * Reset a {@link Player}
     *
     * @param player the player.
     */

    public static void resetPlayer(Player player) {
        player.setGameMode(GameMode.ADVENTURE);

        player.setHealthScale(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setSaturation(12.8F);

        player.setMaximumNoDamageTicks(20);
        player.setFireTicks(0);
        player.setFireTicks(0);

        player.setFallDistance(0.0F);
        player.setLevel(0);
        player.setExp(0.0F);
        player.setWalkSpeed(0.2F);
        player.setAllowFlight(false);
        player.teleport(player.getWorld().getSpawnLocation());

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setHeldItemSlot(0);

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        player.updateInventory();
    }

    /**
     * Play a {@link Sound} to a {@link Player}.
     *
     * @param player the player.
     * @param sound the sound.
     */

    public static void playSound(Player player, String sound) {
        player.playSound(player.getLocation(), Sound.valueOf(sound), 2F, 2F);
    }
}
