package com.example.helperclient;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {

    public ClickGuiScreen() {
        super(Text.literal("Vanguard Client Menu"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int dashboardWidth = 620;
        int dashboardHeight = 360;
        int x = (this.width - dashboardWidth) / 2;
        int y = (this.height - dashboardHeight) / 2;
        int sidebarWidth = 140;

        // Main Background Panel & Sidebar
        context.fill(x, y, x + dashboardWidth, y + dashboardHeight, 0xFF0D0D11);
        context.fill(x, y, x + sidebarWidth, y + dashboardHeight, 0xFF14141A);

        // Sidebar Text
        context.drawText(this.textRenderer, "VANGUARD", x + 18, y + 18, 0xFF00C6FF, true);
        context.drawText(this.textRenderer, "Mods", x + 18, y + 55, 0xFFFFFFFF, false);
        context.drawText(this.textRenderer, "Macros", x + 18, y + 80, 0xFF777788, false);
        context.drawText(this.textRenderer, "Cosmetics", x + 18, y + 105, 0xFF777788, false);
        context.drawText(this.textRenderer, "Edit HUD", x + 18, y + 130, 0xFF777788, false);

        // Header Title
        int contentX = x + sidebarWidth + 20;
        context.drawText(this.textRenderer, "Mods", contentX, y + 18, 0xFFFFFFFF, true);

        // Category Pills
        drawPill(context, contentX, y + 42, 45, 18, "ALL", true);
        drawPill(context, contentX + 52, y + 42, 60, 18, "COMBAT", false);
        drawPill(context, contentX + 118, y + 42, 60, 18, "RENDER", false);

        // Grid Layout for Cards
        int cardWidth = 135;
        int cardHeight = 80;
        int gridStartX = contentX;
        int gridStartY = y + 75;

        // Row 1 Cards
        drawModuleCard(context, gridStartX, gridStartY, cardWidth, cardHeight, "Flight", "Toggle steady motion flight", FlightModule.isEnabled(), mouseX, mouseY);
        drawModuleCard(context, gridStartX + cardWidth + 12, gridStartY, cardWidth, cardHeight, "Entity ESP", "Glow entity outlines through walls", EspModule.isEnabled(), mouseX, mouseY);
        drawModuleCard(context, gridStartX + (cardWidth + 12) * 2, gridStartY, cardWidth, cardHeight, "KillAura", "Automatically attack nearby mobs", KillAuraModule.isEnabled(), mouseX, mouseY);

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawModuleCard(DrawContext context, int x, int y, int w, int h, String title, String desc, boolean enabled, int mouseX, int mouseY) {
        int bgColor = enabled ? 0xFF14241C : 0xFF181820;
        int borderColor = enabled ? 0xFF22B15D : 0xFF2A2A35;

        context.fill(x, y, x + w, y + h, bgColor);

        // Card Outline
        context.fill(x, y, x + w, y + 1, borderColor);
        context.fill(x, y + h - 1, x + w, y + h, borderColor);
        context.fill(x, y, x + 1, y + h, borderColor);
        context.fill(x + w - 1, y, x + w, y + h, borderColor);

        context.drawText(this.textRenderer, title, x + 12, y + 14, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, desc, x + 12, y + 32, 0xFF888899, false);

        // Toggle Switch
        int switchX = x + w - 30;
        int switchY = y + 12;
        int switchBg = enabled ? 0xFF22B15D : 0xFF353545;
        
        context.fill(switchX, switchY, switchX + 20, switchY + 10, switchBg);
        int knobX = enabled ? switchX + 11 : switchX + 1;
        context.fill(knobX, switchY + 1, knobX + 8, switchY + 9, 0xFFFFFFFF);
    }

    private void drawPill(DrawContext context, int x, int y, int w, int h, String text, boolean active) {
        int color = active ? 0xFFFFFFFF : 0xFF22222C;
        int textColor = active ? 0xFF000000 : 0xFFAAAAAA;

        context.fill(x, y, x + w, y + h, color);
        context.drawText(this.textRenderer, text, x + 8, y + 5, textColor, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int dashboardWidth = 620;
            int dashboardHeight = 360;
            int x = (this.width - dashboardWidth) / 2;
            int y = (this.height - dashboardHeight) / 2;
            int gridStartX = x + 160;
            int gridStartY = y + 75;

            // Flight Toggle Click
            if (mouseX >= gridStartX && mouseX <= gridStartX + 135 && mouseY >= gridStartY && mouseY <= gridStartY + 80) {
                FlightModule.toggle();
                return true;
            }

            // ESP Toggle Click
            if (mouseX >= gridStartX + 147 && mouseX <= gridStartX + 282 && mouseY >= gridStartY && mouseY <= gridStartY + 80) {
                EspModule.toggle();
                return true;
            }

            // KillAura Toggle Click
            if (mouseX >= gridStartX + 294 && mouseX <= gridStartX + 429 && mouseY >= gridStartY && mouseY <= gridStartY + 80) {
                KillAuraModule.toggle();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
