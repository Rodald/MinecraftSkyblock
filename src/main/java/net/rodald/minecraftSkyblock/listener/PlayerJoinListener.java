package net.rodald.minecraftSkyblock.listener;


import net.rodald.minecraftSkyblock.Island;
import net.rodald.minecraftSkyblock.MinecraftSkyblock;
import net.rodald.minecraftSkyblock.manager.IslandManager;
import net.rodald.minecraftSkyblock.map.LocalGameMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        File islandsFolder = Bukkit.getWorldContainer().getParentFile();
        File playerIsland = new File(islandsFolder, player.getUniqueId().toString());

        Island island;

        if (playerIsland.exists()) {
            // Player already has an island
            island = IslandManager.getIsland(player);

            // if everything went smooth, island should always be null
            // because the map gets unloaded/removed from the IslandManager when the player leaves
            if (island == null) {
                // load the world, don't regenerate the world bc player doesn't want to lose his progress
                LocalGameMap islandMap = new LocalGameMap(
                        null,
                        player.getUniqueId().toString(),
                        true
                );

                island = new Island(islandMap, player);
            }
        } else {
            // Player doesnt have an island yet
            File gameMapsFolder = new File(MinecraftSkyblock.getInstance().getDataFolder(), "gameMaps");
            File defaultIslandSource = new File(gameMapsFolder, "defaultIsland");

            if (!defaultIslandSource.exists()) {
                Bukkit.getLogger().severe("Default island not found! Please ensure the defaultIsland exists in 'gameMaps'.");
                player.kickPlayer("Server misconfigured. Missing default island.");
                return;
            }

            // load new world
            LocalGameMap islandMap = new LocalGameMap(defaultIslandSource, player.getUniqueId().toString(), true);
            island = new Island(islandMap, player);
            Bukkit.getLogger().info("Created a new island for player " + player.getName());
        }

        // register island
        IslandManager.addIsland(player, island);

        // set player spawn in case he dies
        player.setRespawnLocation(island.spawn());

        // teleport player to own island
        // dont have to set gamemode bc island#teleport takes over that
        island.teleport(player);
    }
}