package com.gmail.opfromthestart.roof;

import com.comphenix.protocol.wrappers.Pair;
import com.gmail.opfromthestart.Messages;
import com.gmail.opfromthestart.PluginListener;
import com.gmail.opfromthestart.TPS;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Objects;

public class Tracker extends PluginListener {
    public HashMap<String, Pair<Long, Location>> pastLocs;
    public TPS tps;
    public static final int skipTime = 1000;
    public static final float tolerance = 1.3f;

    public Tracker(JavaPlugin plug, TPS tp)
    {
        super(plug);
        plugin = plug;
        tps = tp;
        pastLocs = new HashMap<>();
    }

    public static double xzDist(Location a, Location b)
    {
        return Math.sqrt(Math.pow(a.getX()-b.getX(),2)+Math.pow(a.getZ()-b.getZ(),2));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent pme) // TODO make a player speed class
    {
        Player player = pme.getPlayer();
        if (Objects.requireNonNull(player.getLocation().getWorld()).hasCeiling()) { //128
            if (player.getLocation().getY()>127) {
                Messages.sendMe(player.getName());
                if (!pastLocs.containsKey(player.getName()))
                {
                    pastLocs.put(player.getName(), new Pair<>(System.currentTimeMillis(), player.getLocation()));
                    return;
                }
                Pair<Long, Location> pastLoc = pastLocs.get(player.getName());
                if (System.currentTimeMillis() - pastLoc.getFirst() < skipTime)
                {
                    return;
                }
                double speed = 1000*(Tracker.xzDist(pastLoc.getSecond(), player.getLocation()))/(System.currentTimeMillis()-pastLoc.getFirst());
                if (tps.tps < plugin.getConfig().getInt("eclipseplugin.roof.offtps")) {
                    if (plugin.getConfig().getBoolean("eclipseplugin.roof.killifabove"))
                        player.damage(1024);
                    else
                        player.teleport(new Location(player.getWorld(), player.getLocation().getX(), 121, player.getLocation().getZ()));
                    Messages.sendActionBar(player, "§7Nether roof is disabled under §6"
                            + plugin.getConfig().getDouble("eclipseplugin.roof.offtps") + "§7 TPS");
                } else if (tps.tps < plugin.getConfig().getInt("eclipseplugin.roof.lowtps")) {
                    if (speed > plugin.getConfig().getInt("eclipseplugin.roof.lowspeed")) {
                        Messages.sendActionBar(player, "§7Max speed under §6" + plugin.getConfig().getDouble("eclipseplugin.roof.lowtps") +
                                " §7TPS is §6" + plugin.getConfig().getInt("eclipseplugin.roof.lowspeed") + "§7BPS");
                        Vector direction = player.getVelocity();
                        direction.setY(0);
                        direction = direction.normalize().multiply(tolerance*plugin.getConfig().getDouble("eclipseplugin.roof.lowspeed"));
                        player.teleport(pastLoc.getSecond().add(direction));
                        player.setVelocity(player.getVelocity().normalize().multiply(plugin.getConfig().getDouble("eclipseplugin.roof.lowspeed")));
                    }
                } else if (speed > plugin.getConfig().getInt("eclipseplugin.roof.speed")) {
                    Messages.sendActionBar(player, "§7Max speed is §6" + plugin.getConfig().getDouble("eclipseplugin.roof.speed")
                    + "§7BPS");
                    Vector direction = player.getVelocity();
                    direction.setY(0);
                    direction = direction.normalize().multiply(tolerance*plugin.getConfig().getDouble("eclipseplugin.roof.speed"));
                    player.teleport(pastLoc.getSecond().add(direction));
                    player.setVelocity(player.getVelocity().normalize().multiply(plugin.getConfig().getDouble("eclipseplugin.roof.speed")));
                }
                pastLocs.put(player.getName(), new Pair<>(System.currentTimeMillis(), player.getLocation()));
                Messages.sendMe(speed);
            }
        }
    }
}
