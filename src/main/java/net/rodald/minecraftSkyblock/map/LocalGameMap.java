package net.rodald.minecraftSkyblock.map;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.rodald.minecraftSkyblock.Island;
import net.rodald.minecraftSkyblock.manager.IslandManager;
import net.rodald.minecraftSkyblock.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

public class LocalGameMap implements GameMap {
    private final File sourceWorldFolder;
    private File activeWorldFolder;

    private World bukkitWorld;
    private final String worldName;


    public LocalGameMap(File sourceWorldFolder, String worldName, boolean loadOnInit) {
        this.sourceWorldFolder = sourceWorldFolder;
        this.worldName = worldName;

        if (loadOnInit) load();
    }

    public boolean load() {
        if (isLoaded()) return true;

        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer().getParentFile(),
                worldName
        );

        if (sourceWorldFolder != null) {
            try {
                FileUtil.copy(sourceWorldFolder, activeWorldFolder);
            } catch (IOException e) {
                Bukkit.getLogger().severe("Failed to load GameMap from src folder " + sourceWorldFolder);
                e.printStackTrace();
                return false;
            }
        }

        this.bukkitWorld = Bukkit.createWorld(
                new WorldCreator(activeWorldFolder.getName())
        );

        if (bukkitWorld != null) this.bukkitWorld.setAutoSave(true);
        return isLoaded();
    }

    public boolean unLoad() {
        if (bukkitWorld != null) {
            // kick all players in world
            bukkitWorld.getPlayers().forEach(visitor -> {
                Island visitorIsland = IslandManager.getIsland(visitor);

                // teleport visitor back home
                visitorIsland.teleport(visitor);
                visitor.sendMessage(Component.text("The owner of the Island left. You got teleported back to your Island.", NamedTextColor.RED));
            });

            boolean success = Bukkit.unloadWorld(bukkitWorld, true);
            if (!success) {
                // Failed to unload
                return false;
            }

            bukkitWorld = null;
        }

        return true;
    }


    public boolean restoreFromSource() {
        unLoad();
        return load();
    }

    public boolean isLoaded() {
        return bukkitWorld != null;
    }

    public World getWorld() {
        return bukkitWorld;
    }
}
