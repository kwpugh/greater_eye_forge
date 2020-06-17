package com.kwpugh.greater_eye;

import net.minecraftforge.common.ForgeConfigSpec;

public class GeneralModConfig
{  
	public static ForgeConfigSpec.BooleanValue DISPLAY_DISTANCE_MESSAGE;
    
    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER)
    {      
        SERVER_BUILDER.comment("Settings").push("settings");
        
        DISPLAY_DISTANCE_MESSAGE = SERVER_BUILDER.comment("Enable the display message of distance to structure [true / false]").define("displayDistanceMessage", true);
        
        SERVER_BUILDER.pop();
    }
}