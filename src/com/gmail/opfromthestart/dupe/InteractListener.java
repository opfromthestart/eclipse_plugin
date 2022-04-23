package com.gmail.opfromthestart.dupe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InteractListener implements Listener {

	Map<String, Long> cooldown = new HashMap<>();
	JavaPlugin plugin;

	public InteractListener(JavaPlugin plug)
	{
		plugin = plug;
	}

	@EventHandler
	public void onVehicleEnter(PlayerInteractAtEntityEvent event) {
		if (event.getRightClicked() instanceof Llama || event.getRightClicked() instanceof Mule || event.getRightClicked() instanceof Donkey) {
			if ((event.getPlayer().getInventory().getItemInMainHand().getType() == Material.CHEST) || (event.getPlayer().getInventory().getItemInOffHand().getType() == Material.CHEST)) {
				if (event.getPlayer().hasPermission("dupe.use") && plugin.getConfig().getBoolean("eclipseplugin.dupe.active")) {
					ChestedHorse entity = (ChestedHorse) event.getRightClicked();
					purgeItems(event.getPlayer(), plugin.getConfig().getInt("eclipseplugin.dupe.itemlimit"));
					if (entity.getPassengers().size() == 0) {
						entity.addPassenger(event.getPlayer());
					}
					for (ItemStack item : entity.getInventory().getContents()) {
						if (item != null) {
							if (!(item.getType() == Material.SADDLE)) {
								if (cooldown.get(event.getPlayer().getName()) > System.currentTimeMillis()) {
									long timeleft = (cooldown.get(event.getPlayer().getName()) - System.currentTimeMillis()) / 1000;
									sendMessage(event.getPlayer(), "§cYou can't dupe for another §f" + timeleft + " §cseconds.");
									return;
								}
								entity.getWorld().dropItemNaturally(entity.getLocation(), item);
								entity.getWorld().dropItemNaturally(entity.getLocation(), item.clone());
							}
						}
					}
					entity.setCarryingChest(false);
					//sendMessage(event.getPlayer(), "&cChests on llamas, donkeys, mules, Etc is currently disabled, this is to facilitate the SalC1 TreeMC dupe");
					cooldown.put(event.getPlayer().getName(), System.currentTimeMillis() + 15 * 1000);
				}
				else {
					sendMessage(event.getPlayer(), "&6You do not have permission to dupe");
				}
			}
		}
	}

	private void sendMessage(Player player, String message) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	private void purgeItems(Player player, int limit)
	{
		ArrayList<Entity> items = new ArrayList<>();
		for (Entity entity : Objects.requireNonNull(player.getLocation().getWorld()).getNearbyEntities(player.getBoundingBox().expand(60, 60, 60))) {
			if (entity.getType() == EntityType.DROPPED_ITEM) {
				items.add(entity);
			}
		}
		while (items.size() > limit) {
			items.get(items.size() - 1).remove();
			items.remove(items.size() -1);
		}
	}
}
