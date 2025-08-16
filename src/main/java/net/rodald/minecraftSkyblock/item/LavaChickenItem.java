package net.rodald.minecraftSkyblock.item;

import net.kyori.adventure.text.Component;
import net.rodald.cerasislib.items.CustomItem;
import net.rodald.cerasislib.items.interfaces.Consumable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LavaChickenItem extends CustomItem implements Consumable {

    @Override
    public @NotNull Material getMaterial() {
        return Material.ECHO_SHARD;
    }

    @Override
    public Component getItemName() {
        return Component.text("Lava Chicken");
    }

    @Override
    public List<Component> getItemLore() {
        return List.of();
    }

    @Override
    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey("cerasis", "skyblock/item/food/lava_chicken");
    }

    @Override
    public void prepareItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemStack.setItemMeta(itemMeta);
    }


    @Override
    public void handleConsumption(PlayerItemConsumeEvent playerItemConsumeEvent) {

    }

    @Override
    public int getNutrition() {
        return 10;
    }

    @Override
    public float getSaturation() {
        return 10;
    }

    @Override
    public boolean canAlwaysEat() {
        return true;
    }
}
