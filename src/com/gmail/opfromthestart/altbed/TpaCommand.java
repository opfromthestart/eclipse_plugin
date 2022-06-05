package com.gmail.opfromthestart.altbed;

import com.gmail.opfromthestart.PluginCommand;
import net.minecraft.util.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TpaCommand extends PluginCommand {
    public List<Tuple<UUID, UUID>> requests;

    public TpaCommand(Plugin plug) {
        super(plug);

        requests = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.getConfig().getBoolean("eclipseplugin.tpa.active"))
        {
            sender.sendMessage("Tpa is disabled.");
            return true;
        }
        if (args.length == 1)
        {
            Player from_player = Bukkit.getPlayer(sender.getName());
            Player to_player = Bukkit.getPlayer(args[0]);
            assert from_player != null;
            assert to_player != null;
            if (!to_player.isOnline())
            {
                sender.sendMessage("Player " + args[0] + " is not online.");
                return false;
            }
            Tuple<UUID, UUID> req = new Tuple<>(from_player.getUniqueId(), to_player.getUniqueId());
            requests.add(req);
            sender.sendMessage("Tpa request sent to " + args[0] + ". Request will time out in " +
                    plugin.getConfig().getInt("eclipseplugin.tpa.timeout")/1000.0 + " seconds.");
            to_player.sendMessage(sender.getName() + " wants to tpa. Do /tpy to accept or /tpn to cancel");
            (new Thread(() -> {
                try {
                    Thread.sleep(plugin.getConfig().getInt("eclipseplugin.tpa.timeout"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (requests.contains(req))
                {
                    requests.remove(req);
                    sender.sendMessage("Request timed out.");
                }
            })).start();
            return true;
        }
        return false;
    }
}
