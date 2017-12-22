package io.themastergabriel.sheepnstuff;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface ISidedProxy
{
    /**
     * A method fired during your mod's PreInitialization phase. Typically used to register your mod's tile entities, entities, or OreDictionary entries, etc.
     * For more information and typical use-cases, see {@link net.minecraftforge.fml.common.event.FMLPreInitializationEvent}
     *
     * @see http://mcforge.readthedocs.io/en/latest/conventions/loadstages/#pre-initialization
     */
    void onPreInit(FMLPreInitializationEvent event);

    /**
     * A method fired during your mod's Initialization phase. Typically used to register your mod's world generators, recipes, or event handlers, etc
     * For more information and typical use-cases, see {@link net.minecraftforge.fml.common.event.FMLInitializationEvent}
     *
     * @see http://mcforge.readthedocs.io/en/latest/conventions/loadstages/#initialization
     */
    void onInit(FMLInitializationEvent event);

    /**
     * A method fired during your mod's PostInitialization phase. Although not typically used, it is useful in registering/doing anything else requried for your
     * mod to function, such as checking for compatibility with other mods.
     * For more information and typical use-cases, see {@link net.minecraftforge.fml.common.event.FMLPostInitializationEvent}
     *
     * @see http://mcforge.readthedocs.io/en/latest/conventions/loadstages/#post-initialization
     */
    void onPostInit(FMLPostInitializationEvent event);
}