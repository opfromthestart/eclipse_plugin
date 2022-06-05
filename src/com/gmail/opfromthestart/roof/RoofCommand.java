package com.gmail.opfromthestart.roof;

import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class RoofCommand extends PluginCommand {

    public RoofCommand(Plugin plug) {
        super(plug);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2)
        {
            if (Objects.equals(args[0], "offtps")) {
                int val;
                try {
                    val = Integer.parseInt(args[1]);
                } catch (NumberFormatException nfe) {
                    sender.sendMessage("Not a valid number");
                    return false;
                }
                plugin.getConfig().set("eclipseplugin.roof.offtps", val);
                sender.sendMessage("Roof will be unusable under " + val + " TPS");
                plugin.saveConfig();
                return true;
            }
            else if (Objects.equals(args[0], "lowtps")) {
                int val;
                try {
                    val = Integer.parseInt(args[1]);
                } catch (NumberFormatException nfe) {
                    sender.sendMessage("Not a valid number");
                    return false;
                }
                plugin.getConfig().set("eclipseplugin.roof.low", val);
                sender.sendMessage("Roof travel will be slowed under " + val + " TPS");
                plugin.saveConfig();
                return true;
            }
            else if (Objects.equals(args[0], "killifabove"))
            {
                boolean val;
                val = Boolean.parseBoolean(args[1]);

                plugin.getConfig().set("eclipseplugin.roof.killifabove", val);
                sender.sendMessage(val ? "Will kill if above roof" : "Will tp down if above roof");
                plugin.saveConfig();
                return true;
            }
            else if (Objects.equals(args[0], "speed"))
            {
                float val;
                val = Float.parseFloat(args[1]);

                plugin.getConfig().set("eclipseplugin.roof.speed", val);
                sender.sendMessage("Max speed set to " + val + " BPS.");
                plugin.saveConfig();
                return true;
            }
            else if (Objects.equals(args[0], "lowspeed"))
            {
                float val;
                val = Float.parseFloat(args[1]);

                plugin.getConfig().set("eclipseplugin.roof.lowspeed", val);
                sender.sendMessage("Max speed during low tps set to " + val + " BPS.");
                plugin.saveConfig();
                return true;
            }
            sender.sendMessage("Invalid setting");
            return false;
        }
        else if (args.length==1)
        {
            if (Objects.equals(args[0], "speed")) {
                sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.roof.speed")).toString());
                return true;
            }
            else if (Objects.equals(args[0], "lowspeed"))
            {
                sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.roof.lowspeed")).toString());
                return true;
            }
            else if (Objects.equals(args[0], "killifabove"))
            {
                sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.roof.killifabove")).toString());
                return true;
            }
            else if (Objects.equals(args[0], "lowtps"))
            {
                sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.roof.lowtps")).toString());
                return true;
            }
            else if (Objects.equals(args[0], "offtps"))
            {
                sender.sendMessage(Objects.requireNonNull(plugin.getConfig().get("eclipseplugin.roof.offtps")).toString());
                return true;
            }
            sender.sendMessage("Invalid setting");
            return false;
        }
        sender.sendMessage("Invalid arguments");
        return false;
    }
}
