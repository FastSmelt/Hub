package gg.pots.lobby.util;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

@Getter
public class ItemBuilder {

    private final ItemStack itemStack;

    /**
     * Constructor to create an instance of a {@link ItemBuilder}
     *
     * @param material the material.
     * @param amount the amount.
     */

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    /**
     * Constructor to create an instance of a {@link ItemBuilder}
     *
     * @param material the material.
     */

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    /**
     * Constructor to create an instance of a {@link ItemBuilder}
     *
     * @param material the material.
     * @param amount the amount.
     * @param value the value.
     */

    public ItemBuilder(Material material, int amount, short value) {
        this.itemStack = new ItemStack(material, amount, value);
    }

    /**
     * Set the name of a {@link Item}
     *
     * @param name the name.
     * @return the item.
     */

    public ItemBuilder setDisplayName(String name) {
        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Set the lore of a {@link Item}
     *
     * @param lore the lore.
     * @return the item.
     */

    public ItemBuilder setLore(String... lore) {
        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Set the data of a {@link Item}
     *
     * @param data the data.
     * @return the item.
     */

    public ItemBuilder setData(byte data) {
        this.itemStack.setDurability(data);
        return this;
    }

    /**
     * Add a glow to a {@link Item}
     *
     * @return the item.
     */

    public ItemBuilder addGlow() {
        this.itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add a {@link Enchantment} to a {@link Item}
     *
     * @param enchantment the enchantment.
     * @param level the level.
     * @return the item.
     */

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Remove a {@link Enchantment} from a {@link Item}
     *
     * @param enchantment the enchantment.
     * @return the item.
     */

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Set the {@link Color} of a {@link Item}
     *
     * @param color the color.
     * @return the item.
     */

    public ItemBuilder setLeatherArmor(Color color) {
        final LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        this.itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    /**
     * Set the {@link SkullMeta} of a {@link Item}
     *
     * @param skullOwner the skullOwner.
     * @return the item.
     */

    public ItemBuilder setSkullOwner(String skullOwner) {
        try {
            final SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
            skullMeta.setOwner(skullOwner);
            this.itemStack.setItemMeta(skullMeta);
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }

        return this;
    }

    /**
     * Build a {@link ItemStack}
     *
     * @return the item.
     */

    public ItemStack buildItem() {
        return this.itemStack;
    }
}