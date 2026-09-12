package com.example.helperclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class HelperClientModClient implements ClientModInitializer {

    private static KeyBinding guiKey;

    @Override
    public void onInitializeClient() {
        // Right Shift keybind for ClickGUI
        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.vanguard.gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.vanguard.client"
        ));

        // Register module event listeners
        FlightModule.register();
        EspModule.register();
        KillAuraModule.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (guiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new ClickGuiScreen());
                }
            }
        });
    }
}
