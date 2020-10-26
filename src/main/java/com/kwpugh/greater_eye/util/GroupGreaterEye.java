package com.kwpugh.greater_eye.util;

import com.kwpugh.greater_eye.ItemInit;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class GroupGreaterEye extends ItemGroup
{
	public GroupGreaterEye() 
	{
		super("greater_eye");
	}

	@Override
	public ItemStack createIcon() 
	{
		return new ItemStack(ItemInit.GREATER_EYE.get());
	}	
}
