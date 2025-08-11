package net.rodald.minecraftSkyblock.listener;

import net.rodald.minecraftSkyblock.Island;
import net.rodald.minecraftSkyblock.MinecraftSkyblock;
import net.rodald.minecraftSkyblock.manager.IslandManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerQuitListener implements Listener {

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Island island = IslandManager.getIsland(player);
        if (island != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (island.map().unLoad()) {
                        IslandManager.removeIsland(player);
                        this.cancel();
                    }
                }
            }.runTaskTimer(MinecraftSkyblock.getInstance(), 0, 1);
        }
    }

}
