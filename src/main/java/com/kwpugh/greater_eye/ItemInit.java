package com.kwpugh.greater_eye;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, GreaterEye.modid);
	
	public static final RegistryObject<Item> GREATER_EYE = ITEMS.register("greater_eye", () -> new ItemGreaterEye(new Item.Properties().maxStackSize(16).group(GreaterEye.greater_eye)));
}
