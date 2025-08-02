package me.syntaxl.togglemacro;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * This class is responsible for creating and registering the custom key bindings for the mod.
 */
public class KeyBindings {

    // A custom category for our controls in the Controls menu.
    public static final String KEY_CATEGORY = "Toggle Controls";

    // The KeyBinding objects themselves. These will be accessible from anywhere in our mod.
    public static KeyBinding toggleAttack;
    public static KeyBinding toggleUse;

    /**
     * This method initializes the KeyBinding objects and registers them with the game.
     */
    public static void register() {
        // Create a new key binding for toggling attack.
        // "key.toggleAttack" is the unlocalized name, used for language files.
        // Keyboard.KEY_NONE means it's not bound to any key by default.
        // KEY_CATEGORY places it in our custom category in the controls menu.
        toggleAttack = new KeyBinding("key.toggleAttack", Keyboard.KEY_NONE, KEY_CATEGORY);
        toggleUse = new KeyBinding("key.toggleUse", Keyboard.KEY_NONE, KEY_CATEGORY);

        // Register the key bindings with Forge's ClientRegistry.
        // This makes them appear in the game's controls menu.
        ClientRegistry.registerKeyBinding(toggleAttack);
        ClientRegistry.registerKeyBinding(toggleUse);
    }
}
