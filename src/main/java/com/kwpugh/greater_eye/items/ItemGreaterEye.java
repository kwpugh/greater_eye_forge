package com.kwpugh.greater_eye.items;

import com.kwpugh.greater_eye.TagInit;
import com.kwpugh.greater_eye.config.GeneralModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemGreaterEye extends Item
{
 	String structureChoice = "Villages";
	static TagKey<ConfiguredStructureFeature<?, ?>> overworldType = TagInit.VILLAGES;

	public ItemGreaterEye(Properties properties)
	{
		super(properties);
	}	   

	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
	{		
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		
		playerIn.startUsingItem(handIn);
		
		if((worldIn instanceof ServerLevel) && (playerIn.isShiftKeyDown()))   //shift right-click changes structure type to locate
		{
			if (structureChoice == "Villages")
			{
				structureChoice = "Mineshafts";
				overworldType = TagInit.MINESSHAFTS;
			} else if (structureChoice == "Mineshafts")
			{
				structureChoice = "Shipwrecks";
				overworldType = TagInit.SHIPWRECKS;
			} else if (structureChoice == "Shipwrecks")
			{
				structureChoice = "Outposts";
				overworldType = TagInit.OUTPOSTS;
			} else if (structureChoice == "Outposts")
			{
				structureChoice = "Monuments";
				overworldType = TagInit.MONUMENTS;
			} else if (structureChoice == "Monuments")
			{
				structureChoice = "Mansions";
				overworldType = TagInit.MANSIONS;
			} else if (structureChoice == "Mansions")
			{
				structureChoice = "Pyramids";
				overworldType = TagInit.PYRAMIDS;
			} else if (structureChoice == "Pyramids")
			{
				structureChoice = "Strongholds";
				overworldType = TagInit.STRONGHOLDS;
			} else if (structureChoice == "Strongholds")
			{
				structureChoice = "Buried Treasures";
				overworldType = TagInit.BURIED_TREASURES;
			} else if (structureChoice == "Buried Treasures")
			{
				structureChoice = "Ruins";
				overworldType = TagInit.RUINS;
			} else if (structureChoice == "Ruins")
			{
				structureChoice = "Igloos";
				overworldType = TagInit.IGLOOS;
			} else if (structureChoice == "Igloos")
			{
				structureChoice = "Huts";
				overworldType = TagInit.HUTS;
			} else if (structureChoice == "Huts")
			{
				structureChoice = "Villages";
				overworldType = TagInit.VILLAGES;
			}


			
			playerIn.displayClientMessage((new TranslatableComponent("item.greater_eye.greater_eye.message1", structureChoice).withStyle(ChatFormatting.BOLD)), true);
			
			return InteractionResultHolder.success(itemstack);
		}

		if(!playerIn.isShiftKeyDown())   //simple right-click executes
		{		
			if((worldIn instanceof ServerLevel))
			{
				findStructureAndShoot(worldIn, playerIn, itemstack, structureChoice);
				
				return InteractionResultHolder.success(itemstack);
			}
		}
		
        return InteractionResultHolder.success(itemstack);
	}  

	private static void findStructureAndShoot(Level worldIn, Player playerIn, ItemStack itemstack, String structureChoice)
	{
		Random random = new Random();

		boolean displayMessage = GeneralModConfig.DISPLAY_DISTANCE_MESSAGE.get();
		
		// A structure will always be found, no matter how far away
		BlockPos playerpos = playerIn.blockPosition();

		ServerLevel serverLevel = (ServerLevel) worldIn;
		BlockPos locpos = serverLevel.findNearestMapFeature(overworldType, playerIn.blockPosition(), 100, false);

		if(locpos == null)
		{
			playerIn.displayClientMessage((new TranslatableComponent("Cannot be found! Structure may not exist here or may have been replaced by another mod.").withStyle(ChatFormatting.GOLD)), true);

			return;
		}
		
		int structureDistance = Mth.floor(getDistance(playerpos.getX(), playerpos.getZ(), locpos.getX(), locpos.getZ()));
		
		if(displayMessage)
		{
			playerIn.displayClientMessage(new TranslatableComponent("item.greater_eye.greater_eye.message3", structureChoice, structureDistance).withStyle(ChatFormatting.BOLD), true);
		}
	
		EyeOfEnder finderentity = new EyeOfEnder(worldIn, playerIn.getX(), playerIn.getY(0.5D), playerIn.getZ());
		finderentity.setItem(itemstack);
		finderentity.signalTo(locpos);
		worldIn.addFreshEntity(finderentity);

		if (playerIn instanceof ServerPlayer)
		{
			CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)playerIn, locpos);
		}

		worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.NOTE_BLOCK_COW_BELL, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		worldIn.levelEvent(null, 1003, playerIn.blockPosition(), 0);

		if (!playerIn.getAbilities().instabuild)
		{
			itemstack.shrink(1);					
		}
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
		tooltip.add((new TranslatableComponent("item.greater_eye.greater_eye.message2", structureChoice).withStyle(ChatFormatting.LIGHT_PURPLE)));
	}	   
}
