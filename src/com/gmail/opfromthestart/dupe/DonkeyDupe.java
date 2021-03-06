package com.gmail.opfromthestart.dupe;

import com.gmail.opfromthestart.Messages;
import com.gmail.opfromthestart.PluginListener;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//written based on 254nm's dupe plugin

public class DonkeyDupe extends PluginListener {

	Map<String, Long> lastDupe = new HashMap<>();
	JavaPlugin plugin;

	public DonkeyDupe(JavaPlugin plug)
	{
		super(plug);
	}

	@EventHandler
	public void onVehicleEnter(PlayerInteractAtEntityEvent event) {
		if (event.getRightClicked() instanceof Llama || event.getRightClicked() instanceof Mule || event.getRightClicked() instanceof Donkey) {
			ChestedHorse entity = (ChestedHorse) event.getRightClicked();
			if (entity.isCarryingChest()) {
				if ((event.getPlayer().getInventory().getItemInMainHand().getType() == Material.CHEST) || (event.getPlayer().getInventory().getItemInOffHand().getType() == Material.CHEST)) {
					event.setCancelled(true);
					if (event.getPlayer().hasPermission("eclipseplugin.dupe.use") && plugin.getConfig().getBoolean("eclipseplugin.dupe.donkey.active")) {
						purgeItems(event.getPlayer(), plugin.getConfig().getInt("eclipseplugin.dupe.itemlimit"));
						if (!lastDupe.containsKey(event.getPlayer().getName()))
						{
							lastDupe.put(event.getPlayer().getName(), 0L);
						}
						if (lastDupe.get(event.getPlayer().getName()) > System.currentTimeMillis() - plugin.getConfig().getInt("eclipseplugin.dupe.donkey.timeout")) {
							long timeleft = (-System.currentTimeMillis() + plugin.getConfig().getInt("eclipseplugin.dupe.donkey.timeout") + lastDupe.get(event.getPlayer().getName())) / 1000;
							Messages.sendActionBar(event.getPlayer(), "§7You can't dupe for another §6" + timeleft + " §7seconds.");
							return;
						}
						for (ItemStack item : entity.getInventory().getContents()) {
							if (item != null) {
								for (int i=0; i<plugin.getConfig().getInt("eclipseplugin.dupe.donkey.yield"); i++)
									entity.getWorld().dropItemNaturally(entity.getLocation(), item.clone());
							}
						}
						entity.setCarryingChest(false);
						//sendMessage(event.getPlayer(), "&cChests on llamas, donkeys, mules, Etc is currently disabled, this is to facilitate the SalC1 TreeMC dupe");
						lastDupe.put(event.getPlayer().getName(), System.currentTimeMillis());
					} else {
						Messages.sendActionBar(event.getPlayer(), "&6You do not have permission to dupe");
					}
				}
			}
			else
			{
				if (entity.getPassengers().size() == 0) {
					entity.addPassenger(event.getPlayer());
				}
			}
		}
	}

	private void purgeItems(Player player, int limit)
	{
		ArrayList<Entity> items = new ArrayList<>();
		int itemCount = 0;
		for (Entity entity : Objects.requireNonNull(player.getLocation().getWorld()).getNearbyEntities(player.getBoundingBox().expand(60, 60, 60))) {
			if (entity.getType() == EntityType.DROPPED_ITEM && itemCount >= limit) {
				items.add(entity);
			}
			itemCount++;
		}
		items.forEach(Entity::remove);
	}
}
