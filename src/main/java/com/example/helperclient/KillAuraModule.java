package com.example.helperclient;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class KillAuraModule {
    private static boolean enabled = false;
    private static final double RANGE = 4.5; // Attack range in blocks

    public static boolean isEnabled() { return enabled; }

    public static void toggle() { enabled = !enabled; }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if (player == null || client.world == null || !enabled) return;

            // Check attack cooldown before swinging
            if (player.getAttackCooldownProgress(0.5f) < 1.0f) return;

            for (Entity entity : client.world.getEntities()) {
                if (entity instanceof LivingEntity && entity != player && entity.isAlive()) {
                    if (player.squaredDistanceTo(entity) <= RANGE * RANGE) {
                        client.interactionManager.attackEntity(player, entity);
                        player.swingHand(Hand.MAIN_HAND);
                        break;
                    }
                }
            }
        });
    }
}
