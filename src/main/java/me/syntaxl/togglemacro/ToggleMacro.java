package me.syntaxl.togglemacro;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

// The @Mod annotation tells Forge that this is a mod class.
// The modid must be unique and is used for identification. It should match archivesBaseName in build.gradle.
// The name is the human-readable name of the mod.
@Mod(modid = ToggleMacro.MODID, name = ToggleMacro.NAME, version = ToggleMacro.VERSION)
public class ToggleMacro {
    public static final String MODID = "togglemacro";
    public static final String NAME = "ToggleMacro";
    public static final String VERSION = "1.0";

    /**
     * This method is called by Forge during the pre-initialization phase.
     * It's a good place to register things like key bindings.
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // We call the register method from our KeyBindings class to set up the controls.
        KeyBindings.register();
    }

    /**
     * This method is called by Forge during the main initialization phase.
     * It's where we register our event handler so it can listen for game events.
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        // We create a new instance of our KeyInputHandler and register it on the Forge event bus.
        // This allows our handler to receive events like key presses and game ticks.
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }
}
