package com.gmail.opfromthestart.dura;

import com.gmail.opfromthestart.PluginListener;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

//Written based on Xymb's dura

public class UseListener extends PluginListener {
    public UseListener(Plugin plug)
    {
        super(plug);
    }

    @EventHandler
    public void onObserverUpdate(PlayerItemDamageEvent event) {
        if (!plugin.getConfig().getBoolean("eclipseplugin.dura.active"))
            return;
        ItemStack itm = event.getItem();
        PlayerInventory inventory = event.getPlayer().getInventory();
        int place = inventory.first(itm);
        net.minecraft.world.item.ItemStack itmnms = CraftItemStack.asNMSCopy(itm);
        NBTTagCompound nbt = itmnms.t();
        assert nbt != null;
        if (!nbt.e("dmg"))
        {
            return;
        }
        int dmgval = nbt.h("dmg")-event.getDamage();
        //JavaPlugin.getPlugin(Main.class).getLogger().info("Remaining damage:" + dmgval);
        if (dmgval < 0) {
            event.setDamage(-dmgval);
            return;
        }
        nbt.a("dmg", dmgval);
        itmnms.c(nbt);
        ItemStack itmt = CraftItemStack.asBukkitCopy(itmnms);
        //event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), itmt);
        inventory.setItem(place, itmt);
        event.setCancelled(true);
    }
}
