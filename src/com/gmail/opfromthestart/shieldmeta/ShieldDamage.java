package com.gmail.opfromthestart.shieldmeta;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Objects;

public class ShieldDamage implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent ede)
    {
        Entity playerEntity = ede.getEntity();
        if (playerEntity instanceof Player player)
        {
            if (player.isBlocking() && ede.getCause().compareTo(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)==0)
            {
                ede.setCancelled(true);

                ItemStack shieldItem;
                if (player.getInventory().getItemInMainHand().getType() == Material.SHIELD)
                {
                    shieldItem = player.getInventory().getItemInMainHand();
                    PlayerItemDamageEvent playerItemDamageEvent = new PlayerItemDamageEvent(player, shieldItem, (int) Math.ceil( ede.getDamage()));
                    Bukkit.getPluginManager().callEvent(playerItemDamageEvent);

                    Damageable shield = (Damageable) shieldItem.getItemMeta();

                    if (!playerItemDamageEvent.isCancelled())
                    {
                        assert shield != null;
                        shield.setDamage(shield.getDamage() + playerItemDamageEvent.getDamage());

                        if (shield.getDamage() > 336) // TODO dont make this hard coded
                            shieldItem.setAmount(0);
                    }

                    shieldItem.setItemMeta(shield);
                    player.getInventory().setItemInMainHand(shieldItem);
                }
                else if (player.getInventory().getItemInOffHand().getType() == Material.SHIELD)
                {
                    shieldItem = Objects.requireNonNull(player.getInventory().getItemInOffHand());
                    PlayerItemDamageEvent playerItemDamageEvent = new PlayerItemDamageEvent(player, shieldItem, (int) Math.ceil( ede.getDamage()));
                    Bukkit.getPluginManager().callEvent(playerItemDamageEvent);

                    Damageable shield = (Damageable) shieldItem.getItemMeta();

                    if (!playerItemDamageEvent.isCancelled())
                    {
                        assert shield != null;
                        shield.setDamage(shield.getDamage() + playerItemDamageEvent.getDamage());
                    }

                    shieldItem.setItemMeta(shield);
                    player.getInventory().setItemInOffHand(shieldItem);
                }
            }
        }
    }
}
