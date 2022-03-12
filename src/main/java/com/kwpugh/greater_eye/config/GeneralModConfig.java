package com.kwpugh.greater_eye.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class GeneralModConfig
{
    public static ForgeConfigSpec.BooleanValue ENABLE_GRAVEYARDS;
    public static ForgeConfigSpec.BooleanValue ENABLE_DUNGEONS;

    public static ForgeConfigSpec.BooleanValue ENABLE_BUILDINGS;
    public static ForgeConfigSpec.BooleanValue ENABLE_BUILDINGS_NETHER;
    public static ForgeConfigSpec.BooleanValue ENABLE_BUILDINGS_END;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER)
    {      
        SERVER_BUILDER.comment("Settings").push("settings");

        ENABLE_GRAVEYARDS = SERVER_BUILDER.comment("Enable Graveyards type from a datapack [true / false]").define("enableGraveyard", false);
        ENABLE_DUNGEONS = SERVER_BUILDER.comment("Enable Dungeons type from a datapack [true / false]").define("enableDungeon", false);

        ENABLE_BUILDINGS = SERVER_BUILDER.comment("Enable Buildings type from a datapack [true / false]").define("enableBuildings", false);
        ENABLE_BUILDINGS_NETHER = SERVER_BUILDER.comment("Enable Buildings type from a datapack [true / false]").define("enableBuildingsNether", false);
        ENABLE_BUILDINGS_END = SERVER_BUILDER.comment("Enable Buildings type from a datapack [true / false]").define("enableBuildingsEnd", false);

        SERVER_BUILDER.pop();
    }
}