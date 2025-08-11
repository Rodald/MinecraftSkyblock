package net.rodald.minecraftSkyblock;

import net.rodald.minecraftSkyblock.listener.PlayerInteractListener;
import net.rodald.minecraftSkyblock.listener.PlayerJoinListener;
import net.rodald.minecraftSkyblock.listener.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class MinecraftSkyblock extends JavaPlugin {

    private static MinecraftSkyblock instance;


    @Override
    public void onEnable() {
        instance = this;
        File gameMapsFolder = new File(getDataFolder(), "gameMaps");
        if (!gameMapsFolder.exists()) {
            gameMapsFolder.mkdirs();
        }

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MinecraftSkyblock getInstance() {
        return instance;
    }

}
