package com.gmail.opfromthestart.altbed;

import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class HomeCommand extends PluginCommand {

    public Map<UUID, Map<String, Location>> homes;
    // TODO add combat detector

    public HomeCommand(Plugin plug) {
        super(plug);
        homes = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==2)
        {
            if (Objects.equals(args[0], "tp"))
            {
                Player player = Bukkit.getPlayer(sender.getName());
                assert player != null;
                if (homes.containsKey(player.getUniqueId()) && homes.get(player.getUniqueId()).containsKey(args[1]))
                {
                    Location startLoc = player.getLocation().clone();

                    Thread thread = new Thread(()-> {
                        try {
                            Thread.sleep(plugin.getConfig().getInt("eclipseplugin.home.tptime"));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (player.getLocation().distance(startLoc) < 2) {
                            Bukkit.getScheduler().callSyncMethod(plugin, ()->{
                                player.teleport(homes.get(player.getUniqueId()).get(args[1]));
                                return null;
                            });
                            //plugin.getLogger().info(homes.get(player.getUniqueId()).get(args[1]).toString());
                        }
                        else
                        {
                            sender.sendMessage("Teleport was cancelled");
                        }
                    });
                    thread.start();
                    sender.sendMessage("Teleporting to home in " + plugin.getConfig().getInt("eclipseplugin.home.tptime")/1000.0 + " seconds.");
                    return true;
                }
                sender.sendMessage("Cannot find home with name: " + args[1]);
                return false;
            }
            else if (Objects.equals(args[0], "add"))
            {
                Player player = Bukkit.getPlayer(sender.getName());
                assert player != null;
                if (!homes.containsKey(player.getUniqueId()))
                    homes.put(player.getUniqueId(), new HashMap<>());
                Map<String, Location> phomes = homes.get(player.getUniqueId());
                if (phomes.keySet().size() >= plugin.getConfig().getInt("eclipseplugin.home.maxhomes"))
                {
                    sender.sendMessage("You already have " + plugin.getConfig().getInt("eclipseplugin.home.maxhomes") + " homes.");
                    return false;
                }
                if (phomes.containsKey(args[1]))
                {
                    sender.sendMessage("You already have a home named " + args[1]);
                    return true;
                }
                phomes.put(args[1], player.getLocation());
                sender.sendMessage("Home "+args[1] + " added.");
                return true;
            }
            else if (Objects.equals(args[0], "delete"))
            {
                Player player = Bukkit.getPlayer(sender.getName());
                assert player != null;
                if (!homes.containsKey(player.getUniqueId()))
                    homes.put(player.getUniqueId(), new HashMap<>());
                Map<String, Location> phomes = homes.get(player.getUniqueId());
                if (phomes.containsKey(args[1]))
                {
                    phomes.remove(args[1]);
                    sender.sendMessage("Home " +args[1] + " deleted.");
                    return true;
                }
                sender.sendMessage("Home "+args[1] + " not found.");
                return false;
            }
        }
        else if (args.length == 1)
        {
            if (Objects.equals(args[0], "list"))
            {
                Player player = Bukkit.getPlayer(sender.getName());
                assert player != null;
                if (homes.containsKey(player.getUniqueId()) && homes.get(player.getUniqueId()).size() != 0)
                {
                    StringBuilder message = new StringBuilder("Your homes are: ");
                    for (String name : homes.get(player.getUniqueId()).keySet())
                    {
                        message.append(name).append(", ");
                    }
                    sender.sendMessage(message.substring(0, message.length()-2) + ".");
                    return true;
                }
                sender.sendMessage("You have no homes.");
                return true;
            }
        }
        return false;
    }

    public void saveHomes() throws IOException {
        File file = new File("homes.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (UUID player : homes.keySet())
        {
            Map<String, Location> phomes = homes.get(player);
            int i=0;
            for (String name : phomes.keySet())
            {
                if (i>=plugin.getConfig().getInt("eclipseplugin.home.maxhomes"))
                    break;
                config.set(player.toString()+"."+i+".name", name);
                config.set(player +"."+i+".loc", phomes.get(name));
                //phomes.put(config.getString(player+'.'+i+".name"), config.getLocation(player+"."+i+".loc"));
                i += 1;
            }
            for (;i<plugin.getConfig().getInt("eclipseplugin.home.maxhomes");i++)
                config.set(player.toString()+"."+i+".name", "");
        }
        config.save(file);
    }

    public void loadHomes()
    {
        if (!Files.exists(Paths.get("homes.yml")))
            return;
        File file = new File("homes.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        Set<String> players = config.getKeys(false);
        for (String player : players)
        {
            homes.put(UUID.fromString(player), new HashMap<>());
            Map<String, Location> phomes = homes.get(UUID.fromString(player));
            for (int i=0; i<plugin.getConfig().getInt("eclipseplugin.home.maxhomes"); i++)
            {
                if (Objects.equals(config.getString(player + '.' + i + ".name"), ""))
                    continue;
                phomes.put(config.getString(player+'.'+i+".name"), config.getLocation(player+"."+i+".loc"));
            }
        }
    }
}
