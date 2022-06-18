package com.gmail.opfromthestart.dupe;

import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class DupeCommand extends PluginCommand {

    public DupeCommand(Plugin plug) {
        super(plug);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 3) {
            if (Objects.equals(args[0], "donkey")) {
                if (Objects.equals(args[1], "active")) {
                    boolean val;
                    val = Boolean.parseBoolean(args[2]);

                    plugin.getConfig().set("eclipseplugin.dupe.donkey.active", val);
                    sender.sendMessage(val ? "Donkey dupe is active" : "Donkey dupe is disabled");
                    plugin.saveConfig();
                    return true;
                } else if (Objects.equals(args[1], "timeout")) {
                    float val;
                    val = Float.parseFloat(args[2]);

                    plugin.getConfig().set("eclipseplugin.dupe.donkey.timeout", (int) (val * 1000));
                    sender.sendMessage("Timeout set to " + (int) (val * 1000) + "ms.");
                    plugin.saveConfig();
                    return true;
                } else if (Objects.equals(args[1], "yield")) {
                    int val = Integer.parseInt(args[2]);

                    plugin.getConfig().set("eclipseplugin.dupe.donkey.yield", val);
                    sender.sendMessage("Yield set to " + val + " times.");
                    plugin.saveConfig();
                    return true;
                }
            } else if (Objects.equals(args[0], "itemframe")) {
                if (Objects.equals(args[1], "active")) {
                    boolean val;
                    val = Boolean.parseBoolean(args[2]);

                    plugin.getConfig().set("eclipseplugin.dupe.itemframe.active", val);
                    sender.sendMessage(val ? "Item frame dupe is active" : "Item frame dupe is disabled");
                    plugin.saveConfig();
                    return true;
                } else if (Objects.equals(args[1], "rate")) {
                    float val;
                    val = Float.parseFloat(args[2]);

                    plugin.getConfig().set("eclipseplugin.dupe.itemframe.rate", val);
                    sender.sendMessage("Dupe rate set to " + val);
                    plugin.saveConfig();
                    return true;
                }
            }
            sender.sendMessage("Invalid setting");
            return false;
        } else if (args.length == 2) {
            if (Objects.equals(args[0], "donkey")) {
                if (Objects.equals(args[1], "active")) {
                    sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.dupe.donkey.active")).toString());
                    return true;
                } else if (Objects.equals(args[1], "timeout")) {
                    sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.dupe.donkey.timeout")).toString());
                    return true;
                } else if (Objects.equals(args[1], "yield")) {
                    sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.dupe.donkey.yield")).toString());
                    return true;
                }
            } else if (Objects.equals(args[0], "itemframe")) {
                if (Objects.equals(args[1], "active")) {
                    sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.dupe.itemframe.active")).toString());
                    return true;
                } else if (Objects.equals(args[1], "rate")) {
                    sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.dupe.itemframe.rate")).toString());
                    return true;
                }
            } else if (Objects.equals(args[0], "itemlimit")) {
                int val;
                try {
                    val = Integer.parseInt(args[1]);
                } catch (NumberFormatException nfe) {
                    sender.sendMessage("Not a valid number");
                    return false;
                }
                plugin.getConfig().set("eclipseplugin.dupe.itemlimit", val);
                sender.sendMessage("Item limit set to " + val);
                plugin.saveConfig();
                return true;
            }
            sender.sendMessage("Invalid setting");
            return false;
        } else if (args.length == 1) {
            if (Objects.equals(args[0], "itemlimit")) {
                sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.dupe.itemlimit")).toString());
                return true;
            }
        }
        sender.sendMessage("Invalid arguments");
        return false;

    }
}