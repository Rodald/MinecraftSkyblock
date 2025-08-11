package net.rodald.minecraftSkyblock;

import net.rodald.minecraftSkyblock.map.GameMap;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public record Island(GameMap map, Player owner) {

    public Location spawn() {
        World world = map.getWorld();

        Location spawn = world.getSpawnLocation();
        spawn.set(0.5f, world.getHighestBlockYAt(0, 0) + 1, 0.5f);
        return spawn;
    }

    public void teleport(Player player) {
        if (owner == player) {
            player.setGameMode(GameMode.SURVIVAL);
        } else {
            player.setGameMode(GameMode.ADVENTURE);
        }

        player.teleport(spawn());
    }
}
