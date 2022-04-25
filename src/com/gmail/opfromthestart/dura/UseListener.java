package com.gmail.opfromthestart.dura;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

//Written based on Xymb's dura

public class UseListener implements Listener {
    @EventHandler
    public void onObserverUpdate(PlayerItemDamageEvent event) {
        ItemStack itm = event.getItem();
        PlayerInventory inventory = event.getPlayer().getInventory();
        net.minecraft.world.item.ItemStack itmnms = CraftItemStack.asNMSCopy(itm);
        NBTTagCompound nbt = itmnms.s();
        assert nbt != null;
        int dmgval = nbt.h("dmg");
        if (dmgval <= 0) {
            return;
        }
        nbt.a("dmg", dmgval-1);
        ItemStack itmt = CraftItemStack.asBukkitCopy(itmnms);
        switch (itm.getType().getEquipmentSlot()) {
            case HEAD -> inventory.setHelmet(itmt);
            case CHEST -> inventory.setChestplate(itmt);
            case LEGS -> inventory.setLeggings(itmt);
            case FEET -> inventory.setBoots(itmt);
            case OFF_HAND -> inventory.setItemInOffHand(itmt);
            case HAND -> inventory.setItemInMainHand(itmt);
                /*
                if (inventory.getItemInOffHand().getType() != Material.AIR) {
                    NBTItem nbtItemOffHand = new NBTItem(inventory.getItemInOffHand());
                    if (inventory.getItemInOffHand().getType() == itm.getType() && inventory.getItemInOffHand().getEnchantments().toString().equals(itm.getEnchantments().toString()) && nbtItemOffHand.hasKey("dmg")) {
                        inventory.setItemInOffHand(itm);
                    }
                }
                if (inventory.getItemInMainHand().getType() != Material.AIR) {
                    NBTItem nbtItemMainHand = new NBTItem(inventory.getItemInMainHand());
                    if (inventory.getItemInMainHand().getType() == itm.getType() && inventory.getItemInMainHand().getEnchantments().toString().equals(itm.getEnchantments().toString()) && nbtItemMainHand.hasKey("dmg")) {
                        inventory.setItemInMainHand(itm);
                    }
                }
            }*/
        }
        event.setCancelled(true);
    }
}
