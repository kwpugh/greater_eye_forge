package com.kwpugh.greater_eye.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GeneralModConfig
{
    public static ForgeConfigSpec.BooleanValue ENABLE_CUSTOM1;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER)
    {      
        SERVER_BUILDER.comment("Settings").push("settings");

        ENABLE_CUSTOM1 = SERVER_BUILDER.comment("Enable Custom1 type from a datapack [true / false]").define("enableCustom1", false);
        
        SERVER_BUILDER.pop();
    }
}