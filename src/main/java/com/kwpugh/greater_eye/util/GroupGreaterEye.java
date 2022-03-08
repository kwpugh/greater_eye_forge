package com.kwpugh.greater_eye.util;

import com.kwpugh.greater_eye.init.ItemInit;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class GroupGreaterEye extends CreativeModeTab
{
	public GroupGreaterEye() 
	{
		super("greater_eye");
	}

	@Override
	public ItemStack makeIcon() 
	{
		return new ItemStack(ItemInit.GREATER_EYE.get());
	}	
}
