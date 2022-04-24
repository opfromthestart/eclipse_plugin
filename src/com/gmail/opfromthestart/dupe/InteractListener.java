package com.gmail.opfromthestart.dupe;

import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
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

	Map<String, Long> lastDupe = new HashMap<>();
	JavaPlugin plugin;

	public InteractListener(JavaPlugin plug)
	{
		plugin = plug;
	}

	@EventHandler
	public void onVehicleEnter(PlayerInteractAtEntityEvent event) {
		if (event.getRightClicked() instanceof Llama || event.getRightClicked() instanceof Mule || event.getRightClicked() instanceof Donkey) {
			ChestedHorse entity = (ChestedHorse) event.getRightClicked();
			if (entity.isCarryingChest()) {
				if ((event.getPlayer().getInventory().getItemInMainHand().getType() == Material.CHEST) || (event.getPlayer().getInventory().getItemInOffHand().getType() == Material.CHEST)) {
					event.setCancelled(true);
					if (event.getPlayer().hasPermission("eclipseplugin.dupe.use") && plugin.getConfig().getBoolean("eclipseplugin.dupe.active")) {
						purgeItems(event.getPlayer(), plugin.getConfig().getInt("eclipseplugin.dupe.itemlimit"));
						if (entity.getPassengers().size() == 0) {
							entity.addPassenger(event.getPlayer());
						}
						if (!lastDupe.containsKey(event.getPlayer().getName()))
						{
							lastDupe.put(event.getPlayer().getName(), 0L);
						}
						if (lastDupe.get(event.getPlayer().getName()) > System.currentTimeMillis() - plugin.getConfig().getInt("eclipseplugin.dupe.timeout")) {
							long timeleft = (-System.currentTimeMillis() + plugin.getConfig().getInt("eclipseplugin.dupe.timeout") + lastDupe.get(event.getPlayer().getName())) / 1000;
							sendMessage(event.getPlayer(), "§7You can't dupe for another §6" + timeleft + " §7seconds.");
							return;
						}
						for (ItemStack item : entity.getInventory().getContents()) {
							if (item != null) {
								entity.getWorld().dropItemNaturally(entity.getLocation(), item);
								entity.getWorld().dropItemNaturally(entity.getLocation(), item.clone());
							}
						}
						entity.setCarryingChest(false);
						//sendMessage(event.getPlayer(), "&cChests on llamas, donkeys, mules, Etc is currently disabled, this is to facilitate the SalC1 TreeMC dupe");
						lastDupe.put(event.getPlayer().getName(), System.currentTimeMillis());
					} else {
						sendMessage(event.getPlayer(), "&6You do not have permission to dupe");
					}
				}
			}
		}
	}

	private void sendMessage(Player player, String message) {
		IChatBaseComponent msg = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(msg, ChatMessageType.c, player.getUniqueId());
		(((CraftPlayer)player).getHandle()).b.a(bar);
	}

	private void purgeItems(Player player, int limit)
	{
		ArrayList<Entity> items = new ArrayList<>();
		for (Entity entity : Objects.requireNonNull(player.getLocation().getWorld()).getNearbyEntities(player.getBoundingBox().expand(60, 60, 60))) {
			if (entity.getType() == EntityType.DROPPED_ITEM) {
				items.add(entity);
			}
		}
		if (items.size() > limit)
		{
			int toRem = items.size()-limit;
			for (int i=0;i<toRem;i++)
			{
				items.get(limit).remove();
				items.remove(limit);
			}
		}
	}
}
