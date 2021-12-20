package com.kwpugh.greater_eye;

import com.kwpugh.greater_eye.items.ItemGreaterEye;
import com.kwpugh.greater_eye.items.ItemGreaterEyeEnd;
import com.kwpugh.greater_eye.items.ItemGreaterEyeNether;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GreaterEye.modid);
	
	public static final RegistryObject<Item> GREATER_EYE = ITEMS.register("greater_eye", () -> new ItemGreaterEye(new Item.Properties().stacksTo(1).tab(GreaterEye.greater_eye)));
	public static final RegistryObject<Item> GREATER_EYE_END = ITEMS.register("greater_eye_end", () -> new ItemGreaterEyeEnd(new Item.Properties().stacksTo(1).tab(GreaterEye.greater_eye)));
	public static final RegistryObject<Item> GREATER_EYE_NETHER = ITEMS.register("greater_eye_nether", () -> new ItemGreaterEyeNether(new Item.Properties().stacksTo(1).tab(GreaterEye.greater_eye)));
}
