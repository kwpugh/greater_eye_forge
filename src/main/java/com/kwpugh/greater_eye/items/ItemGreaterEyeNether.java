package com.kwpugh.greater_eye.items;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.kwpugh.greater_eye.config.GeneralModConfig;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.item.Item.Properties;

public class ItemGreaterEyeNether extends Item
{
	StructureFeature<?> type = StructureFeature.NETHER_BRIDGE;
	static String typeName = "Fortress";
	String testValue;
	
	public ItemGreaterEyeNether(Properties properties)
	{
		super(properties);
	}	   
	 
	// Get values in NBT
	public static String getTypeData(ItemStack stack)
	{
		if (!stack.hasTag())
		{
			return null;
		}		 

		CompoundTag tags = stack.getTag();
	 
		if (tags.contains("typeName"))
		{
			return tags.getString("typeName");
		}
		return null;
	}
	
	// Set values in NBT
	public static void setTypeData(ItemStack stack, Level world, Player player, String typeName)
	{
		if(world.isClientSide)
		{
			return;
		}
	 
		CompoundTag tags;
	 
		if (!stack.hasTag())
		{
			tags = new CompoundTag();
		}
		else
		{
			tags = stack.getTag();
		}
	 
		if (typeName == null)
		{
			tags.remove("typeName");
		}
		else
		{
			tags.putString("typeName", typeName);
		}
		
		stack.setTag(tags);
	}
	
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
	{
		ItemStack itemstack = playerIn.getItemInHand(handIn);
      
		if (!itemstack.hasTag())
		{
			setTypeData(itemstack, worldIn, playerIn, typeName);
		}
		
		playerIn.startUsingItem(handIn);
		
		if((worldIn instanceof ServerLevel) && (playerIn.isShiftKeyDown()) && (worldIn.dimension().equals(Level.NETHER)))   //shift right-click changes structure type to locate
		{ 
			if(testValue == "Fortress")  // Fortress
			{
				typeName = "Bastion Remnant";
			}
			else if(testValue == "Bastion Remnant") //Bastion
			{
				typeName = "Nether Fossil";
			}
			else if(testValue == "Nether Fossil") //Fossils
			{
				typeName = "Fortress";
			}
			
			switch(getTypeData(itemstack))
			{
				case "Fortress":
					typeName = "Bastion Remnant";
					break;
				case "Bastion Remnant":
					typeName = "Nether Fossil";
					break;
				case "Nether Fossil":
					typeName = "Fortress";
					break;		
				default:
					break;
			}
			setTypeData(itemstack, worldIn, playerIn, typeName);
			
			playerIn.displayClientMessage((new TranslatableComponent("item.greater_eye.greater_eye.message1", typeName).withStyle(ChatFormatting.BOLD)), true);
		  
			return InteractionResultHolder.success(itemstack);
		}
			
		if(!playerIn.isShiftKeyDown())   //simple right-click executes
		{		
			if((worldIn instanceof ServerLevel) && (worldIn.dimension().equals(Level.NETHER)))
			{
				findStructureAndShoot(worldIn, playerIn, itemstack, type);
				
				return InteractionResultHolder.success(itemstack);
			}
		}
		
        return InteractionResultHolder.success(itemstack);
	}  

	private static void findStructureAndShoot(Level worldIn, Player playerIn, ItemStack itemstack, StructureFeature<?> type)
	{
		Random random = new Random();

		switch(getTypeData(itemstack))
		{
		case "Fortress":
			type = StructureFeature.NETHER_BRIDGE;
;			break;
		case "Bastion Remnant":
			type = StructureFeature.BASTION_REMNANT;
			break;
		case "Nether Fossil":
			type = StructureFeature.NETHER_FOSSIL;
			break;
		default:
			break;
		}
		
		boolean displayMessage = GeneralModConfig.DISPLAY_DISTANCE_MESSAGE.get();
		
		// A structure will always be found, no matter how far away
		BlockPos playerpos = playerIn.blockPosition();
		BlockPos locpos = ((ServerLevel)worldIn).getChunkSource().getGenerator().findNearestMapFeature((ServerLevel)worldIn, type, playerIn.blockPosition(), 100, false);
		
		int structureDistance = Mth.floor(getDistance(playerpos.getX(), playerpos.getZ(), locpos.getX(), locpos.getZ()));
		
		if(displayMessage)
		{
			playerIn.displayClientMessage(new TranslatableComponent("item.greater_eye.greater_eye.message3", typeName, structureDistance).withStyle(ChatFormatting.BOLD), true);	
		}
	
		EyeOfEnder finderentity = new EyeOfEnder(worldIn, playerIn.getX(), playerIn.getY(0.5D), playerIn.getZ());
		finderentity.setItem(itemstack);
		finderentity.signalTo(locpos);
		worldIn.addFreshEntity(finderentity);

		if (playerIn instanceof ServerPlayer)
		{
			CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)playerIn, locpos);
		}

		worldIn.playSound((Player)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.NOTE_BLOCK_COW_BELL, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		worldIn.levelEvent((Player)null, 1003, playerIn.blockPosition(), 0);

		if (!playerIn.getAbilities().instabuild)
		{
			itemstack.shrink(1);					
		}
		
		return;
	}
	
	private static float getDistance(int x1, int z1, int x2, int z2)
	{
		int i = x2 - x1;
		int j = z2 - z1;
	  
		return Mth.sqrt((float)(i * i + j * j));
	}
	
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
	{
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add((new TranslatableComponent("item.greater_eye.greater_eye.line1").withStyle(ChatFormatting.GREEN)));
		tooltip.add((new TranslatableComponent("item.greater_eye.greater_eye.line2").withStyle(ChatFormatting.YELLOW)));
		
		if(getTypeData(stack) != null)
		{
			tooltip.add((new TranslatableComponent("item.greater_eye.greater_eye.message2", getTypeData(stack)).withStyle(ChatFormatting.LIGHT_PURPLE)));	
		}
	}	   
}
