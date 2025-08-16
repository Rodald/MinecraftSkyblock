package net.rodald.minecraftSkyblock.block;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.rodald.cerasislib.blocks.CustomBlock;
import net.rodald.cerasislib.blocks.interfaces.DirectionalBlock;
import net.rodald.cerasislib.blocks.interfaces.Touchable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Conveyor extends CustomBlock implements DirectionalBlock, Touchable {

    private static final Map<UUID, List<Vector>> movedThisTick = new HashMap<>();
    private static long lastTick = -1;

    public Conveyor() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("the material is " + this.getMaterial());
        }
    }

    @Override
    public Component getItemName() {
        return Component.text("Conveyor").decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public List<Component> getItemLore() {
        return List.of();
    }

    @Override
    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey("cerasis", "skyblock/automation/conveyor");
    }

    @Override
    public @NotNull Material getBlockType() {
        return Material.PURPUR_SLAB;
    }

    @Override
    public float getBlockHardness() {
        return 20f;
    }

    @Override
    public @NotNull Material getParticleBlockType() {
        return Material.STONE;
    }

    @Override
    public void handleSteppedOn(Location location, Entity entity, ItemDisplay itemDisplay) {
        long currentTick = Bukkit.getCurrentTick();

        // Reset movedThisTick at beginning of each tick
        if (currentTick != lastTick) {
            movedThisTick.clear();
            lastTick = currentTick;
        }

        String facing = itemDisplay.getPersistentDataContainer().get(
                new NamespacedKey("cerasis", "facing"), PersistentDataType.STRING
        );

        Vector movement = switch (facing) {
            case "NORTH" -> new Vector(0, 0, -0.02);
            case "EAST" -> new Vector(0.02, 0, 0);
            case "SOUTH" -> new Vector(0, 0, 0.02);
            case "WEST" -> new Vector(-0.02, 0, 0);
            default -> new Vector(0, 0, 0);
        };

        List<Vector> list = movedThisTick.computeIfAbsent(entity.getUniqueId(), k -> new ArrayList<>());

        // Nur bewegen, wenn dieselbe Richtung noch nicht hinzugefügt wurde
        for (Vector v : list) {
            if (v.equals(movement)) return;
        }

        list.add(movement);
        entity.setVelocity(entity.getVelocity().add(movement));    }
}
