package net.rodald.minecraftSkyblock.map;

import org.bukkit.World;

public interface GameMap {
    boolean load();

    boolean unLoad();

    boolean restoreFromSource();

    boolean isLoaded();

    World getWorld();
}
