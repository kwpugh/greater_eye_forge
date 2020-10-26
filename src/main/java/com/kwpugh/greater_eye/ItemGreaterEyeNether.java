package com.kwpugh.greater_eye;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.EyeOfEnderEntity;
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
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGreaterEyeNether extends Item
{
	Structure<?> type = Structure.field_236378_n_;
	static String typeName = "Fortress";
	
	public ItemGreaterEyeNether(Properties properties)
	{
		super(properties);
	}	   
	   
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
      
		playerIn.setActiveHand(handIn);
		
		if((worldIn instanceof ServerWorld) && (playerIn.isSneaking()) && (worldIn.getDimensionKey().equals(World.THE_NETHER)))   //shift right-click changes structure type to locate
		{   
			if(type == Structure.field_236378_n_)  // Fortress
			{
				type = Structure.field_236383_s_;	  //Bastion
				typeName = "Bastion Remnant";
			}
			else if(type == Structure.field_236383_s_) //Bastion
			{
				type = Structure.field_236382_r_;	  //Fossils
				typeName = " Nether Fossil";
			}
			else if(type == Structure.field_236382_r_) //Fossils
			{
				type = Structure.field_236378_n_;	  //Fortress
				typeName = " Fortress";
			}
			
			playerIn.sendStatusMessage((new TranslationTextComponent("item.greater_eye.greater_eye.message1", typeName).mergeStyle(TextFormatting.BOLD)), true);
		  
			return ActionResult.resultSuccess(itemstack);
		}
			
		if(!playerIn.isSneaking())   //simple right-click executes
		{		
			if((worldIn instanceof ServerWorld) && (worldIn.getDimensionKey().equals(World.THE_NETHER)))
			{
				findStructureAndShoot(worldIn, playerIn, itemstack, type);
				
				return ActionResult.resultSuccess(itemstack);
			}
		}
		
        return ActionResult.resultSuccess(itemstack);
	}  

	private static void findStructureAndShoot(World worldIn, PlayerEntity playerIn, ItemStack itemstack, Structure<?> type)
	{
		
		boolean displayMessage = GeneralModConfig.DISPLAY_DISTANCE_MESSAGE.get();
		
		// A structure will always be found, no matter how far away
		BlockPos playerpos = playerIn.getPosition();
		BlockPos locpos = ((ServerWorld)worldIn).getChunkProvider().getChunkGenerator().func_235956_a_((ServerWorld)worldIn, type, playerIn.getPosition(), 100, false);
		
		int structureDistance = MathHelper.floor(getDistance(playerpos.getX(), playerpos.getZ(), locpos.getX(), locpos.getZ()));
		
		if(displayMessage)
		{
			playerIn.sendStatusMessage(new TranslationTextComponent("item.greater_eye.greater_eye.message3", typeName, structureDistance).mergeStyle(TextFormatting.BOLD), true);	
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
		worldIn.playEvent((PlayerEntity)null, 1003, playerIn.getPosition(), 0);

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
		tooltip.add((new TranslationTextComponent("item.greater_eye.greater_eye.line1").mergeStyle(TextFormatting.GREEN)));
		tooltip.add((new TranslationTextComponent("item.greater_eye.greater_eye.line2").mergeStyle(TextFormatting.YELLOW)));
		tooltip.add((new TranslationTextComponent("item.greater_eye.greater_eye.message2", typeName).mergeStyle(TextFormatting.LIGHT_PURPLE)));
	}	   
}
