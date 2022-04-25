package com.gmail.opfromthestart.roof;

import com.gmail.opfromthestart.Messages;
import com.gmail.opfromthestart.TPS;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Objects;

public class Tracker implements Listener {
    public HashMap<String, Location> pastLocs;
    public JavaPlugin plugin;
    public TPS tps;

    public Tracker(JavaPlugin plug, TPS tp)
    {
        plugin = plug;
        tps = tp;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent pme)
    {
        Player player = pme.getPlayer();
        if (Objects.requireNonNull(player.getLocation().getWorld()).hasCeiling()) { //128
            if (player.getLocation().getY()>127) {

                double speed = player.getVelocity().length();
                if (tps.tps < plugin.getConfig().getInt("eclipseplugin.roof.offtps")) {
                    player.teleport(new Location(player.getWorld(), player.getLocation().getX(), 121, player.getLocation().getZ()));
                    Messages.sendActionBar(player, "§7Nether roof is disabled under §6"
                            + plugin.getConfig().getDouble("eclipseplugin.roof.offtps") + "§7 TPS");
                } else if (tps.tps < plugin.getConfig().getInt("eclipseplugin.roof.lowtps")) {
                    if (speed > plugin.getConfig().getInt("eclipseplugin.roof.lowspeed")) {
                        player.setVelocity(player.getVelocity().normalize().multiply(plugin.getConfig().getDouble("eclipseplugin.roof.lowspeed")));
                        Messages.sendActionBar(player, "§7Max speed under §6" + plugin.getConfig().getDouble("eclipseplugin.roof.lowtps") +
                                " §7TPS is §6" + plugin.getConfig().getInt("eclipseplugin.roof.lowspeed") + "§7BPS");
                        player.teleport(pme.getFrom());
                    }
                } else if (speed > plugin.getConfig().getInt("eclipseplugin.roof.speed")) {
                    player.setVelocity(player.getVelocity().normalize().multiply(plugin.getConfig().getDouble("eclipseplugin.roof.speed")));
                    Messages.sendActionBar(player, "§7Max speed is §6" + plugin.getConfig().getDouble("eclipseplugin.roof.speed")
                    + "§7BPS");
                    player.teleport(pme.getFrom());
                }
                Messages.sendMe(speed);
            }
        }
    }
}
