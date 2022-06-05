package com.gmail.opfromthestart.altbed;

import com.comphenix.protocol.wrappers.Pair;
import com.gmail.opfromthestart.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class TpnCommand extends PluginCommand {
    public TpaCommand tpaCommand;

    public TpnCommand(Plugin plug, TpaCommand tpa) {
        super(plug);
        tpaCommand = tpa;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.getConfig().getBoolean("eclipseplugin.tpa.active"))
        {
            sender.sendMessage("Tpa is disabled.");
            return true;
        }
        if (args.length == 0)
        {
            Player player = Bukkit.getPlayer(sender.getName());
            assert player != null;
            Player toTp = null;
            Pair<UUID, UUID> last_req = null;
            for (Pair<UUID, UUID> req : tpaCommand.requests)
            {
                if (req.getSecond() == player.getUniqueId())
                {
                    toTp = Bukkit.getPlayer(req.getFirst());
                    last_req = req;
                }
            }
            if (toTp == null)
            {
                sender.sendMessage("No tpa requests found.");
                return false;
            }
            tpaCommand.requests.remove(last_req);
            sender.sendMessage("Teleport denied.");
            return true;
        }
        if (args.length == 1)
        {
            Player player = Bukkit.getPlayer(sender.getName());
            assert player != null;
            Player toTp = Bukkit.getPlayer(args[0]);
            assert toTp != null;
            Pair<UUID, UUID> last_req = null;
            for (Pair<UUID, UUID> req : tpaCommand.requests)
            {
                if (req.getSecond() == player.getUniqueId()) {
                    if (req.getFirst() == toTp.getUniqueId()) {
                        last_req = req;
                    }
                }
            }
            if (last_req == null)
            {
                sender.sendMessage("Tpa request not found.");
                return false;
            }
            tpaCommand.requests.remove(last_req);
            sender.sendMessage("Teleport denied");
            return true;
        }
        return false;
    }
}
