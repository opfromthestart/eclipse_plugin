package com.gmail.opfromthestart.dura;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class UseListener implements Listener {
    @EventHandler
    public void onObserverUpdate(PlayerItemDamageEvent event) {
        ItemStack itm = event.getItem();
        PlayerInventory inventory = event.getPlayer().getInventory();
        NBTItem nbtItem = new NBTItem(itm);
        if (!nbtItem.hasKey("dmg")) {
            return;
        }
        int dmg = Integer.parseInt(nbtItem.getString("dmg"));
        if (dmg <= 0) {
            return;
        }
        nbtItem.setString("dmg", String.valueOf(dmg - 1));
        itm = nbtItem.getItem();
        switch (itm.getType().getEquipmentSlot()) {
            case HEAD -> inventory.setHelmet(itm);
            case CHEST -> inventory.setChestplate(itm);
            case LEGS -> inventory.setLeggings(itm);
            case FEET -> inventory.setBoots(itm);
            case OFF_HAND -> inventory.setItemInOffHand(itm);
            case HAND -> {
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
            }
        }
        event.setCancelled(true);
    }

}
