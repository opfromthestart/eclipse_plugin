package com.gmail.opfromthestart.dupe;

import com.gmail.opfromthestart.PluginListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class ItemFrameDupe extends PluginListener {
    public ItemFrameDupe(Plugin plug) {
        super(plug);
    }

    @EventHandler
    public void onItemFrameHit(EntityDamageEvent entityDamageEvent) {
        if (!plugin.getConfig().getBoolean("eclipseplugin.dupe.itemframe.active"))
            return;
        Entity frame = entityDamageEvent.getEntity();
        if (frame.getWorld().getNearbyEntities(frame.getLocation(), 60, 60, 60).size() >
                plugin.getConfig().getInt("eclipseplugin.dupe.itemlimit"))
            return;
        if (frame instanceof ItemFrame frame1) {
            Random r = new Random();
            if (r.nextDouble() < plugin.getConfig().getDouble("eclipseplugin.dupe.itemframe.rate"))
                frame.getWorld().dropItem(frame.getLocation(), frame1.getItem());
        }
    }
}
