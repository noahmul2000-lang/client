package com.example.helperclient;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;

public class FlightModule {
    private static boolean enabled = false;

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if (player != null) {
                if (enabled) {
                    player.getAbilities().flying = true;
                    player.getAbilities().allowFlying = true;
                } else if (!player.isCreative() && !player.isSpectator()) {
                    player.getAbilities().flying = false;
                    player.getAbilities().allowFlying = false;
                }
            }
        });
    }
}
