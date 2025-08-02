package me.syntaxl.togglemacro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyInputHandler {

    private boolean toggleAttackActive = false;
    private boolean toggleUseActive = false;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        // Handle custom toggle key presses
        if (KeyBindings.toggleAttack.isPressed()) {
            toggleAttackActive = !toggleAttackActive;
            // Immediately release the vanilla key if we just toggled OFF
            // This prevents the vanilla key from being stuck "on" from our mod's forcing.
            if (!toggleAttackActive) {
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);
            }
            // Add a chat message for feedback (optional, but good for debugging)
            // String msg = toggleAttackActive ? "Toggle Attack: ON" : "Toggle Attack: OFF";
            // Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
        }

        if (KeyBindings.toggleUse.isPressed()) {
            toggleUseActive = !toggleUseActive;
            // Immediately release the vanilla key if we just toggled OFF
            if (!toggleUseActive) {
                KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), false);
            }
            // String msg = toggleUseActive ? "Toggle Use: ON" : "Toggle Use: OFF";
            // Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        KeyBinding attackKeyBind = mc.gameSettings.keyBindAttack;
        KeyBinding useKeyBind = mc.gameSettings.keyBindUseItem;

        // Essential check: Ensure the game settings and keybinds are loaded.
        // This prevents crashes during very early game startup.
        if (attackKeyBind == null || useKeyBind == null) {
            return;
        }

        // --- Handle Toggle Attack ---
        if (toggleAttackActive) {
            // If the toggle is active, always force the attack key to be "pressed".
            KeyBinding.setKeyBindState(attackKeyBind.getKeyCode(), true);
        }
        // IMPORTANT: NO 'ELSE' BLOCK HERE for KeyBinding.setKeyBindState(false)
        // If toggleAttackActive is false, we let Minecraft's normal input loop
        // (which runs before this tick event) handle the state of the physical mouse button.
        // We only explicitly release it when the toggle is turned OFF (in onKeyInput)
        // or when entering a GUI.

        // --- Handle Toggle Use ---
        if (toggleUseActive) {
            // If the toggle is active, always force the use key to be "pressed".
            KeyBinding.setKeyBindState(useKeyBind.getKeyCode(), true);
        }
        // IMPORTANT: NO 'ELSE' BLOCK HERE for KeyBinding.setKeyBindState(false)
        // Same logic as for attackKeyBind.

        // GUI Check: If we enter a GUI, disable toggles and release keys
        // This should always be the last part of your tick handler to ensure
        // keys are properly released when exiting gameplay.
        if (mc.currentScreen != null) {
            if (toggleAttackActive) {
                toggleAttackActive = false;
                KeyBinding.setKeyBindState(attackKeyBind.getKeyCode(), false); // Ensure key is truly released
            }
            if (toggleUseActive) {
                toggleUseActive = false;
                KeyBinding.setKeyBindState(useKeyBind.getKeyCode(), false); // Ensure key is truly released
            }
        }
    }
}