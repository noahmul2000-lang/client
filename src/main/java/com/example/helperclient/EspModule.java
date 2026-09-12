package com.example.helperclient;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;

public class EspModule {
    private static boolean enabled = false;

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null) return;

            for (Entity entity : client.world.getEntities()) {
                entity.setGlowing(enabled);
            }
        });
    }
}
