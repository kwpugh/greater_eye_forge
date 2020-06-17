package com.kwpugh.greater_eye;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EyeOfEnderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGreaterEye extends Item
{
	String structureType = "Village";	

	public ItemGreaterEye(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
      
		playerIn.setActiveHand(handIn);
		
		if((worldIn instanceof ServerWorld) && (playerIn.isSneaking()) && (worldIn.getDimension().getType() == DimensionType.OVERWORLD))   //shift right-click changes structure type to locate
		{   
			if(structureType == "Village")
			{
				structureType = "Mineshaft";	  
			}
			else if(structureType == "Mineshaft")
			{
				structureType = "Shipwreck";
			}
			else if(structureType == "Shipwreck")
			{
				structureType = "Pillager_Outpost"; 
			}
			else if(structureType == "Pillager_Outpost")
			{
				structureType = "Monument";	  
			}
			else if(structureType == "Monument")
			{
				structureType = "Mansion";  
			}
			else if(structureType == "Mansion")
			{
				structureType = "Desert_Pyramid";  
			}
			else if(structureType == "Desert_Pyramid")
			{
				structureType = "Jungle_Pyramid";  
			}
			else if(structureType == "Jungle_Pyramid")
			{
				structureType = "Stronghold";  
			}			
			else if(structureType == "Stronghold")
			{
				structureType = "Village";  
			}
			
			playerIn.sendMessage((new TranslationTextComponent("item.greater_eye.greater_eye.message1", structureType).applyTextStyle(TextFormatting.LIGHT_PURPLE)));
		  
			return ActionResult.resultSuccess(itemstack);
		}
			
		if(!playerIn.isSneaking())   //simple right-click executes
		{					
			if((worldIn instanceof ServerWorld) && (worldIn.getDimension().getType() == DimensionType.OVERWORLD))
			{
				findStructureAndShoot(worldIn, playerIn, itemstack, structureType);
				
				return ActionResult.resultSuccess(itemstack);
			}
		}
		
        return ActionResult.resultSuccess(itemstack);
	}  
	
	private static void findStructureAndShoot(World worldIn, PlayerEntity playerIn, ItemStack itemstack, String structureType)
	{
		boolean displayMessage = GeneralModConfig.DISPLAY_DISTANCE_MESSAGE.get();
		
		// A structure will always be found, no matter how far away
		BlockPos playerpos = playerIn.getPosition();
		BlockPos locpos = ((ServerWorld)worldIn).getChunkProvider().getChunkGenerator().findNearestStructure(worldIn, structureType, new BlockPos(playerIn), 100, false);
		
		int structureDistance = MathHelper.floor(getDistance(playerpos.getX(), playerpos.getZ(), locpos.getX(), locpos.getZ()));
		
		if(displayMessage)
		{
			playerIn.sendMessage(new TranslationTextComponent("item.greater_eye.greater_eye.message3", structureType, structureDistance).applyTextStyle(TextFormatting.LIGHT_PURPLE));	
		}
	
		EyeOfEnderEntity finderentity = new EyeOfEnderEntity(worldIn, playerIn.getPosX(), playerIn.getPosYHeight(0.5D), playerIn.getPosZ());
		finderentity.func_213863_b(itemstack);
		finderentity.moveTowards(locpos);
		worldIn.addEntity(finderentity);

		if (playerIn instanceof ServerPlayerEntity)
		{
			CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayerEntity)playerIn, locpos);
		}

		worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		worldIn.playEvent((PlayerEntity)null, 1003, new BlockPos(playerIn), 0);

		if (!playerIn.abilities.isCreativeMode)
		{
			itemstack.shrink(1);					
		}
		
		return;
	}
	
	private static float getDistance(int x1, int z1, int x2, int z2)
	{
		int i = x2 - x1;
		int j = z2 - z1;
	  
		return MathHelper.sqrt((float)(i * i + j * j));
	}
	
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add((new TranslationTextComponent("item.greater_eye.greater_eye.line1").applyTextStyle(TextFormatting.GREEN)));
		tooltip.add((new TranslationTextComponent("item.greater_eye.greater_eye.line2").applyTextStyle(TextFormatting.YELLOW)));
		tooltip.add((new TranslationTextComponent("item.greater_eye.greater_eye.message2", structureType).applyTextStyle(TextFormatting.LIGHT_PURPLE)));
	}	   
}
