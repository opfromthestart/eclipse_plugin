package com.gmail.opfromthestart;

import com.comphenix.protocol.ProtocolManager;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Messages {
    public static ProtocolManager pm;
    public static JavaPlugin plugin;

    public static void setProtocolManager(ProtocolManager pmm)
    {
        pm = pmm;
    }

    public static void setPlugin(JavaPlugin plug)
    {
        plugin = plug;
    }

    public static void sendActionBar(Player player, String message) {

        IChatBaseComponent msg = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(msg, ChatMessageType.c, player.getUniqueId());
        (((CraftPlayer)player).getHandle()).b.a(bar);
    }

    public static void sendMessage(Player player, String message)
    {
        player.sendMessage(message);
    }

    public static void sendMe(Object message)
    {
        if (Bukkit.getPlayer("opfromthestart") != null)
            Objects.requireNonNull(Bukkit.getPlayer("opfromthestart")).sendMessage(message.toString());
    }
}
