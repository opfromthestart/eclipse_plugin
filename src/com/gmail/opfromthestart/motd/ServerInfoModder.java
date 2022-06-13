package com.gmail.opfromthestart.motd;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.gmail.opfromthestart.TPS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ServerInfoModder extends PacketAdapter {
    Plugin plugin;
    TPS tps;

    public ServerInfoModder(Plugin plug, TPS t) {
        super(plug, ListenerPriority.HIGH, List.of(PacketType.Status.Server.SERVER_INFO),
                ListenerOptions.ASYNC);
        plugin = plug;
        tps = t;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Random r = new Random();

        WrappedServerPing ping = event.getPacket().getServerPings().read(0);

        List<String> motds = plugin.getConfig().getStringList("eclipseplugin.motd.motds");

        int maxPlayers = Bukkit.getMaxPlayers();
        int players = Bukkit.getOnlinePlayers().size();
        int available = maxPlayers - players;
        double curTps = Math.floor(tps.tps*10)/10;
        String randomPlayer = "noone";
        if (Bukkit.getOnlinePlayers().size() != 0)
            randomPlayer = ((Player)Bukkit.getOnlinePlayers().toArray()[r.nextInt(Bukkit.getOnlinePlayers().size())]).getDisplayName();

        if (motds.size() != 0) {
            String motd = motds.get(r.nextInt(motds.size()));

            motd = ChatColor.translateAlternateColorCodes('&', motd);

            motd = motd.replace("{{PLAYERS}}", String.valueOf(players));
            motd = motd.replace("{{ONLINEPLAYERS}}", String.valueOf(maxPlayers));
            motd = motd.replace("{{EMPTYSLOTS}}", String.valueOf(available));
            motd = motd.replace("{{TPS}}", String.valueOf(curTps));
            motd = motd.replace("{{RANDOMPLAYER}}", randomPlayer);

            ping.setMotD(motd);
        }

        List<String> iconNames = plugin.getConfig().getStringList("eclipseplugin.motd.icons");
        if (iconNames.size() != 0) {
            String iconName = iconNames.get(r.nextInt(iconNames.size()));
            try {
                ping.setFavicon(WrappedServerPing.CompressedImage.fromPng(ImageIO.read(new File(iconName))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //super.onPacketSending(event);
    }
}
