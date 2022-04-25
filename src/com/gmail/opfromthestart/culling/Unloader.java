package com.gmail.opfromthestart.culling;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

public abstract class Unloader implements Listener {

    @EventHandler
    public void onUnload(ChunkUnloadEvent cue)
    {
        if (! willSave(cue.getChunk()))
            cue.setSaveChunk(false);
    }

    public abstract boolean willSave(Chunk chunk);
}
