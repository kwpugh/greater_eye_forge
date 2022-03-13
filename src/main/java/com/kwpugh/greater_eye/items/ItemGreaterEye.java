package com.kwpugh.greater_eye.items;

import com.kwpugh.greater_eye.config.GeneralModConfig;
import com.kwpugh.greater_eye.init.TagInit;
import com.kwpugh.greater_eye.util.LocateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGreaterEye extends Item
{
	boolean enableBuildings = GeneralModConfig.ENABLE_BUILDINGS.get();
	boolean enableGraveYards = GeneralModConfig.ENABLE_GRAVEYARDS.get();

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
			switch (structureChoice)
			{
				case "Villages" -> {
					structureChoice = "Mineshafts";
					overworldType = TagInit.MINESHAFTS;
				}
				case "Mineshafts" -> {
					structureChoice = "Ships";
					overworldType = TagInit.SHIPS;
				}
				case "Ships" -> {
					structureChoice = "Ruins";
					overworldType = TagInit.RUINS;
				}
				case "Ruins" -> {
					structureChoice = "Igloos";
					overworldType = TagInit.IGLOOS;
				}
				case "Igloos" -> {
					structureChoice = "Huts";
					overworldType = TagInit.HUTS;
				}
				case "Huts" -> {
					if(enableGraveYards)  // if graveyards enabled in config, use it here
					{
						structureChoice = "Graveyards";
						overworldType = TagInit.GRAVEYARDS;
					}
					else // otherwise move on to next
					{
						structureChoice = "Strongholds";
						overworldType = TagInit.STRONGHOLDS;
					}
				}
				case "Graveyards" -> {
					structureChoice = "Strongholds";
					overworldType = TagInit.STRONGHOLDS;
				}
				case "Strongholds" -> {
					if(enableBuildings)  // if graveyards enabled in config, use it here
					{
						structureChoice = "Buildings";
						overworldType = TagInit.BUILDINGS;
					}
					else // otherwise move on to next
					{
						structureChoice = "Villages";
						overworldType = TagInit.VILLAGES;
					}
				}
				case "Buildings" -> {
					structureChoice = "Villages";
					overworldType = TagInit.VILLAGES;
				}
			}

			playerIn.displayClientMessage((new TranslatableComponent("item.greater_eye.greater_eye.message1", structureChoice).withStyle(ChatFormatting.BOLD)), true);
			
			return InteractionResultHolder.success(itemstack);
		}

		if(!playerIn.isShiftKeyDown())   //simple right-click executes
		{		
			if((worldIn instanceof ServerLevel))
			{
				LocateUtil.findStructureAndShoot(worldIn, playerIn, itemstack, structureChoice, overworldType);
				
				return InteractionResultHolder.success(itemstack);
			}
		}
		
        return InteractionResultHolder.success(itemstack);
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
