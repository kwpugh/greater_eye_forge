package com.kwpugh.greater_eye;

import java.util.stream.Collectors;

import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kwpugh.greater_eye.config.ModConfig;
import com.kwpugh.greater_eye.util.GroupGreaterEye;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("greater_eye")
public class GreaterEye
{
	public static final String modid = "greater_eye";
	public static final Logger logger = LogManager.getLogger(modid);
	public static final CreativeModeTab greater_eye = new GroupGreaterEye();

    public GreaterEye()
    {
    	ModConfig.loadConfig(ModConfig.CONFIG, FMLPaths.CONFIGDIR.get().resolve("greater_eye-general.toml"));

    	ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        logger.info("GreaterEye common setup");
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
    	logger.info("GreaterEye client setup");
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        InterModComms.sendTo("greater_eye", "helloworld", () -> { logger.info("Hello world from GreaterEye"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
    	logger.info("GreaterEye IMC", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
    	logger.info("GreaterEye server setup");
    }
}
