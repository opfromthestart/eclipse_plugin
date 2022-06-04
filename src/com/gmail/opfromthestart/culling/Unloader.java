package com.gmail.opfromthestart.culling;

import com.gmail.opfromthestart.PluginListener;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

public abstract class Unloader extends PluginListener {

    public Unloader(Plugin plug) {
        super(plug);
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent cue)
    {
        if (! willSave(cue.getChunk()))
            cue.setSaveChunk(false);
    }

    public abstract boolean willSave(Chunk chunk);
}
