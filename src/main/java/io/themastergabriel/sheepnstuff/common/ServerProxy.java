package io.themastergabriel.sheepnstuff.common;

import io.themastergabriel.sheepnstuff.ISidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements ISidedProxy
{
    @Override
    public void onPreInit(FMLPreInitializationEvent event) { }

    @Override
    public void onInit(FMLInitializationEvent event) { }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) { }
}