package net.rodald.minecraftSkyblock.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.rodald.minecraftSkyblock.manager.IslandManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // fail check if player somehow manages to play without owning an island
        if (IslandManager.getIsland(player) == null) {
            player.kick(Component.text("Your Session is invalid. Please rejoin the server.", NamedTextColor.RED));
            event.setCancelled(true);
            return;
        }

        // prevents the player from destroying server or other players property
        if (player.getWorld() != IslandManager.getIsland(player).map().getWorld()) {
            event.setCancelled(true);
            return;
        }
    }
}
