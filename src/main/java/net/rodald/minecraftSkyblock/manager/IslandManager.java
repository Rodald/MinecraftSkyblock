package net.rodald.minecraftSkyblock.manager;

import net.rodald.minecraftSkyblock.Island;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class IslandManager {
    private static final Map<Player, Island> activeIslands = new HashMap<>();

    public static Island getIsland(Player player) {
        return activeIslands.get(player);
    }

    public static void addIsland(Player player, Island island) {
        activeIslands.putIfAbsent(player, island);
    }

    public static void removeIsland(Player player) {
        activeIslands.remove(player);
    }

    public static void clearAllIslands() {
        activeIslands.clear();
    }


}
