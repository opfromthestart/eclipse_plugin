package com.gmail.opfromthestart.bedsave;

import com.gmail.opfromthestart.PluginListener;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class BedDeathListener extends PluginListener {

    public Map<UUID, Location> bed_locs;
    public List<Chunk> death_chunks;

    public BedDeathListener(Plugin plug) {
        super(plug);

        bed_locs = new HashMap<>();
        death_chunks = new ArrayList<>();
        // bed_worlds = new HashMap<>();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent)
    {
        Player player = playerJoinEvent.getPlayer();
        // plugin.getLogger().info(player.getBedSpawnLocation().toString());
        if (player.getBedSpawnLocation() != null) {
            bed_locs.put(player.getUniqueId(), player.getBedSpawnLocation().clone());
            //plugin.getLogger().info(player.getUniqueId().toString());
            //plugin.getLogger().info(player.getBedSpawnLocation().toString());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent)
    {
        Player player = playerInteractEvent.getPlayer();
        // plugin.getLogger().info(player.getBedSpawnLocation().toString());
        if (player.getBedSpawnLocation() != null) {
            bed_locs.put(player.getUniqueId(), player.getBedSpawnLocation().clone());
            //plugin.getLogger().info(player.getUniqueId().toString());
            //plugin.getLogger().info(player.getBedSpawnLocation().toString());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent)
    {
        if (playerDeathEvent.getEntity().getBedSpawnLocation() != null) {
            death_chunks.add(playerDeathEvent.getEntity().getBedSpawnLocation().getChunk());
            //plugin.getLogger().info(String.valueOf(playerDeathEvent.getEntity().getBedSpawnLocation().getChunk().getX()));
        }
    }


    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent chunkUnloadEvent) {
        CraftChunk chunk = (CraftChunk) chunkUnloadEvent.getChunk();
        if (death_chunks.contains(chunk))
        {
            chunk.load(false);
            chunk.setForceLoaded(true);
            Thread thread = new Thread(()->{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                chunk.setForceLoaded(false);});
            death_chunks.remove(chunk);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent)
    {
        CraftPlayer player = (CraftPlayer) playerRespawnEvent.getPlayer();
        if (bed_locs.containsKey(player.getUniqueId())) {
            //plugin.getLogger().info(player.getUniqueId().toString());
            Location loc = bed_locs.get(player.getUniqueId());
            player.setBedSpawnLocation(loc, true);
        }
        // bed_locs.remove(player.getUniqueId());
    }
}
