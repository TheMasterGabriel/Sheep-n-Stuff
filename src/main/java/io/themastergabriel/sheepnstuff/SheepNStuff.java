package io.themastergabriel.sheepnstuff;

import io.themastergabriel.sheepnstuff.common.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(name = SheepNStuff.NAME, modid = SheepNStuff.MODID, version = SheepNStuff.VERSION, acceptedMinecraftVersions = "[1.12.2]")
public class SheepNStuff
{
    public static final String MODID = "sheepnstuff";
    public static final String NAME = "Sheep 'n' Stuff";
    public static final String VERSION = "1.12.2-1.0.0.0";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @SidedProxy(clientSide = "io.themastergabriel.sheepnstuff.client.ClientProxy", serverSide = "io.themastergabriel.sheepnstuff.common.ServerProxy")
    public static ISidedProxy proxy;
    public static final CreativeTabs TAB = new CreativeTabs("sheepnstuff")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ModItems.LIVING_ROD);
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER.info("Handling Mod PreInitialization...");
        proxy.onPreInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LOGGER.info("Handling Mod Initialization...");
        proxy.onInit(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LOGGER.info("Handling Mod PostInitialization...");
        proxy.onPostInit(event);
    }
}