package com.agustheking.smartreplaceitem;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.event.events.player.PlayerMouseButtonEvent;
import com.hypixel.hytale.server.core.event.events.entity.LivingEntityInventoryChangeEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.agustheking.smartreplaceitem.listener.PlayerListener;
import com.agustheking.smartreplaceitem.listener.InventoryListener;
import com.agustheking.smartreplaceitem.listener.ConnectionListener;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import java.util.logging.Level;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import javax.annotation.Nonnull;

public class SmartReplaceItem extends JavaPlugin {
    
    public final Map<UUID, ItemStack> lastHeldItems = new HashMap<>();

    public SmartReplaceItem(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        // Register the listeners
        this.getEventRegistry().registerGlobal(PlayerMouseButtonEvent.class, new PlayerListener(this));
        this.getEventRegistry().registerGlobal(LivingEntityInventoryChangeEvent.class, new InventoryListener(this));
        this.getEventRegistry().registerGlobal(PlayerDisconnectEvent.class, new ConnectionListener(this));
        
        getLogger().at(Level.INFO).log("SmartReplaceItem has been set up successfully!");
    }

    @Override
    protected void start() {
        getLogger().at(Level.INFO).log("SmartReplaceItem started!");
    }

    @Override
    protected void shutdown() {
        getLogger().at(Level.INFO).log("SmartReplaceItem shutting down.");
    }
}
