package io.themastergabriel.sheepnstuff.common.items;

import io.themastergabriel.sheepnstuff.SheepNStuff;
import io.themastergabriel.sheepnstuff.common.blocks.BlockHarvestedTreeLog;
import io.themastergabriel.sheepnstuff.common.blocks.BlockTreePlanks;
import io.themastergabriel.sheepnstuff.common.blocks.ModBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = SheepNStuff.MODID)
public class ModItems
{
    @GameRegistry.ObjectHolder("sheepnstuff:living_rod")
    public static final ItemMagical LIVING_ROD = null;

    @SubscribeEvent
    public static void onItemRegistration(RegistryEvent.Register<Item> event)
    {
        SheepNStuff.LOGGER.info("Registering Mod Items...");

        event.getRegistry().registerAll(new ItemMultiTexture(ModBlocks.HARVESTED_FRUIT_LOG, ModBlocks.HARVESTED_FRUIT_LOG, type -> BlockTreePlanks.EnumType.byMetadata(type.getMetadata()).getUnlocalizedName()).setRegistryName(ModBlocks.HARVESTED_FRUIT_LOG.getRegistryName()), new ItemLivingRod().setCreativeTab(SheepNStuff.TAB).setUnlocalizedName("living_rod").setRegistryName(SheepNStuff.MODID, "living_rod"));
    }

    @SubscribeEvent
    public static void onModelRegistration(ModelRegistryEvent event)
    {
        SheepNStuff.LOGGER.info("Registering Mod Item Models...");

        for(BlockTreePlanks.EnumType type : BlockHarvestedTreeLog.VARIANT.getAllowedValues())
        {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.HARVESTED_FRUIT_LOG), type.getMetadata(), new ModelResourceLocation(ModBlocks.HARVESTED_FRUIT_LOG.getRegistryName(), "axis=y,variant=" + type.getName()));
        }

        ModelLoader.setCustomModelResourceLocation(LIVING_ROD, 0, new ModelResourceLocation(LIVING_ROD.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(LIVING_ROD, -1, LIVING_ROD.getSecondaryResourceLocation());

        MinecraftForge.EVENT_BUS.unregister(ModItems.class);
    }
}