package com.gmail.opfromthestart.bedsave;

import com.gmail.opfromthestart.PluginListener;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BedDeathListener extends PluginListener {

    public Map<UUID, Location> bed_locs;

    public BedDeathListener(Plugin plug) {
        super(plug);

        bed_locs = new HashMap<>();
        // bed_worlds = new HashMap<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent)
    {
        Player player = playerInteractEvent.getPlayer();
        // plugin.getLogger().info(player.getBedSpawnLocation().toString());
        if (player.getBedSpawnLocation() != null) {
            bed_locs.put(player.getUniqueId(), player.getBedSpawnLocation().clone());
            plugin.getLogger().info(player.getUniqueId().toString());
            plugin.getLogger().info(player.getBedSpawnLocation().toString());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent)
    {
        CraftPlayer player = (CraftPlayer) playerRespawnEvent.getPlayer();
        if (bed_locs.containsKey(player.getUniqueId())) {
            plugin.getLogger().info(player.getUniqueId().toString());
            Location loc = bed_locs.get(player.getUniqueId());
            player.setBedSpawnLocation(loc, true);
        }
        // bed_locs.remove(player.getUniqueId());
    }
}
