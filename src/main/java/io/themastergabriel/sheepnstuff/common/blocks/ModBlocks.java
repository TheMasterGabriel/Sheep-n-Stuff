package io.themastergabriel.sheepnstuff.common.blocks;

import io.themastergabriel.sheepnstuff.SheepNStuff;
import net.minecraft.block.Block;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Mod.EventBusSubscriber(modid = SheepNStuff.MODID)
public class ModBlocks
{
    @ObjectHolder("sheepnstuff:fruitlog")
    public static final Block FRUIT_LOG = null;

    @ObjectHolder("sheepnstuff:harvested_fruitlog")
    public static final Block HARVESTED_FRUIT_LOG = null;

    @ObjectHolder("sheepnstuff:fruitleaves")
    public static final Block FRUIT_LEAVES = null;

    @SubscribeEvent
    public static void onBlockRegistration(RegistryEvent.Register<Block> event)
    {
        SheepNStuff.LOGGER.info("Registering Mod Blocks...");
        event.getRegistry().registerAll(new BlockTreeLog().setUnlocalizedName("fruitlog").setRegistryName(SheepNStuff.MODID, "fruitlog"), new BlockHarvestedTreeLog().setUnlocalizedName("harvested_fruitlog").setCreativeTab(SheepNStuff.TAB).setRegistryName(SheepNStuff.MODID, "harvested_fruitlog"));
    }

    @SubscribeEvent
    public static void onModelRegistration(ModelRegistryEvent event)
    {
        SheepNStuff.LOGGER.info("Registering Mod Block Models...");

        MinecraftForge.EVENT_BUS.unregister(ModBlocks.class);
    }
}