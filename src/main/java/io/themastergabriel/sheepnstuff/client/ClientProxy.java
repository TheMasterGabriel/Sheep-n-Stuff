package io.themastergabriel.sheepnstuff.client;

import io.themastergabriel.sheepnstuff.ISidedProxy;
import io.themastergabriel.sheepnstuff.SheepNStuff;
import io.themastergabriel.sheepnstuff.common.blocks.ModBlocks;
import io.themastergabriel.sheepnstuff.common.items.ItemMagical;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements ISidedProxy
{
    @Override
    public void onPreInit(FMLPreInitializationEvent event) { }

    @Override
    public void onInit(FMLInitializationEvent event)
    {
        SheepNStuff.LOGGER.info("Registering Block Colors...");
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic(), ModBlocks.FRUIT_LOG);

        SheepNStuff.LOGGER.info("Creating Magical Font Renderer...");
        ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(ItemMagical.createMagicalFontRenderer());
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) { }
}